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
        out.println("Geben Sie eine Zahl ein (Ganzzahl):");
        while (!Thread.currentThread().isInterrupted()) {  // Check in loop
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                if (zeile.equals("exit")) {
                    System.out.println("Programm beendet");
                    return false;
                }
                musterA(Integer.parseInt(zeile));
            } catch (IOException e) {
                System.out.println("Sie haben keine richtige Zahl eingegeben.");
                e.printStackTrace();
            }
        }
        return false;
    }

    public void musterA(int n) {
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append((i % 2 == 0 ? "+ " : " +").repeat(n));
            out.println(sb);
        }
    }
}
