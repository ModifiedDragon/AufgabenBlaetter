package de.nscr.blatt3;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

/**
 * Aufgabe 3.3: Konsolenprogramm zur ASCII-Umwandlung.
 * <p>
 * Diese Klasse liest Wörter von der Konsole ein und gibt für jeden Buchstaben
 * den entsprechenden ASCII-Code sowie die Gesamtsumme der Codes aus.
 * Eingaben erfolgen über ein {@link SchlangenEingabe}-Objekt, Ausgaben über
 * einen {@link PrintStream}.
 * </p>
 */
public class Aufgabe23 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;

    /**
     * Konstruktor der Klasse.
     * <p>
     * Initialisiert Eingabe und Ausgabe und startet die Hauptschleife, in der
     * wiederholt {@link #anfang()} aufgerufen wird, bis das Programm beendet wird.
     * </p>
     *
     * @param eingabe     Eingabequelle (ähnlich wie {@code System.in}), liest Zeichen
     *                    aus der HTML-Konsole.
     * @param printStream Ausgabestream (ähnlich wie {@code System.out}), schreibt
     *                    Nachrichten in die HTML-Konsole.
     */
    public Aufgabe23(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        boolean weiter = true;
        while (weiter) {
            weiter = anfang();
        }
    }

    /**
     * Liest eine Zeile aus dem Eingabestrom ein.
     * <p>
     * Die Methode sammelt Zeichen bis zum Zeilenumbruch oder bis das Ende
     * der Eingabe erreicht ist. Leere Eingaben am Ende führen zu {@code null}.
     * </p>
     *
     * @return eingelesene Zeile ohne Zeilenumbruch, oder {@code null} bei EOF
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
     * Führt eine Eingabe- und Umwandlungsrunde aus.
     * <p>
     * Fragt den Benutzer nach einem Wort, wandelt jeden Buchstaben in seinen
     * ASCII-Code um und gibt die Codes sowie die Gesamtsumme aus. Mit der
     * Eingabe "exit" kann das Programm beendet werden.
     * </p>
     *
     * @return {@code true}, wenn eine Runde erfolgreich abgeschlossen wurde und
     *         eine neue gestartet werden soll; {@code false}, wenn das Programm
     *         beendet werden soll
     */
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

    /**
     * Wandelt ein Wort in ein Array der entsprechenden ASCII-Werte um.
     * <p>
     * Jeder Buchstabe des Wortes wird in seinen numerischen ASCII-Code
     * konvertiert und in einem Array gespeichert.
     * </p>
     *
     * @param wort das umzuwandelnde Wort
     * @return Array der ASCII-Codes des Wortes
     */
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
