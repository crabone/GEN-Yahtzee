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
    
    public boolean choicePlay(int[] dice, int zone)
    {        
        if(score[zone] != -1)
        {
            return false;
        }
        switch(zone)
        {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                
                oneNumber(dice);
            case 7:
            case 8:  
                return false;
                            
                
            
        }
    }
    
    public boolean oneNumber(int[] dice)
    {
        
    }
    
}
