package com.mycompany.jyahtzee.manager;

import com.mycompany.jyahtzee.server.JYahtzeeServer;
import java.util.HashMap;
import com.mycompany.jyahtzee.server.transport.MultiThreadedServer;
import java.sql.SQLException;

/**
 *
 * @author Kevin
 */
public class GameManager 
{
    HashMap<Integer,Game> games;

    public GameManager()
    {
        games = new HashMap<>();
    }      
    
    public int createGame(int idPlayer) {
        try
        {
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
    
    //id game
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
    
    public void rollInGame(int idGame)
    {
        Game game = games.get(idGame);
    }

}
