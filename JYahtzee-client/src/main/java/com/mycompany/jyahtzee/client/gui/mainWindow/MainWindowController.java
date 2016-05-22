package com.mycompany.jyahtzee.client.gui.mainWindow;

import com.mycompany.jyahtzee.client.JYahtzeeClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Mado on 11.05.2016.
 */
public class MainWindowController {

    @FXML
    private void initialize() throws Exception
    {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(JYahtzeeClient.class.getResource("gui/loginWindow/LoginWindow.fxml"));

        Pane pane = loader.load();

//        stage.setOnCloseRequest(event -> {
//            Platform.exit();
//        });
        stage.setTitle("Yahtzee-Login");
        stage.setScene(new Scene(pane));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    // fonction liée au bouton "créer une partie" de la fenetre MainWindow ou "Yahtzee"
    // sous l'onglet "Accueil"
    @FXML
    private void createNewGame() throws Exception {

    }
}
