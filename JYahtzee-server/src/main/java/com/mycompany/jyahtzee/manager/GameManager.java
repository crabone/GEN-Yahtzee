/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jyahtzee.manager;

import com.mycompany.jyahtzee.server.transport.MultiThreadedServer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin
 */
public class GameManager 
{
    ArrayList<Game> games = new ArrayList();
    
    public boolean creationPartie()
    {
        Game newGame = new Game(joueur);
        games.add(newGame);
        return false;
    }
    
    public void joindrePartie()
    {
        
    }
    
}
