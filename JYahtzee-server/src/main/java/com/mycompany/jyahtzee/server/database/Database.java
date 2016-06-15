/**
 * Projet : Jyahtzee
 * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
 * Date : 15.06.16
 * Version : 3.5
 * Description : Cette class gère les parties qui ont été créés par les joueurs
 */

package com.mycompany.jyahtzee.server.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Projet : Jyahtzee
 * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
 * Date : 15.06.16
 * Version : 3.5
 * Description : Cette classe gère la communication avec la base de données
 */
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
    
    /** fonction permettant de se connecter a la base de donnée
        @param  url l'url de la base de données
        @param  user l'utilisateur de la base de données
        @param  mdp  le mot de passe de l'utilisateur de la base de donnees
     */
    public void connecter(String url, String user, String mdp) throws SQLException{
            connexion = DriverManager.getConnection(url, user, mdp);
    }
    
    /** méthode permettant de se déconnecter de la base de donnees*/
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
    
    /** méthode permettant de se verifier si on est deja connecter a la base de donnees*/
    public boolean connected()
    {
        return connexion != null;
    }

    /** methode permettant d'inserer un joueur dans la base de données
     @param nom le nom du joueur à inserer
    */
    public void insertPlayer(String nom) throws SQLException{
            PreparedStatement preparedStatement = connexion.prepareStatement("insert into Joueur(Username) values (?)");
            preparedStatement.setString(1, nom);
            preparedStatement.executeUpdate();
    }
    
    /** methode permettant d'inserer un joueur dans la base de données
     @param nom le nom du joueur à inserer
     @param mdp le mot de passe à inserer
    */        
    public void insertPlayer(String nom, String mdp) throws SQLException{                
            PreparedStatement preparedStatement = connexion.prepareStatement("insert into Joueur(Username, MDP) values (?, ?)");
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, mdp);
            preparedStatement.executeUpdate();
    }

    /** methode permettant d'inserer le score d'un joueur dans la base de données
     @param joueur nom du joueur 
     @return le score du joueur
     */
    public String score(String joueur) throws SQLException{
            PreparedStatement preparedStatement = connexion.prepareStatement("select ScoreTotal from joueur where Username = ?");
            preparedStatement.setString(1, joueur);
            preparedStatement.execute();
            return preparedStatement.toString();
    }

    /** methode permettant d'inserer le score max dans la base de données
     * @return le score max
     */
    public int scoreMax() throws SQLException{
            Statement state = connexion.createStatement();
            ResultSet result = state.executeQuery("select MAX(scoreTotal)as scoreMax from Joueur");
            return result.getInt("scoreMax");
    }

    /** methode renvoyant le vainqueur de la partie
     * @return le nom du vainqueur
     * @throws SQLException 
     */
    public String winner() throws SQLException{
            Statement state = connexion.createStatement();
            ResultSet result = state.executeQuery("select Username from Joueur where scoreTotal = " + scoreMax());
            return result.getString("Username");
    }

    /** methode renvoyant la liste de tous les joueurs
     * @return la liste de tous les joueurs
     * @throws SQLException 
     */
    public ArrayList<String> players() throws SQLException{
            Statement state = connexion.createStatement();
            // requete selectionnant tous les joueurs de la base de donnees
            ResultSet result = state.executeQuery("select * from Joueur");
            
            // On stocke tous les noms des joueurs dans un tableau
            ArrayList<String> listPlayers = new ArrayList<>();
            while (result.next()){
                    listPlayers.add(result.getString("Username"));
            }
            return listPlayers;
    }

    /** methode renvoyant la liste de toutes les parties
     * @return la liste de toutes les parties
     * @throws SQLException 
     */
    public ArrayList<ArrayList<String>> getGames() throws SQLException
    {
        Statement state = connexion.createStatement();
        Statement stateTemp = connexion.createStatement();
        // requete pour selectionner toutes les parties
        ResultSet result = state.executeQuery("SELECT * FROM Partie");
        ResultSet resutlTemp;
        ArrayList<ArrayList<String>> listGame = new ArrayList<>();
        // On stocke ces partie dans un tableau
        while(result.next())
        {
            ArrayList<String> listGameTemp = new ArrayList<>();
            listGameTemp.add(Integer.toString(result.getInt("ID")));
            listGameTemp.add(result.getString("Etat"));
            // requete pour selectionner un joueur en fonction d'une partie
            resutlTemp = stateTemp.executeQuery("select Joueur.Username from Partie inner join Partie_Joueur on Partie.ID=Partie_Joueur.ID_partie inner join Joueur on Partie_Joueur.ID_joueur=Joueur.ID where Partie.ID=" + result.getInt("ID"));
           // on rajoute dans les joueurs dans un tableau
            while(resutlTemp.next())
            {
                listGameTemp.add(resutlTemp.getString("Username"));
            }
            listGame.add(listGameTemp);
        }
        return listGame;
    }
    
    /**
     * Cette methode crée un nouveau jeu
     * @param stateStart la partie a demarrer
     * @return l'id de la partie
     * @throws SQLException 
     */

    public int newGame(String stateStart) throws SQLException
    {
        // On insère d'abord une partie dans la base de données
        PreparedStatement preparedStatement = connexion.prepareStatement("insert into Partie(Etat) values (?)");
        preparedStatement.setString(1, stateStart);
        preparedStatement.executeUpdate();
        Statement state = connexion.createStatement();
        // requete pour selectionner l'id de la nouvelle partie
        ResultSet getID = state.executeQuery("select ID from Partie where Etat = '" + stateStart + "'");
        getID.last();
        return getID.getInt("ID");
    }


     /**
     * Cette methode change le statut d'un jeu
     * @param idGame l'id de la partie
     * @param state le statut
     * @throws SQLException 
     */
    public void changeState(int idGame, String state)throws SQLException
    {
        // Mis a jour de la partie
        PreparedStatement preparedStatement = connexion.prepareStatement("update Partie set Etat = ? where ID = ?"); 
        preparedStatement.setString(1, state);
        preparedStatement.setInt(2, idGame);
        preparedStatement.executeUpdate();

    }

 
    /**
     * Cette méthode verifie si un joueur existe dans la base de données
     * @param username le nom du joueur
     * @return l'id du joueur
     * @throws SQLException 
     */
    public int playerExist(String username) throws SQLException
    {
        Statement state = connexion.createStatement();
        // Requete pour selectionner l'id du joueur 
        ResultSet resultat = state.executeQuery("select ID from Joueur where Username='"+username +"'");
        if(resultat.next())
        {
            return resultat.getInt("ID");
        }                       
        return 0;
    }

    /**
     * Cette méthode permet d'obtenir l'id d'un joueur a partir de son nom et de son mot de 
     * passe
     * @param userName le nom du joueur
     * @param mdp le mots de passe du labo
     * @return l'id du joueur
     * @throws SQLException 
     */
    
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

    /**
     * Cette methode permet de renvoyer l'id du joeur a partir de son username et
     * de son mot de passe
     * @param userName le nom du joueur
     * @param mdp le mot de passe 
     * @return l'id du joueur
     * @throws SQLException 
     */
    public int verify(String userName, String mdp) throws SQLException
    {
        Statement state = connexion.createStatement();
         // Requete pour selectionner l'id d'un joueur dans la base de donnée
        ResultSet result = state.executeQuery("select ID from Joueur where MDP='" + mdp +"' and Username='"+ userName + "'");
        if (result.next()){
                return result.getInt("ID");
        }
        else{
                return 0;
        }
    }
    
    /**
     * Cette méthode ajoute un joueur à une partie
     * @param idGame l'id de la partie
     * @param idPlayer l'id du joueur
     * @throws SQLException 
     */
    public void addPlayerGame(int idGame,int idPlayer) throws SQLException
    {
            // Requete pour inserer un joueur dans la base de donnée
            PreparedStatement preparedStatement = connexion.prepareStatement("insert into Partie_Joueur(ID_joueur,ID_partie) values (?,?)");
            preparedStatement.setInt(1, idPlayer);
            preparedStatement.setInt(2, idGame);
            preparedStatement.executeUpdate();      
    }
}
