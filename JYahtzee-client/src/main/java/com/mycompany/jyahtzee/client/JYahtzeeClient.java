package com.mycompany.jyahtzee.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class JYahtzeeClient extends Application {

    private Stage mainStage;
    private Pane mainPane;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setTitle("Yahtzee");

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(JYahtzeeClient.class.getResource("gui/mainWindow/MainWindow.fxml"));

        mainPane = loader.load();

        mainStage.setScene(new Scene(mainPane));
        mainStage.show();
    }
}
