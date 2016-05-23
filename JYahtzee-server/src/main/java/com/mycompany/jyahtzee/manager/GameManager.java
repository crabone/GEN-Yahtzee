package com.mycompany.jyahtzee.manager;

import java.util.HashMap;

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
    
    public boolean createGame(int idPlayer) {
        Game newGame = new Game(idPlayer);
        games.put(newGame.getIDGame(), newGame);
        
        return false;
    }

    public boolean joinGame(int id) {
        Game game = games.get(id);
        //game.addPlayer(player);

        return false;
    }
    
    //id game
    public boolean observeGame(int id)
    {
        return false;
    }

}
