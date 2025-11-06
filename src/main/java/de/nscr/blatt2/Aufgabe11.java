package de.nscr.blatt2;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;

public class Aufgabe11 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;

    public Aufgabe11(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        boolean weiter = true;
        while (weiter) {
            weiter = anfang();
        }
    }

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

    public boolean anfang() {
        if (Thread.currentThread().isInterrupted()) return false;  // Check for interruption
        out.println("Geben Sie an welches Muster Sie haben wollen? (a, b, c)");
        while (!Thread.currentThread().isInterrupted()) {
            if (Thread.currentThread().isInterrupted()) return false;
            String zeile = lesen();
            switch (zeile) {
                case "a":
                    System.out.println("Geben Sie die größe des Musters an. (+ Basisgröße)");
                    String temp = lesen();
                    musterA(Integer.parseInt(temp));
                    return true;
                case "b":
                    System.out.println("Geben Sie die größe des Musters an. (+ Basisgröße)");
                    String temp2 = lesen();
                    musterB(Integer.parseInt(temp2));
                    return true;
                case "c":
                    System.out.println("Geben Sie die größe des Musters pro Baum an und die Anzahl der Bäume. (z. B. 10,3)");
                    String[] temp3 = lesen().split(",");
                    musterC(Integer.parseInt(temp3[0]), Integer.parseInt(temp3[1]));
                    return true;
            }
        }
        return false;
    }

    public String lesen() {
        while (!Thread.currentThread().isInterrupted()) {  // Check in loop
            try {
                String zeile = auslesen().toLowerCase();
                if (zeile == null || Thread.currentThread().isInterrupted()) return null;
                if (zeile.equals("exit")) {
                    System.out.println("Programm beendet");
                    return null;
                } else {
                    return zeile;
                }
            } catch (IOException ex) {
                System.out.println("Bitte geben Sie eine gültige Eingabe");
            }
        }
        return null;
    }

    public void musterA(int n) {
        for (int i = 0; i < n + 7; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append((i % 2 == 0 ? "+ " : " +").repeat(9 + n));
            out.println(sb);
        }
    }

    public void musterB(int zeilen) {

        for (int i = 1; i <= zeilen; i++) {
            // Leerzeichen drucken
            for (int j = i; j < zeilen; j++) {
                System.out.print(" ");
            }

            // Pluszeichen drucken
            for (int k = 1; k <= (2 * i - 1); k++) {
                System.out.print("+");
            }

            // Zeilenumbruch
            System.out.println();
        }
    }

    public void musterC(int pZeilen, int anzahl) {
        //Zeilen halbieren, um richtige Größe zuerhalten
        int zeilen = pZeilen / 2;

        for (int i = 1; i <= zeilen; i++) {
            for (int p = 1; p <= anzahl; p++) {

                // Leerzeichen vor Sternen
                for (int j = i; j < zeilen; j++) {
                    System.out.print(" ");
                }

                // Sterne drucken
                for (int k = 1; k <= (2 * i - 1); k++) {
                    System.out.print("*");
                }

                // Abstand zwischen den Bäumen
                for (int s = 0; s < zeilen - i + 3; s++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        // === Stammgröße berechnen ===
        int stammGroesse = (zeilen / 10) + 1;  // alle 10 Zeilen wächst der Stamm um 1
        if (stammGroesse < 1) stammGroesse = 1;

        // === Stamm zeichnen ===
        for (int stammReihe = 0; stammReihe < stammGroesse; stammReihe++) {
            for (int p = 1; p <= anzahl; p++) {

                // Leerzeichen vor Stamm (zentrieren)
                for (int j = 1; j < zeilen - stammGroesse / 2; j++) {
                    System.out.print(" ");
                }

                // Stamm drucken (z. B. 2x2, 3x3, ...)
                for (int b = 0; b < stammGroesse; b++) {
                    System.out.print("*");
                }

                // Abstand zwischen den Bäumen
                for (int s = 0; s < zeilen + 2; s++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
