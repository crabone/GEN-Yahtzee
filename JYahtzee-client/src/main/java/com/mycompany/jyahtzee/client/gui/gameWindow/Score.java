package com.mycompany.jyahtzee.client.gui.gameWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Mado on 29.05.2016.
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
