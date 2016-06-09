/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jyahtzee.manager;

/**
 *
 * @author Kevin
 */

//http://halls-of-valhalla.org/beta/codes/yahtzee-game-in-java,83/

public class ScoreManager 
{
    private int[] score;
    private enum Posibility{ONE,TWO,THREE,FOUR,FIVE,SIX,YATZEE};
    
    public ScoreManager()
    {
        score = new int[15];
        for(int i : score)
        {
            i = -1;
        }
    }
    
    public int getScoreTotal()
    {
        int total = 0;
        for(int i : score)
        {
            total += i;
        }
        
        return total;
    }
    
    public int[] getTabPlayer()
    {
        return score;
    }
    
    public int choicePlay(Die[] dice, int zone)
    {     
        int valeurScore;
        if(score[zone] != -1)
        {
            return -1;
        }
        switch(zone)
        {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                  
                valeurScore = oneNumber(dice, zone);                
                score[zone] = valeurScore;    
                
            case 7:
            case 8:  
                return -1;
                            
                
            
        }
        return -1;
    }
    
    public int oneNumber(Die[] dice, int zone)
    {
        int score = 0;
        for(int i = 0; i< dice.length ; i++)
        {
            if(dice[i].getValue() == zone)
            {
                score += zone;
            }
        }
        return score;
    }
    
}
