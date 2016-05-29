package com.mycompany.jyahtzee.client.game;

import java.util.logging.Logger;

public class Dice {

    private static final Logger LOG = Logger.getLogger(Dice.class.getName());
    
    private int value;

    public Dice(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    public void roll() {
        return;
    }
}
