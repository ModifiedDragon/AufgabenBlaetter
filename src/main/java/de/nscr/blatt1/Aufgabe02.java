package de.nscr.blatt1;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 *
 */
public class Aufgabe02 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;

    /**
     *
     * @param eingabe     Die Eingabe, welche zum Auslesen benutzt wird
     * @param printStream
     */
    public Aufgabe02(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        System.err.println("Aufgabe02 started");
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
        out.println("Welche Zahl wollen Sie als Primzahl überprüfen? (Ganzzahl)");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                if (zeile.equals("exit")) {
                    System.out.println("Programm beendet");
                    return false;
                }
                int pZahl = Integer.parseInt(zeile);
                pZahlBerechnen(pZahl, true);
                return true;
            } catch (IOException e) {
                out.println("Es wurde keine Richtige Nummer eingegeben. (Ganzzahl)");
            }
        }
        return false;
    }

    /**
     *
     * @param pZahl          ist die Zahl, die vom User eingegeben wird und die auf teiler / Primzahl geprüft wird
     * @param ersteDurchlauf wird benutzt um zu erkennen, ob es der erste Durchlauf im rekursiven Sinne ist.
     * @return benutzt den Wahrheitswert, um zu überprüfen, ob es in der 2. Rekursionsebene eine Primzahl ist
     */
    private boolean pZahlBerechnen(int pZahl, boolean ersteDurchlauf) {
        ArrayList<Integer> teiler = new ArrayList<>();
        // Auflisten aller Teiler
        for (int i = 1; i <= pZahl / 2; i++) {
            if (pZahl % i == 0) {
                teiler.add(i);
            }
        }
        teiler.add(pZahl);
        // Überprüfen, ob Primzahl ist (Für 2. Rekursionsebene)
        if (teiler.size() == 2) {
            if (ersteDurchlauf) {
                out.println(pZahl + " ist eine Primzahl.");
            }
            return true;
        } else {
            // Falls erste Rekursionsebene führt der Code dies aus
            if (ersteDurchlauf) {
                // Durchgehen der Zahlen in der TeilerListe und überprüfen, ob diese Teiler sind
                for (Integer integer : teiler) {
                    boolean prim = pZahlBerechnen(integer, false);
                    if (prim) {
                        out.println(pZahl + " ist durch " + integer + " teilbar und diese ist eine Primzahl.");
                    }
                }
                // Überprüfen am Ende, ob die eingegebene Zahl eine Primzahl ist
                out.println(pZahl + " ist keine Primzahl.");
            }
        }
        return false;
    }

}