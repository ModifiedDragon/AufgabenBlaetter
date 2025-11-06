package de.nscr.blatt1;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Random;

/**
 *
 */
public class Aufgabe04 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;
    int zahl;
    int ober;
    int unter;
    int versuchsAnzahl;

    /**
     *
     * @param eingabe     Die Eingabe, welche zum Auslesen benutzt wird
     * @param printStream
     */
    public Aufgabe04(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        boolean weiter = true;
        while (weiter) {
            weiter = anfang();
        }
    }

    /**
     *
     * @return
     * @throws IOException
     */
    private String auslesen() throws IOException {
        StringBuilder line = new StringBuilder();
        int b;
        while ((b = eingabe.read()) != -1) {
            char c = (char) b;
            if (c == '\n') {
                break;
            }
            line.append(c);
        }
        String result = line.toString().trim();
        if (result.isEmpty() && b == -1) {
            return null;
        }
        return result;
    }

    /**
     *
     */
    public boolean anfang() {
        if (Thread.currentThread().isInterrupted()) return false;
        while (!Thread.currentThread().isInterrupted()) {
            out.println("In welchem Bereich raten? Wolle Sie raten (z.B. '3,100')");
            String[] teile;
            versuchsAnzahl = 1;
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                if (zeile.equals("exit")) {
                    System.out.println("Programm beendet");
                    return false;
                }
                teile = zeile.trim().split(",");

                if (teile.length != 2) {
                    System.out.println("Bitte geben Sie zwei Zahlen an.");
                    return true;
                }
                int temp1 = Integer.parseInt(teile[0]);
                if (temp1 > Integer.parseInt(teile[1])) {
                    ober = temp1;
                    unter = Integer.parseInt(teile[1]);
                } else {
                    unter = temp1;
                    ober = Integer.parseInt(teile[1]);
                }
                Random random = new Random();
                // Ober und Untergrenze müssen um 1 erhöht werde, da Random mit 0 anfängt und nicht 1
                zahl = random.nextInt(unter + 1, ober + 1);
                return raten();
            } catch (Exception e) {
                out.println("Bitte geben Sie eine richtige Angabe an.");
            }
        }
        return false;
    }

    /**
     *
     */
    private boolean raten() {
        out.println("Raten Sie die Zahl im Bereich von " + unter + " und " + ober + ".");
        if (Thread.currentThread().isInterrupted()) return false;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                if (zeile.equals("exit")) {
                    System.out.println("Programm beendet");
                    return false;
                }
                int temp2 = Integer.parseInt(zeile);
                if (!vergleicheZahl(temp2)) {
                    versuchsAnzahl++;
                } else {
                    out.println("Sie haben die Zahl in " + versuchsAnzahl + " versuchen erraten.");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *
     * @param pWert ist die generierte Zahl, nach welcher der User sucht. Diese wird genutzt, um sie mit den Schätzungen, des User, zu vergleichen.
     * @return
     */
    public boolean vergleicheZahl(int pWert) { //pWert ist der schätzwert
        if (ober < pWert || unter > pWert) {
            out.println("Ihre Schätzung liegt außerhalb des definierten Bereichs  (" + pWert + ")");
            return false;
        } else if (zahl == pWert) {
            out.println("Sie haben die richtige Zahl erraten, SUPER!!!");
            return true;
        } else if (zahl > pWert) {
            out.println("Ihre Schätzung ist kleiner als die Zahl (" + pWert + ")");
            return false;
        } else {
            out.println("Ihre Schätzung ist größer als die Zahl (" + pWert + ")");
            return false;
        }
    }
}

