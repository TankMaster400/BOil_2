package com.example.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Obliczenia {

    int tab_kosztransportu[][];
    int tab_podaz[];
    int tab_popyt[];
    int tab_cena_z[];
    int tab_cena_s[];

    int size_do;
    int size_od;

    int fdo = 0;
    int fod = 0;

    int tab_jedn[][];
    ArrayList<Pozycja_max> zyski_max = new ArrayList<Pozycja_max>();
    int koszt = 0;
    int zarob = 0;
    int zysk = 0;

    public void oblicz_j() {

        for (int i = 0; i < size_do; i++) {
            for (int j = 0; j < size_od; j++) {
                tab_jedn[i][j] = -tab_kosztransportu[i][j];
                tab_jedn[i][j] -= tab_cena_z[i];
                tab_jedn[i][j] += tab_cena_s[j];
                System.out.print(tab_jedn[i][j] + " ");
            }
            System.out.println();
        }
        if(fod == 1)
        {
            for (int i = 0; i < size_do; i++) {
                tab_jedn[i][size_od-1] = 0;
            }
        }

        for (int i = 0; i < size_do; i++) {
            for (int j = 0; j < size_od; j++) {
                System.out.print(tab_jedn[i][j] + " ");
            }
            System.out.println();
        }

    }

    public void oblicz_pop()
    {
        double min;
        boolean [][]isSet = new boolean[size_do][size_od];
        for (int j = 0; j < size_do; j++)
        {
            for (int i = 0; i < size_od; i++)
            {
                isSet[j][i] = false;
            }
        }
        int k =0;

        Pozycja_max zysk = new Pozycja_max();

        int i = 0, j = 0;

        while(k < (size_do + size_do)){
            System.out.println(k);

            zysk.setLiczba_n(Integer.MIN_VALUE);

            for (int m = 0;  m < size_do; m++)
            {
                for (int n = 0; n < size_od; n++)
                {
                    if (!isSet[m][n])
                    {
                        if (tab_jedn[m][n] > zysk.getLiczba_n()) {
                            zysk.setDostawca(m);
                            zysk.setOdbiorca(n);
                            zysk.setLiczba_n(tab_jedn[m][n]);

                        }
                    }
                }
            }

            i = zysk.getDostawca();
            j = zysk.getOdbiorca();

            //allocating stock in the proper manner
            min = Math.min(tab_popyt[j], tab_podaz[i]);
            if( zysk.getLiczba_n() > 0) {
                zyski_max.add(new Pozycja_max(i,j,(int)min));
            }
            k++;

            tab_popyt[j] -= min;
            tab_podaz[i] -= min;

            if(tab_podaz[i] == 0) {
                for (int l = 0; l < size_od; l++)
                    isSet[i][l] = true;
            }

            if(tab_popyt[j] == 0) {
                for (int l = 0; l < size_do; l++)
                    isSet[l][j] = true;
            }
        }

        int aa = 0;
        while(aa < zyski_max.size()){
            System.out.println(zyski_max.get(aa).getDostawca() +" " +zyski_max.get(aa).getOdbiorca()+ " "+zyski_max.get(aa).getLiczba_n());
            aa++;
        }

    }

    public void oblicz_zar()
    {
        int r = 0;
        while(r < zyski_max.size())
        {
            zarob += zyski_max.get(r).getLiczba_n()*tab_cena_s[zyski_max.get(r).getOdbiorca()];
            r++;
        }
        System.out.println(zarob);
    }

    public void oblicz_k()
    {
        int r = 0;
        while(r < zyski_max.size())
        {
            koszt += zyski_max.get(r).getLiczba_n()*(tab_cena_z[zyski_max.get(r).getDostawca()]+tab_kosztransportu[zyski_max.get(r).getDostawca()][zyski_max.get(r).getOdbiorca()]);
            r++;
        }
        System.out.println(koszt);
    }

    public void oblicz_z() throws IOException {
        this.zysk = this.zarob -this.koszt;
        System.out.println(zysk);
        Wynik();
    }

    public Obliczenia(int[][] tab_kosztransportu, int[] tab_podaz, int[] tab_popyt, int[] tab_cena_z, int[] tab_cena_s) {

        this.size_do = tab_podaz.length;
        this.size_od = tab_popyt.length;

        int suma_od = 0;
        for (int i = 0; i < size_od; i++)
        {
            suma_od += tab_popyt[i];
        }
        int suma_do = 0;
        for (int i = 0; i < size_do; i++)
        {
            suma_do += tab_podaz[i];
        }

        if (suma_od == suma_do) //oba poprawnie
            {
            this.tab_kosztransportu = tab_kosztransportu;
            this.tab_podaz = tab_podaz;
            this.tab_popyt = tab_popyt;
            this.tab_cena_z = tab_cena_z;
            this.tab_cena_s = tab_cena_s;
        }
        else if (suma_od < suma_do)   // fikcyjny odbiorca
        {
            fod =1;
            this.size_od += 1;

            int temp_kt[][] = tab_kosztransportu;
            this.tab_kosztransportu = new int[size_do][size_od];
            for (int i = 0; i < size_do; i++)
            {
                for(int j = 0; j < size_od; j++)
                {
                    if (j < size_od-1 ) {
                        this.tab_kosztransportu[i][j] = temp_kt[i][j];
                    }
                    else
                    {
                        this.tab_kosztransportu[i][j]= 0;
                    }
                }
            }
            this.tab_podaz = tab_podaz;
            int temp_pop[]= tab_popyt;
            this.tab_popyt = new int[size_od];
            for (int i = 0; i < size_od; i++)
            {
                if (i < size_od-1 ) {
                    this.tab_popyt[i] = temp_pop[i];
                }
                else
                {
                    this.tab_popyt[i]= suma_do - suma_od;
                }
            }
            this.tab_cena_z = tab_cena_z;
            int temp_cena_s[]= tab_cena_s;
            this.tab_cena_s = new int[size_od];
            for (int i = 0; i < size_od; i++)
            {
                if (i < size_od-1 ) {
                    this.tab_cena_s[i] = temp_cena_s[i];
                }
                else
                {
                    this.tab_cena_s[i]= 0;
                }
            }
        }
        else //fikcyjny dostawca?
        {
            this.tab_kosztransportu = tab_kosztransportu;
            this.tab_podaz = tab_podaz;
            this.tab_popyt = tab_popyt;
            this.tab_cena_z = tab_cena_z;
            this.tab_cena_s = tab_cena_s;
        }

        tab_jedn = new int[size_do][size_od];
    }

    private void Wynik() throws IOException {
            FXMLLoader fxmlLoader2 = new FXMLLoader(App_main.class.getResource("View_2.fxml"));

            //TableView table = new TableView();
            StackPane stackPane = new StackPane();

            //table.setEditable(false);
            //ArrayList<TableColumn> kolumny = new ArrayList<TableColumn>();
            //ObservableList<String> wiersz = FXCollections.observableArrayList();
            ArrayList<Label> kolumny = new ArrayList<Label>();

//            for(int i=0;i<tab_jedn.length;i++){
//                kolumny.add("Supplier "+(i+1));
//            }

            for(int i=0;i<tab_jedn[0].length;i++){
                kolumny.add(new Label("Customer "+(i+1)));
                kolumny.get(i).setFont(new Font("Arial", 20));
                        //kolumny.add(new TableColumn("Customer "+(i+1)));
//                if(i==0){
//                    kolumny.get(i).setMinWidth(200);
//                    kolumny.get(i).setCellValueFactory(new PropertyValueFactory<String>());
//                    table.setItems(wiersz);
//                }
            }

            //table.getColumns().addAll(kolumny);

            VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(kolumny);

            stackPane.getChildren().addAll(kolumny);
            stackPane.setPadding(new Insets(10, 0, 0, 10));

            //TU POCZATEK
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(0);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        int ot[][]= new int[tab_jedn.length][tab_jedn[0].length-fod];

        Text scenetitle = new Text("Individual profits");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setWrappingWidth(200);
        scenetitle.setTextAlignment(TextAlignment.CENTER);
        grid.add(scenetitle, 0, 0, 3, 1);
        for(int j=0;j<tab_jedn.length;j++) {
            for (int i = 0; i < tab_jedn[0].length-fod; i++) {
                //kolumny.add(new Label("Customer "+(i+1)));
//                if (j == 0) {
//                    grid.add(new Text("Customer " + (i + 1)), i, 1);
//                } else {
                Text temp = new Text(String.valueOf(tab_jedn[j][i]));
                temp.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

                temp.setWrappingWidth(200/(tab_jedn[0].length-fod));
                temp.setTextAlignment(TextAlignment.CENTER);
                    grid.add(temp, i, (j+1));
                //}
                ot[j][i] =0;
            }
        }

        for(int i=0;i<zyski_max.size();i++){
            ot[zyski_max.get(i).dostawca][zyski_max.get(i).odbiorca] = zyski_max.get(i).liczba_n;
        }

        Text scenetitle2 = new Text("Optimal transport");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle2.setWrappingWidth(200);
        scenetitle2.setTextAlignment(TextAlignment.CENTER);
        grid.add(scenetitle2, 0, tab_jedn.length+1, 3, 1);
        for(int j=0;j<ot.length;j++) {
            for (int i = 0; i < ot[0].length; i++) {
                Text temp = new Text(String.valueOf(ot[j][i]));
                temp.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

                temp.setWrappingWidth(200/(tab_jedn[0].length-fod));
                temp.setTextAlignment(TextAlignment.CENTER);
                grid.add(temp, i, (j+tab_jedn.length+2));
            }
        }

        scenetitle2 = new Text("Total cost: "+koszt);
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle2.setWrappingWidth(200);
        scenetitle2.setTextAlignment(TextAlignment.CENTER);
        grid.add(scenetitle2, 0, 2*tab_jedn.length+2, 3, 1);

        scenetitle2 = new Text("Income: "+zarob);
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle2.setWrappingWidth(200);
        scenetitle2.setTextAlignment(TextAlignment.CENTER);
        grid.add(scenetitle2, 0, 2*tab_jedn.length+3, 3, 1);

        scenetitle2 = new Text("Profit: "+zysk);
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle2.setWrappingWidth(200);
        scenetitle2.setTextAlignment(TextAlignment.CENTER);
        grid.add(scenetitle2, 0, 2*tab_jedn.length+4, 3, 1);

            Scene scene2 = new Scene(grid, 560, 440);
            Stage stage2 = new Stage();
            stage2.setTitle("Wyniki");
            stage2.setScene(scene2);
            stage2.show();

        }
    }