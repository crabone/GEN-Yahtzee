/**
 * Projet : Jyahtzee
 * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
 * Date : 15.06.16
 * Version : 3.5
 * Description : Cette class gère les parties qui ont été créés par les joueurs
 */

package com.mycompany.jyahtzee.manager;

import com.mycompany.jyahtzee.server.JYahtzeeServer;
import java.util.HashMap;
import java.sql.SQLException;

public class GameManager 
{
    HashMap<Integer,Game> games;

    public GameManager()
    {
        games = new HashMap<>();
    }      
    
    //Création d'un nouveau jeu
    public int createGame(int idPlayer) {
        try
        {
            //Création de la partie dans la base de données
            JYahtzeeServer.db.connect();
            Game newGame = new Game();         
            games.put(newGame.getIDGame(), newGame);
            JYahtzeeServer.db.disconnect();
            return newGame.getIDGame();
        }
        catch(SQLException e)
        {
            System.out.println("Erreur création d'une partie " + e.getMessage());
            return 0;            
        }
    }

    //Récupère le game selon id
    public Game getGame(int idGame) {
        return games.get(idGame);
    }
    
    //Rejoindre un jeu selon son id
    public boolean joinGame(int idGame, int idPlayer) {
        try
        {
            boolean ret;
            Game game = games.get(idGame);
            if(game == null)
            {
                return false;
            }
            JYahtzeeServer.db.connect();
            //Ajoute le joueur dans la db
            ret = game.addPlayer(idPlayer);
            JYahtzeeServer.db.disconnect();
            return ret;
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    //Reçois la case jouer par le joueur
    public boolean decisionPlayer(int idGame,int idPlayer,int idCase)
    {
        Game game = games.get(idGame);
        return game.playCase(idPlayer, idCase);        
        
    }
    
    //Ajout un observeur au jeu
    public boolean observeGame(int idGame, int idObserver)
    {
        try
        {
            Game game = games.get(idGame);
            return game.addPlayer(idObserver);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    //Lance un dés
    public void rollInGame(int idGame)
    {
        Game game = games.get(idGame);
    }

}
