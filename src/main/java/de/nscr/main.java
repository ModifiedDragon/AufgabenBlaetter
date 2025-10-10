package de.nscr;

import de.nscr.blatt1.Aufgabe01;
import de.nscr.blatt1.Aufgabe02;
import de.nscr.blatt1.Aufgabe03;
import de.nscr.blatt1.Aufgabe04;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 */
public class main {
    static void main() throws IOException {
        /// TODO Failsaves einfügen
        /// TODO Debugs entfernen
        /// TODO Kommentare einfügen
        /// TODO JavaDoc dokumentation verwenden
        /// TODO Namen der KLassen, Methoden, Variablen überprüfen
        /// TODO Anrede an den User überprüfen
        /// TODO Klassen static machen?
        Scanner sc = new Scanner(System.in);
        System.out.println("Welche Aufgabe soll abgerufen werden? (1/2/3/4)");
        switch (sc.nextLine()) {
            case "1" -> new Aufgabe01();
            case "2" -> new Aufgabe02();
            case "3" -> new Aufgabe03();
            case "4" -> new Aufgabe04();
            default -> throw new IOException("Keine Zahl von 1-4 eingegeben");
        }
    }
}
