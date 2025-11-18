package de.nscr.blatt3;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

public class Aufgabe23 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;

    public Aufgabe23(SchlangenEingabe eingabe, PrintStream printStream) {
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
            out.println("Geben sie das Wort zur Umwandlung in ASCII Code an");
            out.println("Mit 'exit' kann auch alternativ das Programm geschlossen werden.");
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                if (zeile.equals("exit")) {
                    out.println("Programm beendet");
                    return false;
                }
                String[] wort = Objects.requireNonNull(zeile).split(" ");
                for (String string : wort) {
                    int[] temp = aSCII(string);
                    int summe = 0;
                    for (int i = 0; i < temp.length; i++) {
                        out.println("Der Charakter " + string.charAt(i) + " ist in ASCII Code: " + temp[i]);
                        summe += temp[i];
                    }
                    out.println("ASCII Summe aus dem Wort: " + summe);
                }
                return true;
            } catch (IOException e) {
                out.println(e.getMessage());
            }
        }
        return false;
    }

    public static int[] aSCII(String wort) {
        char[] arr =  wort.toCharArray();
        int temp = 0;
        int[] a =  new int[arr.length];
        for (char c : arr) {
            temp ++;
            a[temp - 1] = c;
        }
        return a;
    }
}
