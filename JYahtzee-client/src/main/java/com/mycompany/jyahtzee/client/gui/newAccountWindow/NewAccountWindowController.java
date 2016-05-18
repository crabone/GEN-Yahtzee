package com.mycompany.jyahtzee.client.gui.newAccountWindow;

import com.mycompany.jyahtzee.client.transport.Client;
import java.io.IOException;
import javafx.fxml.FXML;
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


    @FXML
    private void cancel()
    {
        ((Stage)mainPane.getScene().getWindow()).close();
    }

    // bien entendu nom a changer...exemple pour Fafa
    @FXML
    private void blabla() throws IOException {
        Client client = new Client("localhost", 4321); // A changer pour les bonnes valeures
        client.register(login.getText());
        
    }
}
