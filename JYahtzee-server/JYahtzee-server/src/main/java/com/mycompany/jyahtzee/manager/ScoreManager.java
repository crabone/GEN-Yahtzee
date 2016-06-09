/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jyahtzee.manager;

import java.util.Arrays;

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
<<<<<<< HEAD:JYahtzee-server/JYahtzee-server/src/main/java/com/mycompany/jyahtzee/manager/ScoreManager.java
    {     
        int valeurScore;
        if(score[zone] != -1)
        {
            return -1;
=======
    {        
        if(score[zone] != -1)
        {
            return 0;
>>>>>>> origin/master:JYahtzee-server/src/main/java/com/mycompany/jyahtzee/manager/ScoreManager.java
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
                
<<<<<<< HEAD:JYahtzee-server/JYahtzee-server/src/main/java/com/mycompany/jyahtzee/manager/ScoreManager.java
            case 7:
            case 8:  
                return -1;
=======
                score[zone] = oneNumber(dice, zone);
            case 7:
            case 8:  
                ;
>>>>>>> origin/master:JYahtzee-server/src/main/java/com/mycompany/jyahtzee/manager/ScoreManager.java
                            
                
            
        }
<<<<<<< HEAD:JYahtzee-server/JYahtzee-server/src/main/java/com/mycompany/jyahtzee/manager/ScoreManager.java
        return -1;
=======
        return score[zone];
>>>>>>> origin/master:JYahtzee-server/src/main/java/com/mycompany/jyahtzee/manager/ScoreManager.java
    }
    
    public int oneNumber(Die[] dice, int zone)
    {
        int score = 0;
<<<<<<< HEAD:JYahtzee-server/JYahtzee-server/src/main/java/com/mycompany/jyahtzee/manager/ScoreManager.java
        for(int i = 0; i< dice.length ; i++)
        {
            if(dice[i].getValue() == zone)
            {
=======
        for (int i = 0; i < dice.length; ++i){
            if (dice[i].getValue() == zone){
>>>>>>> origin/master:JYahtzee-server/src/main/java/com/mycompany/jyahtzee/manager/ScoreManager.java
                score += zone;
            }
        }
        return score;
<<<<<<< HEAD:JYahtzee-server/JYahtzee-server/src/main/java/com/mycompany/jyahtzee/manager/ScoreManager.java
=======
    }
    
    public int brelan(Die[] dice){
        int cpte;
        int score = 0;
        for( int valueDice = 1; valueDice < 7; valueDice++ ){
            cpte = 0;
            for (int i = 0; i < dice.length; ++i){
                if (dice[i].getValue() == valueDice){
                    cpte++;
                }
                if (cpte == 3){
                    for (int k = 0; k < dice.length; ++k){
                        score += dice[k].getValue();
                    }
                    return score;
                }
            }
            
        }
        return score;
    }
    
     public int square(Die[] dice){
        int cpte;
        int score = 0;
        for( int valueDice = 1; valueDice < 7; valueDice++ ){
            cpte = 0;
            for (int i = 0; i < dice.length; ++i){
                if (dice[i].getValue() == valueDice){
                    cpte++;
                }
                if (cpte == 4){
                    for (int k = 0; k < dice.length; ++k){
                        score += dice[k].getValue();
                    }
                    return score;
                }
            }
            
        }
        return score;
>>>>>>> origin/master:JYahtzee-server/src/main/java/com/mycompany/jyahtzee/manager/ScoreManager.java
    }
    
    public int fullHouse(Die[] dice){
        int[] die = sortDiceArray(dice);
        if (die[0] == die[1] && die[1] != die[2]
           && (die[2] == die[3] && dice[3].getValue() == dice[4].getValue())
           || (die[0] == die[1] && die[1] == die[2] && die[2] != die[3])
           && (die[3] == die[4])){
            return 25;
        }
        return 0;
    }
    
    public int largeStraight(Die[] dice){
        int[] die = sortDiceArray(dice);
        boolean isLargeStraight = true;
        int j = 1;
        int k = 2;
        for (int i = 0; i < die.length; ++i){
            if ((die[i] != ++k) || (die[i] != ++j)){
                isLargeStraight = false;
                break;
            }
        }
        if (isLargeStraight)
            return 40;
        return 0;
    }
    
    public int yahtzee(Die[] dice){
        for( int valueDice = 1; valueDice < 7; valueDice++ ){
          int cpte = 0;
          for( int j = 0; j < dice.length; j++ ){
            if( dice[j].getValue() == valueDice )
              cpte++;
            if( cpte > 4 )
              return 50;
          }
        }
        return 0;
    }
    
    public int smallStraight(Die[] dice){
        int[] die = sortDiceArray(dice);
        int tmp ;
        for (int i = 0; i < die.length - 1; ++i){
            if (die[i] == die[i+1]){
                tmp = die[i];
                for(int j = i; j < 4; j++){
                    die[j] = die[j + 1];
                }
                die[die.length - 1] = tmp;
            }
        }
        for(int i = 1; i <= 4; i++){
            int j = i;
            if ((die[0] == j) && (die[1] == ++j) && (die[2] == ++j) && (die[3] == ++j))
                return 30;
        }
        return 0;
    }
    
    public int chance( Die[] dice ){
        int score = 0;
        for( int i = 0; i < dice.length; i++ ){
          score += dice[i].getValue();
        }

        return score;
    }
    
    public int[] sortDiceArray(Die[] dice){
        int[] die = new int[5];
        for (int i = 0; i < dice.length; i++){
            die[i] = dice[i].getValue();
        }
        Arrays.sort(die);
        return die;
    }
    
   
    
}
