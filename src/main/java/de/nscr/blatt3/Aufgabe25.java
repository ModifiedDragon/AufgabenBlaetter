package de.nscr.blatt3;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;

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

    private boolean anfang() {
        if (Thread.currentThread().isInterrupted()) return false;
        while (!Thread.currentThread().isInterrupted()) {
            out.println("Geben Sie zum Berechnen Ihrer IBAN die BLZ und KontoNr ein.  (BLZ, KontoNr)");
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                else if (zeile.equals("exit")) {
                    out.println("Programm beendet");
                    return false;
                }
                String[] teile = zeile.split(",");
                if (teile.length == 2) {
                    String pBlz = teile[0];
                    String pKontoNr = teile[1];
                    berechneIBAN(pBlz.trim(), pKontoNr.trim());
                }
            } catch (IOException e) {
                out.println(e.getMessage());
            }
        }
        return false;
    }

    public static String berechneIBAN (String blz, String kontoNr){
        //ergänz DE Code und 00 als leerstelle
        String umgewandelt = blz + kontoNr + "131400";

        BigInteger big = new BigInteger(umgewandelt);
        int mod = big.mod(BigInteger.valueOf(97)).intValue();

        //berechnet die Prüfzahl
        int pruefe = 98 - mod;

        String pruefStr = String.format("%02d", pruefe);

        System.out.println("Der Ländercode ist: DE");
        System.out.println("Die Prüfziffer ist: " + pruefStr);
        System.out.println("Die BLZ ist: " + blz);
        System.out.println("Die Kontonummer ist: " + kontoNr);
        System.out.println("Die IBAN ist: DE" + pruefStr + blz + kontoNr);
        // IBAN erzeugen
        return "DE" + pruefStr + blz + kontoNr;
    }
}
