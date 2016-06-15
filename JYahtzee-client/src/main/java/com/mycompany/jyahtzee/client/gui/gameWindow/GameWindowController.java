package com.mycompany.jyahtzee.client.gui.gameWindow;

import com.mycompany.jyahtzee.client.JYahtzeeClient;
import com.mycompany.jyahtzee.client.transport.Protocole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


/**
        * Projet : Jyahtzee
        * @author Rosanne Combremont, Madolyne Dupraz, Kevin Ponce, Fabien Franchini, Ibrahim Ounon
        * Date : 15.06.16
        * Version : 3.5
        * Description : Cette classe gère les différentes actions faites sur la fenêtre de jeu
*/
public class GameWindowController {

    @FXML
    private TableView<Score> scoresTable;
    @FXML
    private TableColumn<Score, String> nameCol;
    @FXML
    private TableColumn<Score, String> yourCol;
    @FXML
    private TableColumn<Score, String> hisCol;

    @FXML
    private Label de0;
    @FXML
    private Label de1;
    @FXML
    private Label de2;
    @FXML
    private Label de3;
    @FXML
    private Label de4;

    @FXML
    private CheckBox cb0;
    @FXML
    private CheckBox cb1;
    @FXML
    private CheckBox cb2;
    @FXML
    private CheckBox cb3;
    @FXML
    private CheckBox cb4;

    @FXML
    public void initialize(){
        ObservableList<Score> scores = FXCollections.observableArrayList();

        scores.add(new Score("Uns (Total des 1)", "", ""));
        scores.add(new Score("Deux (Total des 2)", "", ""));
        scores.add(new Score("Trois (Total des 3)", "", ""));
        scores.add(new Score("Quatres (Total des 4)", "", ""));
        scores.add(new Score("Cinqs (Total des 5)", "", ""));
        scores.add(new Score("Six (Total des 6)", "", ""));

        scores.add(new Score("Brelan (Total des 3 dés)", "", ""));
        scores.add(new Score("Carré (Total des 4 dés)", "", ""));
        scores.add(new Score("Full (25 points)", "", ""));
        scores.add(new Score("Petite Suite (30 points)", "", ""));
        scores.add(new Score("Grande Suite (40 points).", "", ""));
        scores.add(new Score("Yahtzee (50 points)", "", ""));
        scores.add(new Score("Chance (Total des 5 dés)", "", ""));

        scores.add(new Score("Total des points", "", ""));


        nameCol.setCellValueFactory(param -> param.getValue().getNameProperty());
        yourCol.setCellValueFactory(param -> param.getValue().getYourScoreProperty());
        hisCol.setCellValueFactory(param -> param.getValue().getHisScoreProperty());

        scoresTable.setItems(scores);

        boolean clickedEnable = true;

        scoresTable.setRowFactory(param -> {
            TableRow<Score> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && clickedEnable){
                    //Ici insérer code pour score. ligne sélectionnée : row.getIndex();
                    // désactiver le clique sur les scores : clickedEnable = false;

                }
            });
            return row;
        });

        // ligne pour modifier score
        // scoresTable.getItems().get(0).getNameProperty().setValue("Yell");

    }

    // Ceci est liée au bouton "Lancer les dés" de la fenetre de jeu
    // Ici tu met les méthodes qui faut pour le lancer de dés.
    @FXML
    private void rollDiceButton() throws Exception{
        // Pour cela tu as la valeur actuelle des dés qui sont défini par des label
        // de0, de1, de2, de3, de4
        // Ainsi que les checkbox correspondante (si la checkbox est cochée, les dés ne seront pas lancés.
        // cb0, cb1, cb2, cb3, cb4

        if(!cb0.isSelected()) {
            JYahtzeeClient.com.setId("0");
            JYahtzeeClient.com.setAbout(Protocole.CMD_ROLL);
            synchronized (JYahtzeeClient.com) {
                JYahtzeeClient.com.notify();
            }
            synchronized (JYahtzeeClient.com) {
                JYahtzeeClient.com.wait();
            }
            int value = JYahtzeeClient.com.getResultInt();
            JYahtzeeClient.com.clearVar();
            de0.setText(Integer.toString(value));
        }

        if(!cb1.isSelected()) {
            JYahtzeeClient.com.setId("1");
            JYahtzeeClient.com.setAbout(Protocole.CMD_ROLL);
            synchronized (JYahtzeeClient.com) {
                JYahtzeeClient.com.notify();
            }
            synchronized (JYahtzeeClient.com) {
                JYahtzeeClient.com.wait();
            }
            int value = JYahtzeeClient.com.getResultInt();
            JYahtzeeClient.com.clearVar();
            de1.setText(Integer.toString(value));
        }

        if(!cb2.isSelected()) {
            JYahtzeeClient.com.setId("2");
            JYahtzeeClient.com.setAbout(Protocole.CMD_ROLL);
            synchronized (JYahtzeeClient.com) {
                JYahtzeeClient.com.notify();
            }
            synchronized (JYahtzeeClient.com) {
                JYahtzeeClient.com.wait();
            }
            int value = JYahtzeeClient.com.getResultInt();
            JYahtzeeClient.com.clearVar();
            de2.setText(Integer.toString(value));
        }

        if(!cb3.isSelected()) {
            JYahtzeeClient.com.setId("3");
            JYahtzeeClient.com.setAbout(Protocole.CMD_ROLL);
            synchronized (JYahtzeeClient.com) {
                JYahtzeeClient.com.notify();
            }
            synchronized (JYahtzeeClient.com) {
                JYahtzeeClient.com.wait();
            }
            int value = JYahtzeeClient.com.getResultInt();
            JYahtzeeClient.com.clearVar();
            de3.setText(Integer.toString(value));
        }

        if(!cb4.isSelected()) {
            JYahtzeeClient.com.setId("4");
            JYahtzeeClient.com.setAbout(Protocole.CMD_ROLL);
            synchronized (JYahtzeeClient.com) {
                JYahtzeeClient.com.notify();
            }
            synchronized (JYahtzeeClient.com) {
                JYahtzeeClient.com.wait();
            }
            int value = JYahtzeeClient.com.getResultInt();
            JYahtzeeClient.com.clearVar();
            de4.setText(Integer.toString(value));
        }

        //bool cb1.isSelected();

        // mettre la valeur 3 à ton score dans la ligne 0.
        // scoresTable.getItems().get(0).getYourScoreProperty().setValue("3");
    }
}
