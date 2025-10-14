package de.nscr.blatt1;

import de.nscr.gui.AufgabenGUI;
import de.nscr.gui.QueueInputStream;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class Aufgabe02 {
    private final AufgabenGUI gui;
    private final QueueInputStream qin;

    /**
     *
     * @param frame
     * @param qin
     */
    public Aufgabe02(AufgabenGUI frame, QueueInputStream qin) {
        gui = frame;
        this.qin = qin;
        ausführen();

    }

    // Custom line reader: Reads bytes from qin until \n, no buffering or extra reads

    /**
     *
     * @return
     * @throws IOException
     */
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
                        ausführen();
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

    /**
     *
     */
    public void ausführen() {
        System.out.println("Welche Zahl willst du als Primzahl überprüfen?");
        try {
            int pZahl = Integer.parseInt(readLineFromQin());
            pZahlBerechnen(pZahl, true);
            weiter();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param pZahl
     * @param ersteDurchlauf
     * @return
     */
    private boolean pZahlBerechnen(int pZahl, boolean ersteDurchlauf) {
        ///TODO überprüfen,
        ArrayList<Integer> teiler = new ArrayList<>();
        // Auflisten aller Teiler
        for (int i = 1; i <= pZahl / 2; i++) {
            if (pZahl % i == 0) {
                teiler.add(i);
            }
        }
        teiler.add(pZahl);
        if (teiler.size() == 2) {
            if (ersteDurchlauf) {
                System.out.println(pZahl + " ist eine Primzahl.");
            }
            return true;
        } else {
            if (ersteDurchlauf) {
                System.out.println(pZahl + " ist durch 1 teilbar und diese ist eine Primzahl.");
                for (int i = 0; i < teiler.size(); i++) {
                    boolean prim = pZahlBerechnen(teiler.get(i), false);
                    if (prim) {
                        System.out.println(pZahl + " ist durch " + teiler.get(i) + " teilbar und diese ist eine Primzahl.");
                    }
                }
                if (teiler.size() == 2) {
                    System.out.println(pZahl + " ist eine Primzahl.");
                } else {
                    System.out.println(pZahl + " ist keine Primzahl.");
                }
            }
        }
        return false;
    }

}
