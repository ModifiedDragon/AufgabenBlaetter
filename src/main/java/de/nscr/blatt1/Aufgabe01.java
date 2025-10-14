package de.nscr.blatt1;

import de.nscr.gui.AufgabenGUI;
import de.nscr.gui.QueueInputStream;

import javax.swing.*;
import java.math.BigInteger;
import java.io.IOException;

/**
 *
 */
public class Aufgabe01 {
    private final AufgabenGUI gui;
    private final QueueInputStream qin;  // Direct qin

    /**
     *
     * @param frame
     * @param qin
     */
    public Aufgabe01(AufgabenGUI frame, QueueInputStream qin) {  // Takes qin
        this.gui = frame;
        this.qin = qin;
        anfang();
    }

    private String readLineFromQin() throws IOException {
        StringBuilder line = new StringBuilder();
        int b;
        while ((b = qin.read()) != -1) {  // Blocks on read() until data
            char c = (char) b;
            if (c == '\n') {
                break;  // Stop at newline
            }
            line.append(c);
        }
        String result = line.toString().trim();
        if (result.isEmpty() && b == -1) {
            return null;  // EOF
        }
        return result;
    }

    public void anfang() {
        System.out.println("Geben sie die erste Zahl ein von der sie die Fakultaet berechnen wollen: " );
        while (true) {
            try {
                String line = readLineFromQin();  // Custom read (blocks until full line)
                if (line == null) {
                    return;
                }
                int zahl = Integer.parseInt(line);  // Parse to int
                berechneFakultaet(zahl);
            } catch (NumberFormatException ex) {
                System.out.println("Es wurde keine Richtige Nummer eingegeben.");
            } catch (IOException ex) {
                System.out.println("Fehler beim Lesen der Eingabe: " + ex.getMessage());
            }
            weiter();
        }
    }

    public void weiter() {
        System.out.println("Wollen sie weitere Faklutaeten berechnen? (y/n)");
        while (true) {
            try {
                String zeile = readLineFromQin();  // Custom read (blocks until full line)
                if (zeile == null) {
                    break;
                }
                zeile = zeile.toLowerCase();
                if (zeile.equals("y")) {
                    anfang();
                    return;
                } else if (zeile.equals("n")) {
                    SwingUtilities.invokeLater(() -> {
                        gui.window.togglevisible();
                        gui.exit();
                    });
                    return;
                } else {
                    System.out.println("Bitte gebe eine gültige Eingabe von entweder 'y' oder 'n'.");
                }
            } catch (IOException ex) {
                System.out.println("Fehler beim Lesen der Eingabe: " + ex.getMessage());
                break;
            }
        }
    }

    public BigInteger berechneFakultaet(int pZahl) {
        BigInteger ergebnis = BigInteger.valueOf(1);  // Fixed: Use valueOf for consistency

        for (int i = pZahl; i > 0; i--) {
            ergebnis = ergebnis.multiply(BigInteger.valueOf(i));
        }

        System.out.println("Die Fakultaet von " + pZahl + " ist " + ergebnis);
        return ergebnis;
    }
}