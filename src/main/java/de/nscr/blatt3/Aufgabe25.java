package de.nscr.blatt3;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;

/**
 * Aufgabe 3.5 des Aufgabenblattes
 * Konsolenanwendung zur Berechnung deutscher IBANs aus BLZ und Kontonummer.
 * Die Eingaben erfolgen über ein SchlangenEingabe-Objekt (ähnlich wie System.in),
 * die Ausgaben über einen PrintStream (ähnlich wie System.out).
 */
public class Aufgabe25 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;

    /**
     * Konstruktor, welcher keine Übergabeparameter bekommen hat. Dieser führt die Abfrage und ausführung in Anfang wiederholt durch, bis false zurückgegeben wird.
     * @param eingabe Eingabe ist eine selbsterstellte Klasse, welche die Eingabe aus der Html Konsole ausließt und übergibt. Hierbei wird bei der Aufgabe die instanz zum Auslesen übergeben. Agiert ähnlich wie System.in
     * @param printStream ist die Ausgabe aus dem Code, welche zu der Html Website Konsole geleitet und übergeben wird. Agiert ähnlich wie System.out
     */
    public Aufgabe25(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        boolean weiter = true;
        while (weiter) {
            weiter = anfang();
        }
    }


    /**
     * Liest eine Zeile aus dem Eingabestrom ein.
     *
     * @return eingelesene Zeile ohne Zeilenumbruch oder null, falls EOF erreicht
     * @throws IOException bei Lesefehlern
     *
     * Hier wird Zeichen für Zeichen gelesen, bis ein '\n' oder -1 (EOF) erreicht wird.
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
     * Startet die Eingabeaufforderung für BLZ und Kontonummer und verarbeitet die Eingaben.
     *
     * @return true, wenn das Programm weiterlaufen soll, false bei Programmende
     *
     * Ablauf:
     * 1. Nutzer wird aufgefordert, BLZ und Kontonummer einzugeben.
     * 2. Eingabe wird gelesen.
     * 3. Bei "exit" wird das Programm beendet.
     * 4. Bei gültiger Eingabe wird die IBAN berechnet.
     */

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

    /**
     * Berechnet eine deutsche IBAN aus BLZ und Kontonummer nach dem Mod-97-Verfahren.
     *
     * @param blz Bankleitzahl
     * @param kontoNr Kontonummer
     * @return berechnete IBAN als String
     *
     * Algorithmus:
     * 1. Ländercode DE in Zahlen umwandeln: D=13, E=14
     * 2. DE00 an die BLZ und Kontonummer anhängen: BLZ + KontoNr + 131400
     * 3. Modulo 97 berechnen und Prüfziffer: 98 - (mod 97)
     * 4. IBAN zusammenstellen: DE + Prüfziffer + BLZ + KontoNr
     */
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
