package de.nscr.blatt3;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

/**
 * Aufgabe 3.1: Konsolenbasiertes Tic-Tac-Toe-Spiel.
 * <p>
 * Diese Klasse implementiert ein einfaches Tic-Tac-Toe-Spiel, das über die
 * HTML-Konsole gespielt wird. Spieler 1 verwendet das Symbol "X", Spieler 2
 * das Symbol "O". Die Spieler geben abwechselnd ihre Züge über Koordinaten
 * (1,1 bis 3,3) ein. Das Spielfeld wird nach jedem Zug aktualisiert und
 * ausgegeben. Das Spiel endet, wenn ein Spieler gewinnt, ein Unentschieden
 * erreicht wird oder der Benutzer "exit" eingibt.
 * </p>
 */
public class Aufgabe21 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;
    private String[][] feld;
    private boolean zug = false; // false: Spieler 1 (X), true: Spieler 2 (O)
    private int zugn = 0;

    /**
     * Konstruktor der Klasse.
     * <p>
     * Initialisiert Eingabe und Ausgabe sowie das Spielfeld. Führt die
     * Spielschleife aus, indem wiederholt {@link #anfang()} aufgerufen wird,
     * bis das Spiel beendet wird.
     * </p>
     *
     * @param eingabe     Eingabequelle (ähnlich wie {@code System.in}), liest Zeichen
     *                    aus der HTML-Konsole.
     * @param printStream Ausgabestream (ähnlich wie {@code System.out}), schreibt
     *                    Nachrichten in die HTML-Konsole.
     */
    public Aufgabe21(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        this.feld = new String[][] {{" ", " ", " "},{" ", " ", " "}, {" ", " ", " "}};
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
     * Startet eine neue Spielrunde Tic-Tac-Toe.
     * <p>
     * Spieler 1 beginnt mit "X", Spieler 2 mit "O". Jeder Spieler gibt
     * abwechselnd Koordinaten ein. Nach jedem Zug wird das Spielfeld
     * aktualisiert und ausgegeben. Das Spiel endet, wenn ein Spieler
     * gewinnt, ein Unentschieden erreicht wird oder "exit" eingegeben wird.
     * </p>
     *
     * @return {@code true}, wenn ein Spieler gewonnen hat und eine neue Runde
     *         gestartet werden soll; {@code false}, wenn das Spiel beendet wird
     */
    private boolean anfang() {
        if (Thread.currentThread().isInterrupted()) return false;
        zug = false;
        zugn = 0;
        feld = new String[][] {{" ", " ", " "},{" ", " ", " "}, {" ", " ", " "}}; // Reset grid
        out.println("Geben Sie abwecheselnd eine Eingabe an. Spieler 1 beginnt mit 'X' und dannach ist Spieler 2 mit 'O' dran.");
        out.println("Koordinaten: 1,1 bis 3,3 (z.B. 1,1 für oben links)");
        printGrid(feld); // Initial grid
        while (!Thread.currentThread().isInterrupted()) {
            String spieler = zug ? "O (Spieler 2)" : "X (Spieler 1)";
            out.println("Spieler " + spieler + " ist dran. Geben Sie Koordinaten ein (z.B. 1,1):");
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                if (zeile.equals("exit")) {
                    out.println("Programm beendet");
                    return false;
                }
                String[] wort = zeile.split(",");
                if (wort.length != 2) {
                    out.println("Eingabe muss genau 2 Koordinaten enthalten, getrennt durch Komma.");
                    continue;
                }
                try {
                    int px = Integer.parseInt(wort[0].trim());
                    int py = Integer.parseInt(wort[1].trim());
                    String symbol = eintragen(px, py);
                    if (symbol != null) {
                        if (pruefeGewonnen(feld, symbol)) {
                            out.println("---------------------Spieler " + (symbol.equals("X") ? "1" : "2") + " hat gewonnen!-------------------");
                            out.println(" ");
                            out.println(" ");
                            out.println(" ");
                            return true; // Neues Spiel starten
                        } else if (zugn == 9) {
                            out.println("Unentschieden!");
                            return false; // Programm beenden
                        }
                    }
                } catch (NumberFormatException e) {
                    out.println("Ungültige Eingabe: Koordinaten müssen Zahlen sein.");
                }
            } catch (IOException e) {
                out.println("Fehler beim Lesen: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Gibt das aktuelle Spielfeld als Tabelle in der Konsole aus.
     * <p>
     * Jede Zelle wird mit dem aktuellen Symbol ("X", "O" oder Leerzeichen)
     * dargestellt. Die Ausgabe ist formatiert mit Rahmenlinien, sodass das
     * Spielfeld klar erkennbar ist.
     * </p>
     *
     * @param grid zweidimensionales Array mit den Spielfeldinhalten
     */
    public void printGrid(String[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Bestimme Spaltenbreite
        int colWidth = 1; // Mindestens 1 für Leerzeichen/X/O
        for (String[] row : grid) {
            for (String val : row) {
                colWidth = Math.max(colWidth, val.length());
            }
        }
        colWidth += 2; // Padding

        // Horizontale Linie
        String horizontalBorder = "+";
        for (int c = 0; c < cols; c++) {
            horizontalBorder += "-".repeat(colWidth) + "+";
        }

        // Tabelle drucken
        for (int r = 0; r < rows; r++) {
            out.println(horizontalBorder);
            out.print("|");
            for (int c = 0; c < cols; c++) {
                String cell = grid[r][c];
                out.printf(" %-" + (colWidth - 1) + "s|", cell);
            }
            out.println();
        }
        out.println(horizontalBorder);
    }

    /**
     * Prüft, ob der angegebene Spieler das Spiel gewonnen hat.
     * <p>
     * Ein Spieler gewinnt, wenn er drei gleiche Symbole in einer Reihe,
     * Spalte oder Diagonale hat.
     * </p>
     *
     * @param feld    aktuelles Spielfeld
     * @param spieler das Symbol des Spielers ("X" oder "O")
     * @return {@code true}, wenn der Spieler gewonnen hat, sonst {@code false}
     */
    public boolean pruefeGewonnen(String[][] feld, String spieler) {
        // Zeilen prüfen
        for (int zeile = 0; zeile < 3; zeile++) {
            if (Objects.equals(feld[zeile][0], spieler) &&
                    Objects.equals(feld[zeile][1], spieler) &&
                    Objects.equals(feld[zeile][2], spieler)) {
                return true;
            }
        }

        // Spalten prüfen
        for (int spalte = 0; spalte < 3; spalte++) {
            if (Objects.equals(feld[0][spalte], spieler) &&
                    Objects.equals(feld[1][spalte], spieler) &&
                    Objects.equals(feld[2][spalte], spieler)) {
                return true;
            }
        }

        // Diagonale 1
        if (Objects.equals(feld[0][0], spieler) &&
                Objects.equals(feld[1][1], spieler) &&
                Objects.equals(feld[2][2], spieler)) {
            return true;
        }

        // Diagonale 2
        return Objects.equals(feld[0][2], spieler) &&
                Objects.equals(feld[1][1], spieler) &&
                Objects.equals(feld[2][0], spieler);
    }

    /**
     * Trägt den aktuellen Spielzug in das Spielfeld ein.
     * <p>
     * Der Spieler gibt die gewünschte Position über Koordinaten (1–3 für Zeile
     * und Spalte) an. Falls das Feld frei ist, wird das Symbol ("X" oder "O")
     * eingetragen und das Spielfeld neu ausgegeben. Falls das Feld bereits
     * belegt oder die Eingabe ungültig ist, wird eine entsprechende Meldung
     * ausgegeben.
     * </p>
     *
     * @param px Zeilenkoordinate (1–3)
     * @param py Spaltenkoordinate (1–3)
     * @return das Symbol ("X" oder "O"), wenn der Zug erfolgreich war;
     *         {@code null}, wenn der Zug ungültig war
     */
    public String eintragen(int px, int py) {
        int x = px - 1;
        int y = py - 1;

        if (x >= 0 && x < 3 && y >= 0 && y < 3) {
            if (feld[x][y].equals(" ")) {
                String symbol = zug ? "O" : "X";
                feld[x][y] = symbol;
                printGrid(feld);
                zug = !zug;
                zugn++;
                return symbol;
            } else {
                out.println("Feld ist bereits belegt. Geben Sie andere Koordinaten ein.");
                printGrid(feld);
                return null;
            }
        } else {
            out.println("Ungültige Koordinaten. Geben Sie Werte zwischen 1 und 3 ein.");
            return null;
        }
    }
}