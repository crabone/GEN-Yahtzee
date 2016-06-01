package com.mycompany.jyahtzee.client.gui.mainWindow;

import com.mycompany.jyahtzee.client.JYahtzeeClient;
import com.mycompany.jyahtzee.client.gui.gameWindow.Score;
import com.mycompany.jyahtzee.client.transport.Client;
import com.mycompany.jyahtzee.client.transport.Communication;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mado on 11.05.2016.
 */
public class MainWindowController {

    @FXML
    private TableView<Partie> tabPartie;
    @FXML
    private TableColumn<Partie, String> numPartie;
    @FXML
    private TableColumn<Partie, String> etat;
    @FXML
    private TableColumn<Partie, String> joueur1;

    @FXML
    private void initialize() throws Exception
    {
        // Afficher liste de partie a rejoindre
        Client client = Client.getInstance();
        Communication com = new Communication(client);

/*
        ObservableList<Partie> parties = FXCollections.observableArrayList();

        ArrayList<ArrayList<String>> listGameCreated = new ArrayList<ArrayList<String>>();
        boolean ok = com.getGames(listGameCreated);
        if(ok) {
            for(int i = 0; i < listGameCreated.size(); i++) {
                ObservableList<String> tmp = FXCollections.observableArrayList();
                String numeroPartie = (listGameCreated.get(i)).get(0);
                String etatPartie = (listGameCreated.get(i)).get(1);
                String joueur1Partie = (listGameCreated.get(i)).get(2);

                parties.add(new Partie(numeroPartie, etatPartie, joueur1Partie));
            }
            numPartie.setCellValueFactory(param -> param.getValue().getNumPartieProperty());
            etat.setCellValueFactory(param -> param.getValue().getEtatPartieProperty());
            joueur1.setCellValueFactory(param -> param.getValue().getJoueur1PartieProperty());

            tabPartie.setItems(parties);
        }
*/

        ObservableList<Partie> parties = FXCollections.observableArrayList();
        for(int i = 0; i < 3; i++) {
            String numeroPartie = "34";
            String etatPartie = "en attente";
            String joueur1Partie = "John";

            parties.add(new Partie(numeroPartie, etatPartie, joueur1Partie));
        }

        numPartie.setCellValueFactory(param -> param.getValue().getNumPartieProperty());
        etat.setCellValueFactory(param -> param.getValue().getEtatPartieProperty());
        joueur1.setCellValueFactory(param -> param.getValue().getJoueur1PartieProperty());

        tabPartie.setItems(parties);

    }


    // fonction liée au bouton "créer une partie" de la fenetre MainWindow ou "Yahtzee"
    // sous l'onglet "Accueil"
    @FXML
    private void createNewGame() throws Exception {
        Client client = Client.getInstance();
        Communication com = new Communication(client);
        boolean ok = com.create();
        if(ok) {
            FXMLLoader loader = new FXMLLoader(JYahtzeeClient.class.getResource("gui/gameWindow/GameWindow.fxml"));

            try {
                JYahtzeeClient.setMainStage(loader.load(), "Yahtzee");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Il y a eu une erreur lors de la creation de la partie");
            alert.showAndWait();
        }
    }

    @FXML
    private void testGameWindow(){ //TODO elever a la fin du projet
        FXMLLoader loader = new FXMLLoader(JYahtzeeClient.class.getResource("gui/gameWindow/GameWindow.fxml"));

        try {
            JYahtzeeClient.setMainStage(loader.load(), "Yahtzee");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
