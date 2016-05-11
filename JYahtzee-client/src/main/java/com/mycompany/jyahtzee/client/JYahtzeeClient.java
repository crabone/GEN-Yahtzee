package com.mycompany.jyahtzee.client;

import com.mycompany.jyahtzee.client.gui.mainWindow.MainWindowController;
import com.mycompany.jyahtzee.client.transport.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 *
 * @author crab_one
 */
public class JYahtzeeClient extends Application {
    private Stage mainStage;
    private Pane mainPane;
    
    public static void main(String[] args) throws IOException {
//        Client client = new Client("localhost", 4321);
//        client.connect();
//        client.sendMessage();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        mainStage = primaryStage;
        mainStage.setTitle("Yahtzee");

       FXMLLoader loader = new FXMLLoader();

        loader.setLocation(JYahtzeeClient.class.getResource("gui/mainWindow/MainWindow.fxml"));

        mainPane = loader.load();

        mainStage.setScene(new Scene(mainPane));
        mainStage.show();
    }
}
