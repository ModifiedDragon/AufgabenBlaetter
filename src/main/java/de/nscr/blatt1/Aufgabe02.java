package de.nscr.blatt1;

import de.nscr.gui.AufgabenGUI;
import de.nscr.gui.QueueInputStream;

import java.io.IOException;

public class Aufgabe02 {
    private final AufgabenGUI gui;
    private final QueueInputStream qin;
    int pZahl;

    public Aufgabe02(AufgabenGUI frame, QueueInputStream qin) {
        gui = frame;
        this.qin = qin;
        pZahlBerechnen(pZahl);
    }

    // Custom line reader: Reads bytes from qin until \n, no buffering or extra reads
    private String readLineFromQin() throws IOException {
        StringBuilder line = new StringBuilder();
        int b;
        while ((b = qin.read()) != -1) {  // Blocks on read() until data or EOF
            char c = (char) b;
            if (c == '\n') {
                break;  // Stop at newline
            }
            line.append(c);
        }
        String result = line.toString().trim();  // Trim extra spaces
        if (result.isEmpty() && b == -1) {
            return null;  // EOF reached
        }
        return result;
    }

    private void weiter() {
        System.out.println("Willst du noch eine Sache abfragen? (y/n)");
        while (true) {
            try {
                String zeile = readLineFromQin();
                switch (zeile) {
                    case "y":

                        return;
                    case "n":
                        gui.window.togglevisible();
                        gui.exit();
                        return;
                    default:
                        System.out.println("Bitte gebe eine gültige Eingabe. (y/n)");
                        weiter();
                }
            } catch (IOException e) {
                System.out.println("Bitte gebe eine gültige Eingabe. (y/n)");
            }
        }
    }

    private void pZahlBerechnen(int pZahl) {
        ///  Methode der Primzahlzerlegung aussuchen

    }

    /*
    private void teiler(int zahl) {
        for (int i = 0; i < zahl / 2; i++) {
            switch(i) {
                case i%2==0 -> continue;
                case i%3==0 -> continue;
                case i%5==0 -> continue;
                case i%7==0 -> continue;
            }

        }
    }

     */

}
