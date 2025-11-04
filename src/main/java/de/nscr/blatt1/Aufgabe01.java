package de.nscr.blatt1;

import de.nscr.gui.SchlangenEingabe;

import java.io.PrintStream;
import java.math.BigInteger;
import java.io.IOException;

/**
 *
 */

public class Aufgabe01 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;

    /**
     * @param eingabe     Die Eingabequelle (kommt vom ConsoleController)
     * @param printStream
     */
    public Aufgabe01(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        System.err.println("DEBUG: Aufgabe01 gestartet");
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
        if (Thread.currentThread().isInterrupted()) return;  // Check for interruption
        out.println("Geben Sie eine Zahl ein, deren Fakultät berechnet werden soll (Ganzzahl):");
        while (!Thread.currentThread().isInterrupted()) {  // Check in loop
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return;

                int zahl = Integer.parseInt(zeile);

                if (zahl < 0) {
                    out.println("Bitte geben Sie eine Zahl >= 0 ein");
                    continue;
                }
                if (zahl == 0) {
                    out.println("Die Fakultät von 0 ist 1");
                } else {
                    berechneFakultaet(zahl);
                }
                weiter();
                return;
            } catch (NumberFormatException ex) {
                out.println("Es wurde keine gültige Ganzzahl eingegeben.");
            } catch (IOException ex) {
                out.println("Fehler beim Lesen der Eingabe: " + ex.getMessage());
                return;
            }
        }
    }

    /**
     * Fragt, ob weitere Berechnungen gewünscht sind
     */
    public void weiter() throws IOException {
        if (Thread.currentThread().isInterrupted()) return;
        out.println("Wollen Sie weitere Fakultäten berechnen? (y/n)");
        while (!Thread.currentThread().isInterrupted()) {
            String zeile = auslesen();
            if (zeile == null || Thread.currentThread().isInterrupted()) return;
            if (zeile.equalsIgnoreCase("y")) {
                anfang();  // This should only happen once
                return;
            } else if (zeile.equalsIgnoreCase("n")) {
                out.println("Aufgabe beendet.");
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
        out.println("Die Fakultät von " + pZahl + " ist " + ergebnis);
    }
}
