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

    public boolean joinGame(int idGame, int idPlayer) {
        Game game = games.get(idGame);
        return game.addPlayer(idPlayer);
    }
    
    //id game
    public boolean observeGame(int idGame, int idObserver)
    {
        Game game = games.get(idGame);
        return game.addPlayer(idObserver);
    }
    
    public void rollInGame(int idGame)
    {
        Game game = games.get(idGame);
    }

}
