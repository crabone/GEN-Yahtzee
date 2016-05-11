package com.mycompany.jyahtzee.client.gui.loginWindow;

import com.mycompany.jyahtzee.client.gui.newAccountWindow.NewAccountWindowController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Mado on 11.05.2016.
 */
public class LoginWindowController {
    @FXML
    private Pane mainPane;

    @FXML
    private void initialize() {

    }

    @FXML
    private void newAccount() throws Exception{
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(NewAccountWindowController.class.getResource("NewAccountWindow.fxml"));
        Pane pane = loader.load();

        stage.setTitle("Inscription");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(pane));
        stage.showAndWait();

    }


    @FXML
    private void closeApp(){
        Platform.exit();
        ((Stage)mainPane.getScene().getWindow()).close();
    }


}
