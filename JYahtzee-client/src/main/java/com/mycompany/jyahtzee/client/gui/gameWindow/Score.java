package com.mycompany.jyahtzee.client.gui.gameWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Projet : Jyahtzee
 * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
 * Date : 15.06.16
 * Version : 3.5
 * Description : définition d'un score d'une partie de Yahtzee
 */
public class Score {
    private StringProperty name;
    private StringProperty yourScore;
    private StringProperty hisScore;

    public Score(String name, String yourScore, String hisScore) {
        this.name = new SimpleStringProperty(name);
        this.yourScore = new SimpleStringProperty(yourScore);
        this.hisScore = new SimpleStringProperty(hisScore);
    }

    public StringProperty getNameProperty (){
        return name;
    }

    public StringProperty getYourScoreProperty (){
        return yourScore;
    }

    public StringProperty getHisScoreProperty (){
        return hisScore;
    }
}
