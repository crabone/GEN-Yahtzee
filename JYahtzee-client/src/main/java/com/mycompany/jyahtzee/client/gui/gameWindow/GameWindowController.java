package com.mycompany.jyahtzee.client.gui.gameWindow;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
}
