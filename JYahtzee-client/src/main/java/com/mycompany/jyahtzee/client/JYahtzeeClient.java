package com.mycompany.jyahtzee.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class JYahtzeeClient extends Application {

    private static Stage mainStage;
    private static Pane mainPane;

    public static void main(String[] args) throws IOException {
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
