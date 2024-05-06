package com.example.demo;

public class Obliczenia {

    int tab_kosztransportu[][];
    int tab_podaz[];
    int tab_popyt[];
    int tab_cena_z[];
    int tab_cena_s[];

    int size_do;
    int size_od;

    int tab_jedn[][];
    int tab_pop[][];
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

    }

    public void oblicz_pop()
    {

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
        for (int i = 0; i < size_do; i++)
        {
            suma_od += tab_podaz[i];
        }
        int suma_do = 0;
        for (int i = 0; i < size_od; i++)
        {
            suma_do += tab_popyt[i];
        }

        suma_od -=1;

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

    }
}