package com.mycompany.jyahtzee.client.gui.gameWindow;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * Created by Mado on 29.05.2016.
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
    private Label de1;
    @FXML
    private Label de2;
    @FXML
    private Label de3;
    @FXML
    private Label de4;
    @FXML
    private Label de5;

    @FXML
    private CheckBox cb1;
    @FXML
    private CheckBox cb2;
    @FXML
    private CheckBox cb3;
    @FXML
    private CheckBox cb4;
    @FXML
    private CheckBox cb5;

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

        // ligne pour modifier score
        // scoresTable.getItems().get(0).getNameProperty().setValue("Yell");

    }

    // Ceci est liée au bouton "Lancer les dés" de la fenetre de jeu
    // Ici tu met les méthodes qui faut pour le lancer de dés.
    @FXML
    private void rollDice() {
        // Pour cela tu as la valeur actuelle des dés qui sont défini par des label
        // de1, de2, de3, de4, de5
        // Ainsi que les checkbox correspondante (si la checkbox est cochée, les dés ne seront pas lancés.
        // cb1, cb2, cb3, cb4, cb5

        //bool cb1.isSelected();

        // mettre la valeur 3 à ton score dans la ligne 0.
        // scoresTable.getItems().get(0).getYourScoreProperty().setValue("3");
    }
}
