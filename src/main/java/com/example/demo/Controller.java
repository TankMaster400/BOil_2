package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Controller {
    @FXML
    private Label welcomeText;
    @FXML
    private VBox vBox;

    private int currHeight = 4;
    private int currWidth = 4;
    @FXML
    protected void calculate() throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");

        int[] supply = new int[currHeight-2];
        int[] demand = new int[currWidth-2];
        int[] zakup = new int[currHeight-2];
        int[] sprzedaz = new int[currWidth-2];

        int[][] grid = new int[currHeight-2][currWidth-2];

        for(int i = 0; i < currHeight; i++){
            HBox hBox = (HBox) vBox.getChildren().get(i);
            for(int j = 0; j < currWidth; j++){
                TextField tf = (TextField) hBox.getChildren().get(j);
                if(i == 0){ //top
                    if(j > 0 && j < currWidth-1){ //skip first and last
                        demand[j-1]=Integer.parseInt(tf.getText());
                    }
                }else if(i == currHeight-1){ //bottom
                    if(j > 0 && j < currWidth-1){ //skip first and last
                        sprzedaz[j-1]=Integer.parseInt(tf.getText());
                    }
                }else{ //sides and core
                    if(j == 0 || j == currWidth-1){ //side left and right
                        if(j == 0){
                            supply[i-1]=Integer.parseInt(tf.getText());
                        }else{
                            zakup[i-1]=Integer.parseInt(tf.getText());
                        }
                    }else{ //core
                        grid[i-1][j-1]=Integer.parseInt(tf.getText());
                    }
                }

            }
        }

        // grid = [8, 14, 17], [12, 9, 19]
        // supply = [20,30]                 DONE
        // demand = [10,28,27]              DONE
        // zakup = [10,12]                  DONE
        // sprzedaz = [30,25,30]            DONE

        Obliczenia ob1 = new Obliczenia(grid, supply,demand,zakup, sprzedaz);
        ob1.oblicz_j();
        ob1.oblicz_pop();
        ob1.oblicz_zar();
        ob1.oblicz_k();
        ob1.oblicz_z();
    }
    @FXML
    protected void sizeUpH(){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        for(int i=0; i < currWidth; i++){
            TextField tf = new TextField();
            if(i == 0){
                tf.setDisable(true);
            }
            if(i == currWidth-1){
                tf.setDisable(true);
            }
            hBox.getChildren().add(tf);
        }

        HBox hbox = (HBox) vBox.getChildren().getLast();
        hbox.getChildren().getFirst().setDisable(false);
        hbox.getChildren().getLast().setDisable(false);

        vBox.getChildren().add(hBox);
        currHeight++;
    }
    @FXML
    protected void sizeUpW(){

        for(int i=0; i < currHeight; i++){
            TextField tf2 = new TextField();
            HBox hbox = (HBox) vBox.getChildren().get(i);
            if(i==0){
                tf2.setDisable(true);
                hbox.getChildren().getLast().setDisable(false);
            }
            if(i == currHeight-1){
                hbox.getChildren().getLast().setDisable(false);
                tf2.setDisable(true);
            }
            hbox.getChildren().add(tf2);

        }
        currWidth++;
    }
    @FXML
    protected void sizeDownH(){
        if(currHeight == 4){return;};

        for(int i=0; i < currHeight-1; i++){
            HBox hBox = (HBox) vBox.getChildren().get(i);
        }
        vBox.getChildren().remove(vBox.getChildren().getLast());

        HBox hbox = (HBox)  vBox.getChildren().getLast();
        hbox.getChildren().getFirst().setDisable(true);
        hbox.getChildren().getLast().setDisable(true);
        currHeight--;
    }
    @FXML
    protected void sizeDownW(){
        if(currWidth == 4){return;};

        for(int i=0; i < currHeight; i++){
            HBox hBox = (HBox) vBox.getChildren().get(i);
            hBox.getChildren().remove(hBox.getChildren().getLast());
            if(i == 0){
                hBox.getChildren().getLast().setDisable(true);
            }
            if(i == currHeight-1){
                hBox.getChildren().getLast().setDisable(true);
            }
        }
        currWidth--;
    }
}