package de.nscr.blatt1;

import de.nscr.gui.AufgabenGUI;
import de.nscr.gui.SchlangenEingabe;

import javax.swing.*;
import java.math.BigInteger;
import java.io.IOException;

/**
 *
 */
public class Aufgabe01 {
    private final AufgabenGUI gui;
    private final SchlangenEingabe eingabe;
    /**
     *
     * @param gui Der Frame, der übergeben wird
     * @param eingabe Die Eingabe, welche zum Auslesen benutzt wird
     */
    public Aufgabe01(AufgabenGUI gui, SchlangenEingabe eingabe) {
        this.gui = gui;
        this.eingabe = eingabe;
        anfang();
    }

    /**
     *
     * @return
     * @throws IOException
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
        String ergebnis = zeile.toString().trim().toUpperCase();
        if (ergebnis.isEmpty() && b == -1) {
            return null;
        }
        return ergebnis;
    }

    /**
     *
     */
    public void anfang() {
        System.out.println("Geben Sie die erste Zahl ein von der sie die Fakultät berechnen wollen: " );
        while (true) {
            try {
                String zeile = auslesen();
                if (zeile == null) {
                    return;
                }
                int zahl = Integer.parseInt(zeile);
                berechneFakultaet(zahl);
            } catch (NumberFormatException ex) {
                System.out.println("Es wurde keine Richtige Nummer eingegeben.");
            } catch (IOException ex) {
                System.out.println("Fehler beim Lesen der Eingabe: " + ex.getMessage());
            }
            weiter();
        }
    }

    /**
     *
     */
    public void weiter() {
        System.out.println("Wollen Sie weitere Fakultäten berechnen? (y/n)");
        while (true) {
            try {
                String zeile = auslesen();
                if (zeile == null) {
                    break;
                }
                zeile = zeile.toLowerCase();
                if (zeile.equals("y")) {
                    anfang();
                    return;
                } else if (zeile.equals("n")) {
                    SwingUtilities.invokeLater(() -> {
                        gui.gui.togglevisible();
                        gui.exit();
                    });
                    return;
                } else {
                    System.out.println("Bitte geben Sie eine gültige Eingabe von entweder 'y' oder 'n'.");
                }
            } catch (IOException ex) {
                System.out.println("Fehler beim Lesen der Eingabe: " + ex.getMessage());
                break;
            }
        }
    }

    /**
     *
     * @param pZahl ist die Eingabe vom User, von der die Fakultät berechnet wird
     */
    public void berechneFakultaet(int pZahl) {
        BigInteger ergebnis = BigInteger.valueOf(1);

        for (int i = pZahl; i > 0; i--) {
            ergebnis = ergebnis.multiply(BigInteger.valueOf(i));
        }

        System.out.println("Die Fakultät von " + pZahl + " ist " + ergebnis);
    }
}