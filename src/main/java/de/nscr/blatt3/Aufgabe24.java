package de.nscr.blatt3;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

public class Aufgabe24 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;

    public Aufgabe24(SchlangenEingabe eingabe, PrintStream printStream) {
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

    private boolean anfang() {
        if (Thread.currentThread().isInterrupted()) return false;
        while (!Thread.currentThread().isInterrupted()) {
            boolean versch = true;
            out.println("Wollen Sie einen Text Entschlüsseln oder Verschlüsseln? (ent/ver)");
            out.println("Bei keiner richtigen eingabe wird als Standart auf Verschlüsseln gegangen");
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                else if (zeile.equals("exit")) {
                    out.println("Programm beendet");
                    return false;
                } else if (zeile.equals("ent")) {
                    versch = false;
                }
            } catch (IOException e) {
                out.println(e.getMessage());
            }
            out.println("Geben Sie den Text und den Schlüssel an: (Wort, 10)");
            out.println("Sie können so viel Text eingeben wie Sie wollen, aber ein Komma kann nur am ende und vor dem Schlüssel kommen");
            out.println("Es werden dabei alle Zeichen, welche nicht Basisbuchstaben des Lateinischen Alphabetes sind, ignoriert.");
            out.println("Der Schlüssel gibt die Verschiebung nach rechts an und wiederholt sich falls über 26");
            out.println("Mit 'exit' kann auch alternativ das Programm geschlossen werden.");
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                if (zeile.equals("exit")) {
                    out.println("Programm beendet");
                    return false;
                }
                String[] woerter = Objects.requireNonNull(zeile).split(" ");

            } catch (IOException e) {
            out.println(e.getMessage());
        }
    }
        return false;
    }
}
