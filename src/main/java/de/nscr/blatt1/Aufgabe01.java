package de.nscr.blatt1;

import de.nscr.archive.AufgabenGUI;
import de.nscr.gui.SchlangenEingabe;

import javax.swing.*;
import java.math.BigInteger;
import java.io.IOException;

/**
 *
 */
import java.io.IOException;
import java.math.BigInteger;

public class Aufgabe01 {
    private final SchlangenEingabe eingabe;

    /**
     * @param eingabe Die Eingabequelle (kommt vom ConsoleController)
     */
    public Aufgabe01(SchlangenEingabe eingabe) {
        this.eingabe = eingabe;
        System.out.println("DEBUG: Aufgabe01 gestartet");
        anfang();
    }

    /**
     * Liest eine Zeile aus der SchlangenEingabe
     */
    private String auslesen() throws IOException {
        StringBuilder zeile = new StringBuilder();
        int b;
        while ((b = eingabe.read()) != -1) {
            char c = (char) b;
            if (c == '\n') {
                break;
            }
            zeile.append(c);
        }
        String ergebnis = zeile.toString().trim();
        if (ergebnis.isEmpty() && b == -1) {
            return null;
        }
        return ergebnis;
    }

    /**
     * Startpunkt der Aufgabe
     */
    public void anfang() {
        System.out.println("DEBUG: Aufgabe01 wartet auf Eingabe...");
        System.out.println("Geben Sie eine Zahl ein, deren Fakultät berechnet werden soll (Ganzzahl):");
        while (true) {
            try {
                String zeile = auslesen();
                if (zeile == null) {
                    return; // Eingabe beendet
                }

                int zahl = Integer.parseInt(zeile);

                if (zahl < 0) {
                    System.out.println("Bitte geben Sie eine Zahl >= 0 ein");
                    continue;
                }
                if (zahl == 0) {
                    System.out.println("Die Fakultät von 0 ist 1");
                } else {
                    berechneFakultaet(zahl);
                }
                weiter();
                return;
            } catch (NumberFormatException ex) {
                System.out.println("Es wurde keine gültige Ganzzahl eingegeben.");
            } catch (IOException ex) {
                System.out.println("Fehler beim Lesen der Eingabe: " + ex.getMessage());
                return;
            }
        }
    }

    /**
     * Fragt, ob weitere Berechnungen gewünscht sind
     */
    public void weiter() {
        System.out.println("Wollen Sie weitere Fakultäten berechnen? (y/n)");
        while (true) {
            try {
                String zeile = auslesen();
                if (zeile == null) {
                    return;
                }
                zeile = zeile.toLowerCase();
                if (zeile.equals("y")) {
                    anfang();
                    return;
                } else if (zeile.equals("n")) {
                    System.out.println("Aufgabe beendet.");
                    return;
                } else {
                    System.out.println("Bitte geben Sie eine gültige Eingabe ein. (y/n)");
                }
            } catch (IOException ex) {
                System.out.println("Fehler beim Lesen der Eingabe: " + ex.getMessage());
                return;
            }
        }
    }

    /**
     * Berechnet die Fakultät
     */
    public void berechneFakultaet(int pZahl) {
        BigInteger ergebnis = BigInteger.ONE;
        for (int i = pZahl; i > 0; i--) {
            ergebnis = ergebnis.multiply(BigInteger.valueOf(i));
        }
        System.out.println("Die Fakultät von " + pZahl + " ist " + ergebnis);
    }
}
