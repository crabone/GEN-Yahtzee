package com.mycompany.jyahtzee.client.gui.newAccountWindow;

import com.mycompany.jyahtzee.client.transport.Client;
import java.io.IOException;

import com.mycompany.jyahtzee.client.transport.Communication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * Created by Mado on 11.05.2016.
 */
public class NewAccountWindowController {

    @FXML
    private Pane mainPane;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;


    // Ceci est liée au bouton "Annuler" de la fenetre NewAccount ou aussi nommée "Inscription"
    // la fenetre se ferme, bref rien de plus a faire ici.
    @FXML
    private void cancel()
    {
        ((Stage)mainPane.getScene().getWindow()).close();
    }

    // Ceci est liée au bouton "Valider" de la fenetre NewAccount ou aussi nommée "Inscription"
    // Ici tu met les méthodes qui faut pour que le client puisse se créer un compte!
    @FXML
    private void registerClient() throws Exception {
        Client client = new Client("localhost", 4321);

        client.connect();
        Communication com = new Communication(client);
        boolean ok = com.inscription(login.getText(), password.getText());        

        if (ok){
            ((Stage)mainPane.getScene().getWindow()).close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Votre compte a ete creer avec succes");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Il y a eu une erreur lors de la creation de votre compte");
            alert.showAndWait();
        }
        client.disconnect();
    }
}
