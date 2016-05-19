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
public class Die {
    
    private int value;
    
    public Die()
    {
        roll();
    }
    
    public int getValue()
    {
        return value;
    }
    
    public void roll()
    {
        value = Random.randomValue(1, 6);
    }
    
}
