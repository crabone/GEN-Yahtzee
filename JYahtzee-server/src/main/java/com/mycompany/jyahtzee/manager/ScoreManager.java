
package com.mycompany.jyahtzee.manager;

import java.util.Arrays;


/**
 * Projet : Jyahtzee
 * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
 * Date : 15.06.16
 * Version : 3.5
 * Description : Cette classe permet de gerer le calcul de score
 * Sources : //http://halls-of-valhalla.org/beta/codes/yahtzee-game-in-java,83/
             //http://www.codeproject.com/Articles/8657/A-Simple-Yahtzee-Game
 */
public class ScoreManager 
{
    private int[] score;
    private enum Posibility{ONE,TWO,THREE,FOUR,FIVE,SIX,YATZEE};
    
    
    /**
     * Constructeur
     */
    public ScoreManager()
    {
        score = new int[15];
        for(int i : score)
        {
            i = -1;
        }
    }
    
    /**
     * méthode permettant de récupérer le score total
     * @return le total 
     */
    public int getScoreTotal()
    {
        int total = 0;
        for(int i : score)
        {
            total += i;
        }
        
        return total;
    }
    /**
     * Cette methode renvoie le tableu de score
     * @return le tableau de score
     */
    public int[] getTabPlayer()
    {
        return score;
    }

    /**
     * Calcul du score en fonction d'une zone 
     * @param dice le tableau de dés
     * @param zone la zone jouer
     * @return 
     */
    public int choicePlay(Die[] dice, int zone)
    {        
        if(score[zone] != -1)
        {
            return 0;
        }
        switch(zone)
        {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:

                score[zone] = oneNumber(dice, zone);
            case 7:
            case 8:  
                ;
        }
        return score[zone];
    }
    
    /**
     * Cette méthode calcul le score obtenu pour une zone
     * @param dice
     * @param zone
     * @return 
     */
    public int oneNumber(Die[] dice, int zone)
    {
        int score = 0;
        for (int i = 0; i < dice.length; ++i){
             if (dice[i].getValue() == zone){
                 score += zone;
             }
        }
        return score;
    }
    
    /**
     * methode calculant le score lorsqu'on a trois dés identiques
     * @param dice le tableau representant les cinq dés
     * @return le score obtenu
     */
    public int brelan(Die[] dice){
        int cpte;
        int score = 0;
        for( int valueDice = 1; valueDice < 7; valueDice++ ){
            cpte = 0;
            for (int i = 0; i < dice.length; ++i){
                if (dice[i].getValue() == valueDice){
                    cpte++;
                }
                // Lorsqu'il ya trois dés identiques de tous les dés
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
    
    /**
     * methode calculant le score lorsqu'on a quatre dés identiquess
     * @param dice le tableau representant les cinq dés
     * @return le score obtenu
     */
     public int square(Die[] dice){
        int cpte;
        int score = 0;
        // boucle parcourant les différentes valeurs possible d'un dés
        for( int valueDice = 1; valueDice < 7; valueDice++ ){
            cpte = 0;
            // on compare la valeur d'un dé avec l'une des valeurs possibles
            for (int i = 0; i < dice.length; ++i){
                if (dice[i].getValue() == valueDice){
                    cpte++;
                }
                // Lorsqu'il ya quatre dés identiques de tous les dés
                if (cpte == 4){
                    for (int k = 0; k < dice.length; ++k){
                        score += dice[k].getValue();
                    }
                    return score;
                }
            }
            
        }
        return score;
    }
    
    /**
     * methode calculant le score lorsqu'on a trois dés identique et deux dés identiques
     * @param dice le tableau representant les cinq dés
     * @return le score obtenu
     */
    public int fullHouse(Die[] dice){
        int[] die = sortDiceArray(dice);
        // on compare les trois dé successifs puis les deux dernier dés 
        if (die[0] == die[1] && die[1] != die[2]
           && (die[2] == die[3] && dice[3].getValue() == dice[4].getValue())
           || (die[0] == die[1] && die[1] == die[2] && die[2] != die[3])
           && (die[3] == die[4])){
            return 25;
        }
        return 0;
    }
    
    /**
     * methode calculant le score lorsqu'on a cinq dés identiques
     * @param dice le tableau representant les cinq dés
     * @return le score obtenu
     */
    public int yahtzee(Die[] dice){
        // on compare la valeur d'un dé avec l'une des valeurs possibles 
        // puis on compte si les valeurs coincident
        for( int valueDice = 1; valueDice < 7; valueDice++ ){
          int cpte = 0;
          for( int j = 0; j < dice.length; j++ ){
            if( dice[j].getValue() == valueDice )
              cpte++;
            // si le compteur est égale à 5 c'est a dire 5 dés identiques
            if( cpte == 5 )
              return 50;
          }
        }
        return 0;
    }

    /**
     * methode calculant le score lorsqu'on a une grande suite soit
     * 1,2,3,4,5 ou 2,3,4,5,6
     * @param dice le tableau representant les cinq dés
     * @return le score obtenu
     */
    public int largeStraight(Die[] dice){
        // tri du tableau
        int[] die = sortDiceArray(dice);
        boolean isLargeStraight = true;
        int j = 1;
        int k = 2;
        // on test les 5 dés successifs si on a un dé qui coincide pas dans l'une des
        //deux suite on sort de la boucle
        for (int i = 0; i < die.length; ++i){
            if ((die[i] != ++k) || (die[i] != ++j)){
                isLargeStraight = false;
                break;
            }
        }
        
        // On renvoie un score de 40 lorsqu'il y a une grande suite.
        if (isLargeStraight)
            return 40;
        return 0;
    }
    
     /**
     * methode calculant le score lorsqu'on a une grande suite soit
     * 1,2,3,4 ; 2,3,4,5 ou 3,4,5,6
     * @param dice le tableau representant les cinq dés
     * @return le score obtenu
     */
    public int smallStraight(Die[] dice){
        int[] die = sortDiceArray(dice);
        int tmp ;
        
        // on place tous les dés de meme valeurs a la fin du tableau
        for (int i = 0; i < die.length - 1; ++i){
            if (die[i] == die[i+1]){
                tmp = die[i];
                for(int j = i; j < 4; j++){
                    die[j] = die[j + 1];
                }
                die[die.length - 1] = tmp;
            }
        }
        
        // pour les quatres premiers dés on vérifie si il respecte une petite suite
        for(int i = 1; i <= 4; i++){
            int j = i;
            if ((die[0] == j) && (die[1] == ++j) && (die[2] == ++j) && (die[3] == ++j))
                return 30;
        }
        return 0;
    }
    
    /**
     * methode calculant le score lorsqu'il ya de spécial
     * @param dice le tableau representant les cinq dés
     * @return le score obtenu
     */
    public int chance( Die[] dice ){
        int score = 0;
        for( int i = 0; i < dice.length; i++ ){
          score += dice[i].getValue();
        }
        return score;
    }
    
    /**
     * methode permettant de trier dans l'ordre croissant le tableau de dés
     * @param dice le tableau representant les cinq dés
     * @return le score obtenu
     */
    public int[] sortDiceArray(Die[] dice){
        int[] die = new int[5];
        for (int i = 0; i < dice.length; i++){
            die[i] = dice[i].getValue();
        }
        Arrays.sort(die);
        return die;
    }
}
