package com.example.demo;

public class Pozycja_max {

    int liczba_n;
    int dostawca;
    int odbiorca;

    public Pozycja_max( int dostawca, int odbiorca,int liczba_n) {
        this.liczba_n = liczba_n;
        this.dostawca = dostawca;
        this.odbiorca = odbiorca;
    }

    public Pozycja_max() {
        this.liczba_n = 0;
        this.dostawca = 0;
        this.odbiorca = 0;
    }


    public int getLiczba_n() {
        return liczba_n;
    }

    public void setLiczba_n(int liczba_n) {
        this.liczba_n = liczba_n;
    }

    public int getDostawca() {
        return dostawca;
    }

    public void setDostawca(int dostawca) {
        this.dostawca = dostawca;
    }

    public int getOdbiorca() {
        return odbiorca;
    }

    public void setOdbiorca(int odbiorca) {
        this.odbiorca = odbiorca;
    }
}
