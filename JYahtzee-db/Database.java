package com.mycompany.jyahtzee.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public static void main(String... args) throws SQLException{
		Database db = new Database();
		db.connecter("jdbc:mysql://localhost:3306/yahtzee", "root", "");
		db.insererJoueur("ibrahim");
	}
}
