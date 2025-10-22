package de.nscr.blatt1;

import de.nscr.gui.AufgabenGUI;
import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 *
 */
public class Aufgabe04 {
    private final AufgabenGUI gui;
    private final SchlangenEingabe eingabe;
    int zahl;
    int ober;
    int unter;
    int versuchsAnzahl = 1;

    /**
     *
     * @param gui     Der Frame, der übergeben wird
     * @param eingabe Die Eingabe, welche zum Auslesen benutzt wird
     */
    public Aufgabe04(AufgabenGUI gui, SchlangenEingabe eingabe) {
        this.gui = gui;
        this.eingabe = eingabe;
        anfang();
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

    /**
     *
     */
    public void anfang() {
        System.out.println("In welchem Bereich raten? Wolle Sie raten (z.B. '3,100')");
        String[] teile;
        while (true) {
            try {
                String angabe = auslesen();
                teile = angabe.trim().split(",");

                if (teile.length != 2) {
                    System.out.println("Bitte geben Sie zwei Zahlen an.");
                    anfang();
                }
                int temp1 = Integer.parseInt(teile[0]);
                if (temp1 > Integer.parseInt(teile[1])) {
                    ober = temp1;
                    unter = Integer.parseInt(teile[1]);
                } else {
                    unter = temp1;
                    ober = Integer.parseInt(teile[1]);
                }
                Random random = new Random();
                // Ober und Untergrenze müssen um 1 erhöht werde, da Random mit 0 anfängt und nicht 1
                zahl = random.nextInt(unter + 1, ober + 1);
                raten();
                return;
            } catch (Exception e) {
                System.out.println("Bitte geben Sie eine richtige Angabe an.");
            }
        }
    }

    /**
     *
     */
    private void weiter() {
        System.out.println("Wollen Sie noch eine Sache abfragen? (y/n)");
        while (true) {
            try {
                String zeile = auslesen();
                switch (zeile) {
                    case "y":
                        anfang();
                        return;
                    case "n":
                        gui.gui.togglevisible();
                        gui.exit();
                        return;
                    default:
                        System.out.println("Bitte geben Sie eine gültige Eingabe. (y/n)");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    private void raten() {
        System.out.println("Raten Sie die Zahl im Bereich von " + unter + " und " + ober + ".");
        try {
            int temp2 = Integer.parseInt(Objects.requireNonNull(auslesen()));
            if (!vergleicheZahl(temp2)) {
                versuchsAnzahl++;
                raten();
            } else {
                System.out.println("Sie haben die Zahl in " + versuchsAnzahl + " versuchen erraten.");
                weiter();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param pWert ist die generierte Zahl, nach welcher der User sucht. Diese wird genutzt, um sie mit den Schätzungen, des User, zu vergleichen.
     * @return
     */
    public boolean vergleicheZahl(int pWert) { //pWert ist der schätzwert
        if (ober < pWert || unter < pWert) {
            System.out.println("Ihre Schätzung liegt außerhalb des definierten Bereichs");
            return false;
        } else if (zahl == pWert) {
            System.out.println("Sie haben die richtige Zahl erraten, SUPER!!!");
            return true;
        } else if (zahl > pWert) {
            System.out.println("Ihre Schätzung ist größer als die Zahl");
            return false;
        } else if (zahl < pWert) {
            System.out.println("Ihre Schätzung ist kleiner als die Zahl");
            return false;
        }
        return false;
    }
}

