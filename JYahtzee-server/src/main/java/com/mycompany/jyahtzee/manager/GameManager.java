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

    public boolean createGame() {
        //Game newGame = new Game(player);
       // games.put(newGame.getID(), newGame);
        
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
