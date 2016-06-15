package com.mycompany.jyahtzee.client.gui.mainWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Projet : Jyahtzee
 * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
 * Date : 15.06.16
 * Version : 3.5
 * Description : définition d'une partie
 */
public class Partie {

    private StringProperty numPartie;
    private StringProperty etatPartie;
    private StringProperty joueur1Partie;

    public Partie(String numPartie, String etatPartie, String joueur1Partie) {
        this.numPartie = new SimpleStringProperty(numPartie);
        this.etatPartie = new SimpleStringProperty(etatPartie);
        this.joueur1Partie = new SimpleStringProperty(joueur1Partie);
    }

    public StringProperty getNumPartieProperty (){
        return numPartie;
    }

    public StringProperty getEtatPartieProperty (){
        return etatPartie;
    }

    public StringProperty getJoueur1PartieProperty (){
        return joueur1Partie;
    }
}
