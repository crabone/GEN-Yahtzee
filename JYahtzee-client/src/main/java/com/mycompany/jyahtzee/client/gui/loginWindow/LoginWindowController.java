package com.mycompany.jyahtzee.client.gui.loginWindow;

import com.mycompany.jyahtzee.client.JYahtzeeClient;
import com.mycompany.jyahtzee.client.gui.newAccountWindow.NewAccountWindowController;
import com.mycompany.jyahtzee.client.transport.Client;
import com.mycompany.jyahtzee.client.transport.Communication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Created by Mado on 11.05.2016.
 */
public class LoginWindowController {

    private JYahtzeeClient jYahtzeeClient;

    @FXML
    private Pane mainPane;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private TextField ipServer;


    // Fonction dont je pensais avoir besoin, mais pour le moment ne sert a rien :)
    @FXML
    private void initialize() {

    }

    // Ca c'est la création de ma fenêtre,
    // Ne rien mettre dedans! c'est mon business!
    @FXML
    private void newAccount() throws Exception{
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader(NewAccountWindowController.class.getResource("NewAccountWindow.fxml"));
        Pane pane = loader.load();

        stage.setTitle("Inscription");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(pane));
        stage.showAndWait();
    }

    // Fonction liées au bouton "Entrer" de la fenêtre LoginWindows aussi nommée: "Authentification"
    // Ici tu met les méthodes qu'il faut pour qu'un client puisse se connecter avec un compte déjà créé
    @FXML
    private void authentificationClient () throws Exception {

        Client client = new Client("localhost", 4321);
        client.connect();
        Communication com = new Communication(client);
        boolean ok = com.authentification(login.getText(), password.getText());

        if (ok){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Votre avez été loggé avec succes");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(JYahtzeeClient.class.getResource("gui/mainWindow/MainWindow.fxml"));


            try {
                JYahtzeeClient.setMainStage(loader.load(), "Yahtzee");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Il y a eu une erreur lors de la connexion à votre compte");
            alert.showAndWait();
        }
    }

    // Ceci est liée au bouton "Quitter" de la fenetre LoginWindows aussi nomnée "Authentification"
    // le fenetre se ferme rien besoin de faire ici...
    @FXML
    private void closeApp(){
        Platform.exit();
        ((Stage)mainPane.getScene().getWindow()).close();
    }

    @FXML
    private void skipLogin(){
        FXMLLoader loader = new FXMLLoader(JYahtzeeClient.class.getResource("gui/mainWindow/MainWindow.fxml"));

        try {
            JYahtzeeClient.setMainStage(loader.load(), "Yahtzee");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setjYahtzeeClient(JYahtzeeClient jYahtzeeClient){
        this.jYahtzeeClient = jYahtzeeClient;
    }


}
