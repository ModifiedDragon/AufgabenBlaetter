package de.nscr.blatt3;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

public class Aufgabe21 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;
    private String[][] feld;
    private boolean zug = false; // false: Spieler 1 (X), true: Spieler 2 (O)
    private int zugn = 0;

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
     * Liest eine Zeile von der Eingabe.
     * @return die gelesene Zeile oder null bei EOF
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