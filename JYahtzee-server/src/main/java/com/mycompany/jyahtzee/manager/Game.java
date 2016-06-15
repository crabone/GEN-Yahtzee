/**
 * Projet : Jyahtzee
 * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
 * Date : 15.06.16
 * Version : 3.5
 * Description : Cette class gère une partie qui a été crée par un joueur
 */
package com.mycompany.jyahtzee.manager;

import com.mycompany.jyahtzee.server.JYahtzeeServer;
import com.mycompany.jyahtzee.server.database.Database;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;


public class Game extends Observable
{
    private int idGame;
    private int playerXTurn;
    private Status status;
    private Die[] dice;
    
    //Status du jeu
    private enum Status{OPEN,PLAY,CLOSE};
    //HashMap reliant un id de joueur à un scoreManager
    private HashMap<Integer,ScoreManager> scoreManage;
    private ArrayList<Integer> players;
    private ArrayList<Integer> observers;
    
    public Game() throws SQLException
    {
        status = Status.OPEN;
        //Créer un nouveau game dans la base de donnée avec status OPEN
        idGame = JYahtzeeServer.db.newGame(status.name());
       
        
        players = new ArrayList<>();
        observers = new ArrayList<>();
        scoreManage  = new HashMap<>();
        
        //Tableau pour les 5 dés
        dice = new Die[5];
        playerXTurn = 0;
        
        for(int i = 0 ; i < 5 ; i++)
        {
            dice[i] = new Die();
        }
    }
    
    //Ajout un player a la partie seulement si elle a le status OPEN
    public boolean addPlayer(int idPlayer)throws SQLException
    {
        if(status == Status.OPEN)
        {
            //Ajout d'un player et crée un scoreManager pour gérer son score
            ScoreManager scorePlayer = new ScoreManager();
            players.add(idPlayer); 
            //Ajout du player dans la base de donnée
            JYahtzeeServer.db.addPlayerGame(idGame,idPlayer);
            scoreManage.put(idPlayer,scorePlayer);
            return true;
        }
        return false;
        
    }
    
    //Obtient la liste des id des joueurs de la partie
    public ArrayList<Integer> getPlayers()
    {
        return players;
    }
    
    //Supprime un joueur de la partie
    public boolean removePlayer(int idPlayer)
    {
        players.remove(idPlayer);
        scoreManage.remove(idPlayer);
        return true;
    }
    
    //Recupère l'id du jeu
    public int getIDGame()
    {
        return idGame;
    }
    
    //Démarre le jeu
    public void startGame()
    {
        //Change le status et modifie dans la base de donnée
        status = Status.PLAY;
        try
        {
            JYahtzeeServer.db.changeState(idGame,status.name());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        //Choisi le premier joueur
        playerXTurn = Random.randomValue(0, players.size());
        
    }
    
    //Lance les dés pour d'une list de dés
    public void rolle(int[] dieToReRoll)
    {
        for(int i : dieToReRoll)
        {
            dice[i].roll();
        }
    }
    
    //Lance un dés selon le numéro du dés reçu
    public int rolle(int dieToReRoll)
    {
        //Renvoi la valeur obtenu
        dice[dieToReRoll].roll();
        return dice[dieToReRoll].getValue();
    }
    
    //Choix de la case choisie par le joueur
    public boolean playCase(int idPlayer, int choice)
    {
        int score;
        score = playChoice(idPlayer, dice, choice);
        if(score != -1)
        {
            for(int i = 0 ; i < players.size(); i++)
            {
                //Va envoyer la mise à jour de la vue aux joueurs
                JYahtzeeServer.server.sendUpdateVue(idPlayer, score, choice);
            }
            
            //Passe au joueur suivant
            playerXTurn = (playerXTurn + 1) % players.size();
            //Envoi uniquement au joueur concerne l'info de son tour
            JYahtzeeServer.server.sendTurnPlayer(players.get(playerXTurn));
                        
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //Calcul le score selon son choix et la valeur des dés
    public int playChoice(int idPlayer, Die[] dice, int choice)
    {
        ScoreManager score = scoreManage.get(idPlayer);
        return score.choicePlay(dice, choice);
    }
}
