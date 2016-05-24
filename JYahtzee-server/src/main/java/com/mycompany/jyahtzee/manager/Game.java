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
    private HashMap<Integer,ScoreManager> scoreManage;
    private ArrayList<Integer> players;
    private ArrayList<Integer> observers;
    
    public Game(int idPlayer)
    {
        status = Status.OPEN;
        idGame = JYahtzeeServer.db.newGame(status.name());
        players = new ArrayList<>();
        observers = new ArrayList<>();
        scoreManage  = new HashMap<>();
        players.add(idPlayer);
        
        dice = new Die[5];
        playerXTurn = 0;
        
        for(int i = 0 ; i < 5 ; i++)
        {
            dice[i] = new Die();
        }
        
    }
    
    
    public boolean addPlayer(int idPlayer)
    {
        if(status == Status.OPEN)
        {
            ScoreManager scorePlayer = new ScoreManager();
            players.add(idPlayer);
            scoreManage.put(idPlayer,scorePlayer);
            return true;
        }
        return false;
        
    }
    
    public ArrayList<Integer> getPlayers()
    {
        return players;
    }
    public boolean removePlayer(int idPlayer)
    {
        players.remove(idPlayer);
        scoreManage.remove(idPlayer);
        return true;
    }
    
    public int getIDGame()
    {
        return idGame;
    }
    
    public void startGame()
    {
        status = Status.PLAY;
        JYahtzeeServer.db.addPlayerGame(idGame,players);
        
        int i;
        for(i = 0 ; i < 5 ; i++)
        {
            dice[i].roll();
        }
        
        playerXTurn = Random.randomValue(0, players.size());
        
        
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
        return false;
    }
}
