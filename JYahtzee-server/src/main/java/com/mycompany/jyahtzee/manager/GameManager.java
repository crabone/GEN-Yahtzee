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
    HashMap<Integer,Game> games = new HashMap<>();
    

    public GameManager()
    {
    }
    
    
    
    public boolean createGame()
    {
        Game newGame = new Game(player);
        games.put(newGame.getID(),newGame);
        return false;
    }
    
    public boolean joinGame(int id)
    {
        Game game = games.get(id);
        game.addPlayer(player);
        
        return false;
    }
    
    public boolean observeGame(int id)
    {
        return false;
    }
    
}
