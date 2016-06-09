/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jyahtzee.manager;

import com.mycompany.jyahtzee.server.JYahtzeeServer;
import com.mycompany.jyahtzee.server.database.Database;
import java.sql.SQLException;
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
    
    public Game() throws SQLException
    {
        status = Status.OPEN;
        idGame = JYahtzeeServer.db.newGame(status.name());
       
        
        players = new ArrayList<>();
        observers = new ArrayList<>();
        scoreManage  = new HashMap<>();
        
        dice = new Die[5];
        playerXTurn = 0;
        
        for(int i = 0 ; i < 5 ; i++)
        {
            dice[i] = new Die();
        }
    }
    
    
    public boolean addPlayer(int idPlayer)throws SQLException
    {
        if(status == Status.OPEN)
        {
            ScoreManager scorePlayer = new ScoreManager();
            players.add(idPlayer);            
            JYahtzeeServer.db.addPlayerGame(idGame,idPlayer);
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
        try
        {
            JYahtzeeServer.db.changeState(idGame,status.name());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        playerXTurn = Random.randomValue(0, players.size());
        //JYahtzeeServer.server
        
    }
    
    public void rolle(int[] dieToReRoll)
    {
        for(int i : dieToReRoll)
        {
            dice[i].roll();
        }
    }
    
    
    public int rolle(int dieToReRoll)
    {
        dice[dieToReRoll].roll();
        return dice[dieToReRoll].getValue();
    }
    
    public boolean playCase(int idPlayer, int choice)
    {
        int score;
        score = playChoice(idPlayer, dice, choice);
        if(score != -1)
        {
            for(int i = 0 ; i < players.size(); i++)
            {
                JYahtzeeServer.server.sendUpdateVue(idPlayer, score, choice);
            }
            
            playerXTurn = (playerXTurn + 1) % players.size();
            JYahtzeeServer.server.sendTurnPlayer(players.get(playerXTurn));
            
            
            return true;
        }
        else
        {
            return false;
        }
    }
    public int playChoice(int idPlayer, Die[] dice, int choice)
    {
        ScoreManager score = scoreManage.get(idPlayer);
        return score.choicePlay(dice, choice);
    }
    
}
