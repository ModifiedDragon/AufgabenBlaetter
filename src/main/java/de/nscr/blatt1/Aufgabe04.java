package de.nscr.blatt1;

import de.nscr.gui.GUI;

import java.util.Random;
import java.util.Scanner;

/**
 *
 */
public class Aufgabe04 {
    int zahl;
    int ober;
    int unter;
    int anzahl = 1;
    Scanner scanner;

    /**
     *
     */
    public Aufgabe04(GUI frame) {
        frame.exit();
        scanner = frame.input;
        anfang();
    }

    /**
     *
     */
    public void anfang() {
        System.out.println("In Welchem Bereich willst du raten? z.B. '3,100'");
        String[] teile;
        String angabe = scanner.nextLine();
        teile = angabe.trim().split(",");

        if (teile.length != 2) {
            System.out.println("Bitte gebe zwei Zahlen an.");
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
    }

    /**
     *
     */
    private void weiter() {
        System.out.println("Willst du noch eine Sache abfragen? (y/n)");
        String zeile = scanner.next();
        // Diese Zeile leert den Zeilen umbruch (56)
        scanner.nextLine();
        switch (zeile) {
            case "y" :
                anfang();
                break;
            case "n" :
                GUI gui = new GUI(scanner);
                gui.setup();
                break;
            default :
                System.out.println("Bitte gebe eine gültige Eingabe. (y/n)");
                weiter();
        }
    }

    /**
     *
     */
    private void raten() {
        System.out.println("Rate die Zahl im Bereich von " + unter + " und " + ober + ".");
        int temp2 = scanner.nextInt();
        if (!vergleicheZahl(temp2)) {
            anzahl++;
            raten();
        } else {
            System.out.println("Du hast die Zahl in " + anzahl + " versuchen erraten.");
            weiter();
        }
    }

    /**
     *
     * @param pWert
     * @return
     */
    public boolean vergleicheZahl(int pWert){ //pWert ist der schätzwert
        if (zahl == pWert) {
            System.out.println("Sie haben die richtige Zahl erraten, SUPER!!!");
            return true;
        } else if (zahl > pWert) {
            System.out.println("Ihre schaetzung ist groeßer als die Zahl");
            return false;
        } else if (zahl < pWert) {
            System.out.println("Ihre schaetzung ist kleiner als die Zahl");
            return false;
        } else if (ober < pWert || unter < pWert) {
            System.out.println("Ihre schaetzung liegt außerhalb des definierten Bereichs");
            return false;
        }
        return false;
    }
}

