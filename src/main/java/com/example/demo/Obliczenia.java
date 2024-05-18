package com.example.demo;

import java.util.Arrays;
import java.util.LinkedList;

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
    int tab_pop[][];
    int koszt = 0;
    int zarob = 0;
    int zysk = 0;

    LinkedList<Pozycja_max> zyski_max = new LinkedList<Pozycja_max>();

    static int INF = 1000;

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
            for (int i = 0;  i < size_od; i++)
                isSet[j][i] = false;

        int k =0;
        Pozycja_max zysk = new Pozycja_max();
        int i = 0, j = 0;

        while(k < (size_do + size_do)){
            System.out.println(k);
            zysk.setZyskj(Integer.MIN_VALUE);
            //picking up the least cost cell
            for (int m = 0;  m < size_do; m++)
            {
                for (int n = 0; n < size_od; n++)
                {
                    if (!isSet[m][n])
                    {
                        if (tab_jedn[m][n] > zysk.getZyskj()) {
                            zysk.setDostawca(m);
                            zysk.setOdbiorca(n);
                            zysk.setZyskj(tab_jedn[m][n]);

                        }
                    }
                }
            }


            i = zysk.getDostawca();
            j = zysk.getOdbiorca();

            //allocating stock in the proper manner
            min = Math.min(tab_popyt[j], tab_podaz[i]);
            if( zysk.getZyskj() >= 0) {
                zyski_max.get(k).setOdbiorca(j);
                zyski_max.get(k).setDostawca(i);
                zyski_max.get(k).setZyskj((int) min);
            }
            k++;

            tab_popyt[j] -= min;
            tab_podaz[i] -= min;

            System.out.println(tab_popyt[j] );
            System.out.println(tab_podaz[i] );
            //allocating null values in the removed row/column
            if(tab_podaz[i] == 0) {
                for (int l = 0; l < size_od; l++)
                    isSet[i][l] = true;
            }

            if(tab_popyt[j] == 0) {
                for (int l = 0; l < size_do; l++)
                    isSet[l][j] = true;
            }

            for (int jj = 0; jj < size_do; jj++) {
                for (int ii = 0; ii < size_od; ii++) {
                    System.out.print(isSet[jj][ii] + " ");
                }
                System.out.println();
            }
        }


        int aa = 0;
        while(aa < k){
            System.out.println(zyski_max.get(aa).getDostawca() +" " +zyski_max.get(aa).getOdbiorca()+ " "+zyski_max.get(aa).getZyskj());
            aa++;
        }

    }

    public void oblicz_zar()
    {
        for (int i = 0; i < size_do; i++)
        {
            for (int j = 0; j < size_od; j++)
            {
            zysk+=tab_pop[i][j] * tab_cena_s[j];
            }
        }
    }

    public void oblicz_k()
    {
        for (int i = 0; i < size_do; i++)
        {
            for (int j = 0; j < size_od; j++)
            {
                zysk+=tab_pop[i][j] * (tab_cena_z[j] + tab_kosztransportu[i][j]);
            }
        }
    }

    public void oblicz_z()
    {
        this.zysk = this.zarob -this.koszt;
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
                    this.tab_popyt[i]= 0;
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
        tab_pop = new int[size_do][size_od];

        for(int i=0; i < (size_do + size_od +1); i++)
            zyski_max.add(new Pozycja_max());
    }
}