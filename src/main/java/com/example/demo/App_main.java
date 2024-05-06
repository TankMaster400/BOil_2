package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App_main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App_main.class.getResource("View_1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
        int[][] grid = new int[][] {
                { 3, 1, 7, 4 }, { 2, 6, 5, 9 }, { 8, 3, 3, 2 }
        }; // table

        int[] supply
                = new int[] { 300, 400, 500 }; // supply
        int[] demand
                = new int[] { 250, 350, 400, 200 };
        int[] zakup
                = new int[] { 2, 3, 5 }; // supply
        int[] sprzedaz
                = new int[] { 10, 20, 14, 17 };

        Obliczenia ob1 = new Obliczenia(grid, supply,demand,zakup, sprzedaz);
        ob1.oblicz_j();
    }
}