/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jyahtzee.manager;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class Game
{
    private int id;
    private ArrayList<Joueur> joueurs = new ArrayList<>();

    public Game(Joueur joueur)
    {
        joueurs.add(joueur);
       //id = getID;
    }
    
    public boolean ajouterPlayer(Joueur joueur)
    {
        joueurs.add(joueur);
        return false;
        
    }
    
    public ArrayList<Joueur> getJoueurs()
    {
        return joueurs;
    }
    
}
