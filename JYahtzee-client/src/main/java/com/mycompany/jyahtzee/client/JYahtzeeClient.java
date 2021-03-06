package com.mycompany.jyahtzee.client;

import com.mycompany.jyahtzee.client.transport.CommRequired;
import com.mycompany.jyahtzee.client.transport.Communication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Projet : Jyahtzee
 * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
 * Date : 15.06.16
 * Version : 3.5
 * Description : Classe principale
 */
public class JYahtzeeClient extends Application {

    private static Stage mainStage;
    private static Pane mainPane;

    public static Communication threadCom;
    public static CommRequired com;

    public static void main(String[] args) throws IOException {
        com = new CommRequired();
        threadCom = new Communication();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setTitle("Yahtzee");

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(JYahtzeeClient.class.getResource("gui/loginWindow/LoginWindow.fxml"));

        mainPane = loader.load();

        setMainStage(mainPane, "Login");
        mainStage.setResizable(false);
    }

    public static void setMainStage(Pane pane, String name){
        mainStage.hide();
        mainPane = pane;
        mainStage.setTitle(name);
        mainStage.setScene(new Scene(mainPane));
        mainStage.show();
    }
}
