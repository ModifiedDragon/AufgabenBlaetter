package de.nscr.blatt3;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

public class Aufgabe22 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;

    public Aufgabe22(SchlangenEingabe eingabe, PrintStream printStream) {
        // Palindrome
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
            out.println("Geben Sie die Wörter zum überprüfen der Palindrome ein (Format wort1,wort2)");
            try {
                String[] wort = Objects.requireNonNull(auslesen()).split(",");
                for (String string : wort) {
                    string = string.trim();
                    if (string.contentEquals(new StringBuilder(string).reverse())) {
                        out.println(string + " ist ein Palindrom");
                    }
                }
            } catch (IOException e) {
                out.println(e.getMessage());
            }
        }
        return false;
    }
}
