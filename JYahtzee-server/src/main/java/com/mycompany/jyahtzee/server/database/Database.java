package com.mycompany.jyahtzee.server.database;
import com.mycompany.jyahtzee.manager.Player;
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

public class Database {
	private Connection connexion = null;
	private Statement state;
	public void connecter(String url, String utilisateur, String mdp) throws SQLException{
		connexion = DriverManager.getConnection(url,utilisateur,mdp);
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
        
        public int getIDJoueur(String userName, String mdp) throws SQLException
        {
            Statement state = connexion.createStatement();
            ResultSet resultat = state.executeQuery("select ID from Joueur where Username = '" + userName + "' AND MDP = '"+mdp+"'");
            if(resultat.next())
            {
                return resultat.getInt("ID");
            }
            return 0;
            
        }
        
        public Player getPlayer(int id) throws SQLException
        {
            Statement state = connexion.createStatement();
            ResultSet resultat = state.executeQuery("select ID,Username,ScoreTotal from Joueur where ID = '" +id + "'");
            if(resultat.next())
            {
                Player player = new Player(resultat.getInt("ID"),resultat.getString("Username"),resultat.getInt("ScoreTotal"));
                return player;
            }
            return null;
            
        }
        public int newGame(String stateStart) throws SQLException
        {
            PreparedStatement preparedStatement = connexion.prepareStatement("insert into Partie(Etat) values (?)");
            preparedStatement.setString(1, stateStart);
            preparedStatement.executeUpdate();
            Statement state = connexion.createStatement();
            ResultSet getID = state.executeQuery("select ID from Partie where Etat = '" + stateStart + "'");
            getID.last();
            return getID.getInt("ID");
            
            
        }
        public static void changeState(String state)
        {
            
        }
        
	public static void main(String... args) throws SQLException{
		Database db = new Database();
		//db.connecter("jdbc:mysql://localhost:3306/yahtzee", "root", "versus1204@");
                db.connecter("jdbc:mysql://localhost:3306/yahtzee", "root", "root");
                //System.out.println("OK");
		/*db.insererJoueur("ibrahim");
		for (int i = 0; i < db.players().size(); i++){
			System.out.println(db.players().get(i));
		}
		System.out.println(db.scoreMax());
                */
                Player pl = db.getPlayer(1);
                //System.out.println("Player : id :"+pl.getID() + " Username : "+pl.getUserName() + " score : = "+ pl.getScore());
               // int id;
                //id = db.getIDJoueur("rose", "rose");
                //db.insererJoueur(null);
                
                //id = db.newGame("test2");
                //System.out.println("Lecture  " + id);
                
                
                
        }
	
}
