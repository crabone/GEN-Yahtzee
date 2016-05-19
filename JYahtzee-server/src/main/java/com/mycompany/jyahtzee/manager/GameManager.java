/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public boolean createGame()
    {
        Game newGame = new Game(player);
        games.put(newGame.getIDGame(),newGame);
        return false;
    }
    
    //id game
    public boolean joinGame(int id)
    {
        Game game = games.get(id);
        game.addPlayer(player);
        
        return false;
    }
    
    //id game
    public boolean observeGame(int id)
    {
        return false;
    }
    
}
