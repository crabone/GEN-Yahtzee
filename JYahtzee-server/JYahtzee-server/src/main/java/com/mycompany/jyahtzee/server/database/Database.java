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

    // for the database connexion
    public static final String DB_PWD = "root";
    public static final String DB_USERNAME = "root";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/Yahtzee";
    
    public void connect()throws SQLException
    {
        connecter(DB_URL, DB_USERNAME, DB_PWD);
    }
    public void connecter(String url, String user, String mdp) throws SQLException{
            connexion = DriverManager.getConnection(url, user, mdp);
    }
    public void disconnect()
    {
        if(connexion != null)
        {
            try
            {
                connexion.close();
                connexion = null;
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    public boolean connected()
    {
        return connexion != null;
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

    public String score(String joueur) throws SQLException{
            PreparedStatement preparedStatement = connexion.prepareStatement("select ScoreTotal from joueur where Username = ?");
            preparedStatement.setString(1, joueur);
            preparedStatement.execute();
            return preparedStatement.toString();
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

    public ArrayList<ArrayList<String>> getGames() throws SQLException
    {
        Statement state = connexion.createStatement();
        Statement stateTemp = connexion.createStatement();
        ResultSet result = state.executeQuery("SELECT * FROM Partie");
        ResultSet resutlTemp;
        ArrayList<ArrayList<String>> listGame = new ArrayList<>();
        while(result.next())
        {
            ArrayList<String> listGameTemp = new ArrayList<>();
            listGameTemp.add(Integer.toString(result.getInt("ID")));
            listGameTemp.add(result.getString("Etat"));
            resutlTemp = stateTemp.executeQuery("select Joueur.Username from Partie inner join Partie_Joueur on Partie.ID=Partie_Joueur.ID_partie inner join Joueur on Partie_Joueur.ID_joueur=Joueur.ID where Partie.ID=" + result.getInt("ID"));
            while(resutlTemp.next())
            {
                listGameTemp.add(resutlTemp.getString("Username"));
            }
            listGame.add(listGameTemp);
        }
        return listGame;
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


    public void changeState(int idGame, String state)throws SQLException
    {
        PreparedStatement preparedStatement = connexion.prepareStatement("update Partie set Etat = ? where ID = ?"); 
        preparedStatement.setString(1, state);
        preparedStatement.setInt(2, idGame);
        preparedStatement.executeUpdate();

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

    public void addPlayerGame(int idGame,int idPlayer) throws SQLException
    {
            PreparedStatement preparedStatement = connexion.prepareStatement("insert into Partie_Joueur(ID_joueur,ID_partie) values (?,?)");
            preparedStatement.setInt(1, idPlayer);
            preparedStatement.setInt(2, idGame);
            preparedStatement.executeUpdate();      
    }
}
