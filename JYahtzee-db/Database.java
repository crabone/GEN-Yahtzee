package com.mycompany.jyahtzee.db;
import com.mycompany.jyahtzee.server.hash;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mycompany.jyahtzee.server.hash.Hash;

public class Database {
	private Connection connexion;
	private Statement state;
	public void connecter(String url, String utilisateur, String mdp) throws SQLException{
		connexion = DriverManager.getConnection(url, utilisateur, mdp);
	}
	
	public void insererJoueur(String nom) throws SQLException{
		PreparedStatement preparedStatement = connexion.prepareStatement("insert into Joueur(Username) values (?)");
		preparedStatement.setString(1, nom);
		preparedStatement.executeUpdate();

	}
	
	public void modifierJoueur(String nom) throws SQLException{
		PreparedStatement preparedStatement = connexion.prepareStatement("update Username from Joueur where Username = ? ");
		preparedStatement.setString(1, nom);
		preparedStatement.executeUpdate();

	}
	
	public String score(String joueur) throws SQLException{
		PreparedStatement preparedStatement = connexion.prepareStatement("select ScoreTotal from joueur where Username = ?");
		preparedStatement.setString(1, joueur);
		preparedStatement.execute();
		return preparedStatement.toString();
	}
	
	public void modifierMdp(String oldPassword, String newPassword)throws SQLException{
		Hash hasher = new Hash();
		String oldhash = hasher.createHash(oldPassword);
		PreparedStatement preparedStatement = connexion.prepareStatement("select * from joueur where MDP = ?");
		preparedStatement.setString(1, oldhash);
		ResultSet result = preparedStatement.executeQuery();
		if (result.next()){
			String newHash = hasher.createHash(newPassword);
			preparedStatement = connexion.prepareStatement("update MDP from Joueur where MDP = ?"); 
			preparedStatement.setString(1, newHash);
			preparedStatement.execute();
		}
		else{
			System.out.println("Mot de passe intouvable dans la base de donnés");
		}
	}
	
	public int scoreMax(){
		Statement state = connexion.createStatement();
		ResultSet result = state.executeQuery("select MAX(scoreTotal)as scoreMax from Joueur");
		return result.getInt("scoreMax");
	}
	
	public String gagnant(){
		Statement state = connexion.createStatement();
		ResultSet result = state.executeQuery("select Username from Joueur where scoreTotal = " + scoreMax());
		return result.getString("Username");
	}
	public static void main(String... args) throws SQLException{
		Database db = new Database();
		db.connecter("jdbc:mysql://localhost:3306/yahtzee", "root", "versus1204@");
		db.insererJoueur("ibrahim");
	}
}
