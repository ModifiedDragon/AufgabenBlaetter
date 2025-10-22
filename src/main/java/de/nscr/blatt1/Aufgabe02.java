package de.nscr.blatt1;

import de.nscr.gui.AufgabenGUI;
import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 */
public class Aufgabe02 {
    private final AufgabenGUI gui;
    private final SchlangenEingabe eingabe
            ;

    /**
     *
     * @param gui Der Frame, der übergeben wird
     * @param eingabe Die Eingabe, welche zum Auslesen benutzt wird
     */
    public Aufgabe02(AufgabenGUI gui, SchlangenEingabe eingabe) {
        this.gui = gui;
        this.eingabe = eingabe;
        ausfuehren();

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
     *
     */
    private void weiter() {
        System.out.println("Wollen Sie noch eine Sache abfragen? (y/n)");
        while (true) {
            try {
                String zeile = auslesen();
                switch (zeile) {
                    case "y":
                        ausfuehren();
                        return;
                    case "n":
                        gui.gui.togglevisible();
                        gui.exit();
                        return;
                    default:
                        System.out.println("Bitte geben Sie eine gültige Eingabe ein. (y/n)");
                        weiter();
                }
            } catch (IOException e) {
                System.out.println("Bitte gebe Sie eine gültige Eingabe ein. (y/n)");
            }
        }
    }

    /**
     *
     */
    public void ausfuehren() {
        System.out.println("Welche Zahl wollen Sie als Primzahl überprüfen? (Ganzzahl)");
        while (true) {
            try {
                int pZahl = Integer.parseInt(Objects.requireNonNull(auslesen()));
                pZahlBerechnen(pZahl, true);
                weiter();
                return;
            } catch (IOException e) {
                System.out.println("Es wurde keine Richtige Nummer eingegeben (Ganzzahl).");
            }
        }

    }

    /**
     *
     * @param pZahl ist die Zahl, die vom User eingegeben wird und die auf teiler / Primzahl geprüft wird
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
                System.out.println(pZahl + " ist eine Primzahl.");
            }
            return true;
        } else {
            // Falls erste Rekursionsebene führt der Code dies aus
            if (ersteDurchlauf) {
                // Durchgehen der Zahlen in der TeilerListe und überprüfen, ob diese Teiler sind
                for (Integer integer : teiler) {
                    boolean prim = pZahlBerechnen(integer, false);
                    if (prim) {
                        System.out.println(pZahl + " ist durch " + integer + " teilbar und diese ist eine Primzahl.");
                    }
                }
                // Überprüfen am Ende, ob die eingegebene Zahl eine Primzahl ist
                System.out.println(pZahl + " ist keine Primzahl.");
            }
        }
        return false;
    }

}
