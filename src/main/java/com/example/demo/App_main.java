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
                { 0, 0, 0}, { 0, 0, 0 }
        }; // table

        int[] supply
                = new int[] { 3,3 }; // supply
        int[] demand
                = new int[] { 2, 2 ,2};
        int[] zakup
                = new int[] { 10, 12 }; // supply
        int[] sprzedaz
                = new int[] { 30, 25, 30 };

        Obliczenia ob1 = new Obliczenia(grid, supply,demand,zakup, sprzedaz);
        ob1.oblicz_j();
        ob1.oblicz_pop();
    }
}