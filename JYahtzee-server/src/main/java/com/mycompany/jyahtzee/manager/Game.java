/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jyahtzee.manager;

import com.mycompany.jyahtzee.server.JYahtzeeServer;
import com.mycompany.jyahtzee.server.database.Database;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;


public class Game extends Observable
{
    private int idGame;
    private int playerXTurn;
    private Status status;
    private Die[] dice;
    private enum Status{OPEN,PLAY,CLOSE};
    private HashMap<Integer,Player> players;
    private HashMap<Integer,Player> observers;
    private HashMap<Integer,ScoreManager> scoreManage;
    private ArrayList<Integer> listPlayers;
    
    public Game(int idPlayer)
    {
        status = Status.OPEN;
        idGame = JYahtzeeServer.db.newGame(status.name());
        players = new HashMap<>();
        observers = new HashMap<>();
        scoreManage  = new HashMap<>();
        listPlayers = new ArrayList<>();
        players.put(player.getID(),player);
        
        dice = new Die[5];
        playerXTurn = 0;
        listPlayers.add(player.getID());
        
        for(int i = 0 ; i < 5 ; i++)
        {
            dice[i] = new Die();
        }
        
    }
    
    
    public boolean addPlayer(Player player)
    {
        if(status == Status.OPEN)
        {
            ScoreManager scorePlayer = new ScoreManager();
            players.put(player.getID(),player);
            scoreManage.put(player.getID(),scorePlayer);
            listPlayers.add(player.getID());
        }
        return false;
        
    }
    
    public HashMap<Integer,Player> getPlayers()
    {
        return players;
    }
    public boolean removePlayer(Player player)
    {
        players.remove(player.getID());
        scoreManage.remove(player.getID());
        listPlayers.remove(player.getID());
        
        return false;
    }
    
    public int getIDGame()
    {
        return idGame;
    }
    
    public void startGame()
    {
        int i;
        for(i = 0 ; i < 5 ; i++)
        {
            dice[i].roll();
        }
        
        playerXTurn = Random.randomValue(0, players.size());
        
        this.run();
    }
    
    public void reRolle(int[] dieToReRoll)
    {
        for(int i : dieToReRoll)
        {
            dice[i].roll();
        }
    }
    
    public boolean playChoice(Player player, int[] dice, int choice)
    {
        int index;
    }
}
