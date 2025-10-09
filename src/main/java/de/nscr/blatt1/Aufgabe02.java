package de.nscr.blatt1;

import java.util.Scanner;

public class Aufgabe02 {
    int pZahl;

    public Aufgabe02() {
        Scanner sc = new Scanner(System.in);
        pZahl = sc.nextInt();
        pZahlBerechnen(pZahl);
    }

    private void pZahlBerechnen(int pZahl) {
        ///  Methode der Primzahlzerlegung aussuchen

    }

    /*
    private void teiler(int zahl) {
        for (int i = 0; i < zahl / 2; i++) {
            switch(i) {
                case i%2==0 -> continue;
                case i%3==0 -> continue;
                case i%5==0 -> continue;
                case i%7==0 -> continue;
            }

        }
    }

     */

}
