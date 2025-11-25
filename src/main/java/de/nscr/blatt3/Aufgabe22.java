package de.nscr.blatt3;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

/**
 * Aufgabe 3.2: Ein kleines Konsolenprogramm zur Palindrom-Erkennung.
 * <p>
 * Die Klasse liest Eingaben aus einer HTML-Konsole (über {@link SchlangenEingabe})
 * und überprüft, ob die eingegebenen Wörter Palindrome sind. Ergebnisse werden
 * über einen {@link PrintStream} ausgegeben.
 * </p>
 */
public class Aufgabe22 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;

    /**
     * Konstruktor der Klasse.
     * <p>
     * Initialisiert die Eingabe- und Ausgabeströme und startet die Hauptschleife,
     * in der wiederholt Eingaben abgefragt und überprüft werden, bis das Programm
     * beendet wird (z. B. durch Eingabe von "exit").
     * </p>
     *
     * @param eingabe     Eingabequelle (ähnlich wie {@code System.in}), liest Zeichen
     *                    aus der HTML-Konsole.
     * @param printStream Ausgabestream (ähnlich wie {@code System.out}), schreibt
     *                    Nachrichten in die HTML-Konsole.
     */
    public Aufgabe22(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        boolean weiter = true;
        while (weiter) {
            weiter = anfang();
        }
    }

    /**
     * Liest eine Zeile aus der Eingabequelle.
     * <p>
     * Die Methode sammelt Zeichen, bis ein Zeilenumbruch erreicht wird oder
     * das Ende der Eingabe erreicht ist. Leere Eingaben am Ende führen zu
     * {@code null}.
     * </p>
     *
     * @return die eingelesene Zeile als String, oder {@code null}, wenn keine Eingabe mehr vorhanden ist
     * @throws IOException falls beim Lesen ein Fehler auftritt
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
     * Führt eine Eingabe- und Prüfungsrunde aus.
     * <p>
     * Fragt den Benutzer nach Wörtern, überprüft jedes Wort auf Palindrom-Eigenschaft
     * und gibt das Ergebnis aus. Mit der Eingabe "exit" kann das Programm beendet werden.
     * </p>
     *
     * @return {@code true}, wenn eine Runde erfolgreich abgeschlossen wurde und eine neue gestartet werden soll;
     *         {@code false}, wenn das Programm beendet werden soll
     */
    private boolean anfang() {
        if (Thread.currentThread().isInterrupted()) return false;
        while (!Thread.currentThread().isInterrupted()) {
            out.println("Geben Sie die Wörter zum überprüfen der Palindrome ein (Format wort1 wort2)");
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
                    string = string.trim().toLowerCase();
                    if (string.contentEquals(new StringBuilder(string).reverse())) {
                        out.println(string + " ist ein Palindrom");
                    }
                }
                return true;
            } catch (IOException e) {
                out.println(e.getMessage());
            }
        }
        return false;
    }
}
