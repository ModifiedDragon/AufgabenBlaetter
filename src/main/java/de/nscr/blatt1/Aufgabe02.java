package de.nscr.blatt1;

import de.nscr.gui.GUI;

import java.util.Scanner;

public class Aufgabe02 {
    int pZahl;
    Scanner scanner;

    public Aufgabe02(GUI frame) {
        frame.exit();
        scanner = frame.input;
        pZahlBerechnen(pZahl);
    }

    private void weiter() {
        System.out.println("Willst du noch eine Sache abfragen? (y/n)");
        String zeile = scanner.next();
        switch (zeile) {
            case "y" :

                break;
            case "n" :
                GUI gui = new GUI(scanner);
                gui.setup(1);
                break;
            default :
                System.out.println("Bitte gebe eine gültige Eingabe. (y/n)");
                weiter();
        }
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
