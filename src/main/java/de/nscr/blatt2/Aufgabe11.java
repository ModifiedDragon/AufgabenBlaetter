package de.nscr.blatt2;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

/**
 * Dies ist die Aufgabe 2.1 oder auch die Aufgabe aus Testat 2.
 * Sie wird mit Buttons auf der Website ausgeführt
 */
public class Aufgabe11 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;

    /**
     * Konstruktor, welcher keine Übergabeparameter bekommen hat. Dieser führt die Abfrage und ausführung in Anfang wiederholt durch, bis false zurückgegeben wird.
     * @param eingabe
     * @param printStream
     */
    public Aufgabe11(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        boolean weiter = true;
        while (weiter) {
            weiter = anfang();
        }
    }

    /**
     * Liest eine Zeile aus dem Eingabestrom ein.
     *
     * @return eingelesene Zeile ohne Zeilenumbruch, oder null bei EOF
     * @throws IOException falls ein Lesefehler auftritt
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
     * anfang ist die Methode, welche ausgeführt wird, um abzufragen, welches Muster und welche Größen benutzt werden sollen.
     * Dabei wird dies nur benutzt, wenn keine Übergabeparameter gegeben sind und anfang wird wiederholt aufgerufen, bis 'exit' in der Kommandozeile eingegeben wird.
     * @return Gibt einen boolean Wert, welcher bei der Eingabe 'exit' false zurückgibt. Solange true returnt wird, wird das Programm wiederholt ausgeführt.
     */
    public boolean anfang() {
        if (Thread.currentThread().isInterrupted()) return false;
        while (!Thread.currentThread().isInterrupted()) {
            out.println("Geben Sie an welches Muster Sie haben wollen? (a, b, c)");
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                switch (zeile) {
                    case "a":
                        out.println("Geben Sie die größe des Musters an. (+ Basisgröße)");
                        String temp = auslesen();
                        if (Thread.currentThread().isInterrupted()) return false;
                        assert temp != null;
                        musterA(Integer.parseInt(temp));
                        return true;
                    case "b":
                        out.println("Geben Sie die größe des Musters an.");
                        String temp2 = auslesen();
                        if (Thread.currentThread().isInterrupted()) return false;
                        assert temp2 != null;
                        musterB(Integer.parseInt(temp2));
                        return true;
                    case "c":
                        out.println("Geben Sie die größe des Musters pro Baum an und die Anzahl der Bäume. (z. B. 10,3)");
                        String[] temp3 = Objects.requireNonNull(auslesen()).split(",");
                        if (Thread.currentThread().isInterrupted()) return false;
                        if (temp3.length < 2) {
                            out.println("Unter 2 eingaben. Stoppe Programm.");
                            return false;
                        }
                        musterC(Integer.parseInt(temp3[0]), Integer.parseInt(temp3[1]));
                        return true;
                    default:
                        out.println("Keine richtige Eingabe (a,b,c)");
                        return true;
                }
            } catch (NumberFormatException e) {
                out.println("Bitte gebe eine richtige Angabe an.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    /**
     * Es wird die Zahl n genommen und zur Basis addiert
     * darauf wird ein Karomuster mir + und leerzeichen erstellt
     * @param n bestimmt die größer des Musters
     */
    public void musterA(int n) {
        for (int i = 0; i < n + 7; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append((i % 2 == 0 ? "+ " : " +").repeat(9 + n));
            out.println(sb);
        }
    }

    /**
     * Der Parameter zeilen wird genutz um die Tiefe des Musters zubestimmen.
     * Abhängig von den Zeilen wird dann eine Pyramidenförmiges Muster erstellt
     * mit dem + zeichen
     * @param zeilen bestimmt die größe des Musters
     */
    public void musterB(int zeilen) {
        for (int i = 1; i <= zeilen; i++) {
            // Leerzeichen drucken
            for (int j = i; j < zeilen; j++) {
                out.print(" ");
            }

            // Pluszeichen drucken
            for (int k = 1; k <= (2 * i - 1); k++) {
                out.print("+");
            }

            // Zeilenumbruch
            out.println();
        }
    }

    /**
     ** Zeichnet eine Reihe von Tannenbaum-Mustern in der Konsole.
     *
     * Jeder Baum besteht aus einem dreieckigen Baumteil und einem Stamm.
     * Es können mehrere Bäume nebeneinander gezeichnet werden.
     *
     * @param pZeilen bestimmt die größe der Bäume
     * @param anzahl bestimmt die Anzahl der Bäume
     */
    public void musterC(int pZeilen, int anzahl) {
        if (pZeilen < 1 || anzahl < 1) {
            out.println("Falsche Angabe");
            return;
        }
        int zeilen = pZeilen % 2 == 0 ? pZeilen / 2 : pZeilen / 2 + 1;
        for (int i = 1; i <= zeilen; i++) {
            for (int p = 1; p <= anzahl; p++) {

                // Leerzeichen vor Sternen
                for (int j = i; j < zeilen; j++) {
                    out.print(" ");
                }

                // Sterne drucken
                for (int k = 1; k <= (2 * i - 1); k++) {
                    out.print("*");
                }

                // Abstand zwischen den Bäumen
                for (int s = 0; s < zeilen - i; s++) {
                    out.print(" ");
                }
            }
            out.println();
        }

        // Stammgröße berechnen
        int stammGroesse = (zeilen / 10) + 1;  // alle 10 Zeilen wächst der Stamm um 1
        if (stammGroesse < 1) {
            stammGroesse = 1;
        }

        // Stamm zeichnen
        for (int stammReihe = 0; stammReihe < stammGroesse; stammReihe++) {
            for (int p = 1; p <= anzahl; p++) {

                // Leerzeichen vor Stamm
                for (int j = 1; j < zeilen - stammGroesse / 2; j++) {
                    out.print(" ");
                }

                // Stamm drucken
                for (int b = 0; b < stammGroesse; b++) {
                    out.print("*");
                }

                // Abstand zwischen den Bäumen
                for (int s = 0; s < zeilen - 1; s++) {
                    out.print(" ");
                }
            }
            out.println();
        }
    }
}
