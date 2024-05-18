package com.example.demo;

import java.util.ArrayList;
import java.util.List;

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
            if( zysk.getLiczba_n() >= 0) {
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

    public void oblicz_z()
    {
        this.zysk = this.zarob -this.koszt;
        System.out.println(zysk);
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
}