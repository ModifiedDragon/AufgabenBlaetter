package de.nscr.blatt1;

import de.nscr.gui.GUI;

import java.util.ArrayList;
import java.util.Scanner;

public class Aufgabe02 {
    Scanner scanner;

    public Aufgabe02(GUI frame) {
        frame.exit();
        scanner = frame.input;
        ausführen();
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


    public void ausführen() {
        System.out.println("Welche Zahl willst du als Primzahl überprüfen?");
        int pZahl = scanner.nextInt();
        pZahlBerechnen(pZahl, true);
    }

    private boolean pZahlBerechnen(int pZahl, boolean ersteDurchlauf) {
        ArrayList<Integer> teiler = new ArrayList<>();
        for (int i = 1; i <= pZahl / 2; i++) {
            if (pZahl % i == 0) {
                teiler.add(i);
            }
        }
        teiler.add(pZahl);
        if (teiler.size() == 2) {
            System.out.println(ersteDurchlauf ? pZahl + " ist eine Primzahl." : "Der Teiler " + pZahl + " ist eine Primzahl.");
            return true;
        }
        if (ersteDurchlauf) {
            for (int i = 0; i < teiler.size(); i++) {
                boolean prim = pZahlBerechnen(teiler.get(i), false);
                System.out.println((pZahl + " ist durch " + teiler.get(i) + " teilbar") + (prim ? " und ist eine Primzahl." : "." ));
            }
        }
        return false;
    }

}
