package com.mycompany.jyahtzee.client.gui.mainWindow;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Mado on 01.06.2016.
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
