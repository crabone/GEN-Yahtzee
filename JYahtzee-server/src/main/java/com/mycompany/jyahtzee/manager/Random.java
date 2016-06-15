/**
 * Projet : Jyahtzee
 * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
 * Date : 15.06.16
 * Version : 3.5
 * Description : Cette class gère le random de valeur. Extrémité comprise
 *               Dans le projet utilisé pour choisir le premier joueur et le 
 *               random des dés
 */
package com.mycompany.jyahtzee.manager;

public class Random 
{
    //Permet de faire un random de valeur borne comprise
    public static int randomValue(int valMin, int valMax)
    {
        return (int)(Math.random() * valMax + valMin);
    }
    
}
