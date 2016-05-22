package com.mycompany.jyahtzee.server.database;
import java.security.NoSuchAlgorithmException;
//import com.mycompany.jyahtzee.server.hash;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mycompany.jyahtzee.server.hash.Hash;

//import com.mycompany.jyahtzee.server.hash.Hash;

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
        public void insererJoueur(String nom, String mdp) throws SQLException, NoSuchAlgorithmException{
                Hash hasher = new Hash();
		String hash = hasher.createHash(mdp);
		PreparedStatement preparedStatement = connexion.prepareStatement("insert into Joueur(Username, MDP) values (?, ?)");
		preparedStatement.setString(1, nom);
                preparedStatement.setString(2, hash);
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
	
	public void modifierMdp(String oldPassword, String newPassword)throws SQLException, NoSuchAlgorithmException{
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
	
	public int scoreMax() throws SQLException{
		Statement state = connexion.createStatement();
		ResultSet result = state.executeQuery("select MAX(scoreTotal)as scoreMax from Joueur");
		return result.getInt("scoreMax");
	}
	
	public String gagnant() throws SQLException{
		Statement state = connexion.createStatement();
		ResultSet result = state.executeQuery("select Username from Joueur where scoreTotal = " + scoreMax());
		return result.getString("Username");
	}
	
	public ArrayList<String> players() throws SQLException{
		Statement state = connexion.createStatement();
		ResultSet result = state.executeQuery("select * from Joueur");
		ArrayList<String> listPlayers = new ArrayList<>();
		while (result.next()){
			listPlayers.add(result.getString("Username"));
		}
		return listPlayers;
	}
        // Check if a player named "username" exists: if true, return his ID otherwise return 0
        public int playerExist(String username) throws SQLException
        {
            Statement state = connexion.createStatement();
            ResultSet resultat = state.executeQuery("select ID from Joueur where Username='"+username +"'");
            if(resultat.next())
            {
                return resultat.getInt("ID");
            }                       
            return 0;
        }
        
        public int getIDJoueur(String userName, String mdp) throws SQLException
        {
            Statement state = connexion.createStatement();
            ResultSet resultat = state.executeQuery("select ID from Joueur where Username='"+userName +"' AND MDP='"+mdp+"'");
            if(resultat.next())
            {
                return resultat.getInt("ID");
            }
                       
            return 0;
        }
        public int verify(String userName, String mdp) throws SQLException, NoSuchAlgorithmException
        {
            Hash hasher = new Hash();
            String hashToCheck = hasher.createHash(mdp);
            //PreparedStatement preparedStatement = connexion.prepareStatement("select * from joueur where MDP = ? and Username = ?");
            //preparedStatement.setString(1, hashToCheck);
            //preparedStatement.setString(2, userName);
            //ResultSet result = preparedStatement.executeQuery();
            Statement state = connexion.createStatement();
            ResultSet result = state.executeQuery("select ID from Joueur where MDP='" + hashToCheck +"' and Username='"+ userName + "'");
            if (result.next()){
                    return result.getInt("ID");
            }
            else{
                    return 0;
            }
        }
        
	public static void main(String... args) throws SQLException{
		Database db = new Database();
		db.connecter("jdbc:mysql://localhost:3306/Yahtzee", "root", "");
               /* try
                {
                    Connection connexion;
                    connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Yahtzee", "root", "");
                    
                }
                catch(SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }*/
                int id = 0;
                String test;
                try
                {
                    db.insererJoueur("rosanne", "1234");
                    id = db.verify("rosanne", "1234");
                }
                catch(NoSuchAlgorithmException e)
                {
                    System.out.println("error");
                }
                //test = db.score("kevin");
                System.out.println("id=" + id);
		/*db.insererJoueur("ibrahim");
		for (int i = 0; i < db.players().size(); i++){
			System.out.println(db.players().get(i));
		}
		System.out.println(db.scoreMax());
                        */
	}
}
