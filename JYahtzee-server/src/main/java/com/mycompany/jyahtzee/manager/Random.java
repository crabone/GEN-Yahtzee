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
public class Random 
{
    public static int randomValue(int valMin, int valMax)
    {
        return (int)(Math.random() * valMax + valMin);
    }
    
}
