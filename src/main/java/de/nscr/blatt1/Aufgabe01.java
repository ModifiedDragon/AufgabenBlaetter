package de.nscr.blatt1;

import de.nscr.gui.GUI;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 */
public class Aufgabe01 {

    Scanner scanner;


    /**
     *
     */
    public Aufgabe01(GUI frame) {
        frame.exit();
        this.scanner = frame.input;
        anfang();
    }

    /**
     *
     */
    public void anfang() {
        System.out.print("Geben sie die erste Zahl ein von der sie die Fakultaet berechnen wollen: " );
        berechneFakultaet(scanner.nextInt());
        System.out.println("Wollen sie weitere Faklutaeten berechnen? (y/n)");
        weiter();
    }

    /**
     *
     */
    public void weiter () {
        String zeile = scanner.nextLine();
        if (zeile.equals("y")) {
            anfang();
        } else if (zeile.equals("n")) {
            GUI gui = new GUI(scanner);
            gui.setup();
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