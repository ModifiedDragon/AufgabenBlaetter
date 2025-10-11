package de.nscr;

import de.nscr.gui.GUI;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 */
public class Main {
    static void main() {
        /// TODO Failsaves einfügen
        /// TODO Debugs entfernen
        /// TODO Kommentare einfügen
        /// TODO JavaDoc dokumentation verwenden
        /// TODO Namen der KLassen, Methoden, Variablen überprüfen
        /// TODO Anrede an den User überprüfen
        /// TODO Klassen static machen?
        GUI gui = new GUI(new Scanner(System.in));
        gui.setup();
        /*
        Scanner sc = new Scanner(System.in);
        System.out.println("Welche Aufgabe soll abgerufen werden? (1/2/3/4)");
        switch (sc.nextLine()) {
            case "1" -> new Aufgabe01(new JFrame());
            case "2" -> new Aufgabe02(new JFrame());
            case "3" -> new Aufgabe03(new JFrame());
            case "4" -> new Aufgabe04(new JFrame());
            default -> throw new IOException("Keine Zahl von 1-4 eingegeben");
        }
         */
    }
}
