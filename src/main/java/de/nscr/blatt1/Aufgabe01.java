package de.nscr.blatt1;

import de.nscr.gui.AufgabenGUI;
import de.nscr.gui.GUI;

import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 */
public class Aufgabe01 {
    private final AufgabenGUI gui;
    Scanner scanner;

    /**
     *
     */
    public Aufgabe01(AufgabenGUI gui) {
        this.gui = gui;
        this.scanner = gui.scanner;
        anfang();
    }

    /**
     *
     */
    public void anfang() {
        System.out.println("Geben sie die erste Zahl ein von der sie die Fakultaet berechnen wollen: " );
        try {
            int zahl = scanner.nextInt();
            scanner.nextLine();  // Consume leftover newline
            berechneFakultaet(zahl);
        } catch (InputMismatchException ex) {
            System.out.println("Ungültige Eingabe! Bitte eine Zahl eingeben.");
            scanner.nextLine();  // Clear bad input
            anfang();
        }


        System.out.println("Wollen sie weitere Faklutaeten berechnen? (y/n)");
        weiter();
    }

    /**
     *
     */
    public void weiter () {
        // Stackoverflowexeption wegen rekursion erreichbar
        String zeile = scanner.nextLine().trim().toLowerCase();
        if (zeile.equals("y")) {
            anfang();
        } else if (zeile.equals("n")) {
            gui.window.togglevisible();
            gui.exit();
        } else {
            System.out.println("Bitte gebe eine gültige Eingabe von entweder 'y' oder 'n'.");
            weiter();
        }
    }


    /**
     *
     * @param pZahl
     * @return
     */
    public BigInteger berechneFakultaet(int pZahl) {
        BigInteger ergebnis = new BigInteger("1");

        for (int i = pZahl; i > 0; i--) {
            BigInteger i1 = BigInteger.valueOf(i);
            ergebnis = ergebnis.multiply(i1);
        }

        System.out.println("Die Fakultaet von " + pZahl + " ist " + ergebnis);
        return ergebnis;
    }
}