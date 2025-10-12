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
        gui.setup(0);
    }
}
