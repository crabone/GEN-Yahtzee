package com.mycompany.jyahtzee.server.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.mycompany.jyahtzee.manager.Player;


public class Database {
    private Connection connexion = null;
    private Statement state;
    public void connecter(String url, String utilisateur, String mdp) throws SQLException{
            connexion = DriverManager.getConnection(url,utilisateur,mdp);
    }

    public void insertPlayer(String nom) throws SQLException{
            PreparedStatement preparedStatement = connexion.prepareStatement("insert into Joueur(Username) values (?)");
            preparedStatement.setString(1, nom);
            preparedStatement.executeUpdate();

    }
    public void insertPlayer(String nom, String mdp) throws SQLException{                
            PreparedStatement preparedStatement = connexion.prepareStatement("insert into Joueur(Username, MDP) values (?, ?)");
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, mdp);
            preparedStatement.executeUpdate();

    }

    public void modifyPlayer(String nom) throws SQLException{
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

    public void modifyPwd(String oldPassword, String newPassword)throws SQLException{		
            PreparedStatement preparedStatement = connexion.prepareStatement("select * from joueur where MDP = ?");
            preparedStatement.setString(1, oldPassword);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()){
                    preparedStatement = connexion.prepareStatement("update MDP from Joueur where MDP = ?"); 
                    preparedStatement.setString(1, newPassword);
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

    public String winner() throws SQLException{
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
    public int verify(String userName, String mdp) throws SQLException
    {
        Statement state = connexion.createStatement();
        ResultSet result = state.executeQuery("select ID from Joueur where MDP='" + mdp +"' and Username='"+ userName + "'");
        if (result.next()){
                return result.getInt("ID");
        }
        else{
                return 0;
        }
    }

    public void addPlayerGame(int idGame,ArrayList<Integer> players) throws SQLException
    {
        for(int idPlayer : players)
        {
            PreparedStatement preparedStatement = connexion.prepareStatement("insert into Partie_Joueur(ID_joueur,ID_partie) values (?,?)");
            preparedStatement.setInt(1, idPlayer);
            preparedStatement.setInt(2, idGame);
            preparedStatement.executeUpdate();
            
        }
        
    }
    public static void main(String... args) throws SQLException{
        Database db = new Database();
        db.connecter("jdbc:mysql://localhost:3306/Yahtzee", "root", "root");
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

        //db.insererJoueur("rosanne", "1234");
        id = db.verify("rosanne", "1234");

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
