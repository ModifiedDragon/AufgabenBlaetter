package de.nscr.blatt3;

import de.nscr.gui.SchlangenEingabe;

import java.io.PrintStream;

public class Aufgabe25 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;

    public Aufgabe25(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        boolean weiter = true;
        while (weiter) {
            weiter = anfang();
        }
    }

    private boolean anfang() {
        return false;
    }
}
