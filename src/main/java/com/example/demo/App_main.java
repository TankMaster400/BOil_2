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

        int[][] grid = new int[][] {
                {6,4}, {2,5}
        }; // table

        int[] supply
                = new int[] {35,25}; // supply
        int[] demand
                = new int[] { 20,30};
        int[] zakup
                = new int[] {6,7}; // supply
        int[] sprzedaz
                = new int[] {11,13};

        Obliczenia ob1 = new Obliczenia(grid, supply,demand,zakup, sprzedaz);
        ob1.oblicz_j();
        ob1.oblicz_pop();
        ob1.oblicz_zar();
        ob1.oblicz_k();
        ob1.oblicz_z();


    }

    public static void main(String[] args) throws IOException {
        launch();

    }
}