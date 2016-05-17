/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jyahtzee.manager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Kevin
 */
public class Game
{
    private int idGame;
    private HashMap<Integer,Player> players = new HashMap<>();
    private HashMap<Integer,Player> observers = new HashMap<>();

    public Game(Player player)
    {
        players.put(player.getID(),player);
    }
    
    public boolean addPlayer(Player player)
    {
        players.put(player.getID(),player);
        return false;
        
    }
    
    public HashMap<Integer,Player> getPlayers()
    {
        return players;
    }
    public boolean removePlayer(Player player)
    {
        players.remove(player.getID());
        
        return false;
    }
    
    public int getID()
    {
        return idGame;
    }
    
}
