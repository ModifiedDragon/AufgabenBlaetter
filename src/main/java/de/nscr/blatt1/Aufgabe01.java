package de.nscr.blatt1;
import java.math.BigInteger;
import java.util.Scanner;

public class Aufgabe01 {

    static Scanner sc = new Scanner(System.in);


    public Aufgabe01() {
        anfang();
    }

    public void anfang() {
        System.out.print("Geben sie die erste Zahl ein von der sie die Fakultaet berechnen wollen: " );
        berechneFakultaet(sc.nextInt());
        System.out.println("Wollen sie weitere Faklutaeten berechnen? (y/n)");
        weiter();
    }

    public void weiter () {
        String zeile = sc.nextLine();
        if (zeile.equals("y")) {
            anfang();
        } else if (zeile.equals("n")) {
            sc.close();
        } else {
            System.out.println("Bitte gebe eine gültige Eingabe von entweder 'y' oder 'n'.");
            weiter();
        }
    }

    public BigInteger berechneFakultaet(int pZahl) {
        BigInteger ergebnis = new BigInteger("1");

        for (int i = pZahl; i > 0; i--) {
            BigInteger i1 = BigInteger.valueOf(i);
            ergebnis = ergebnis.multiply(i1);
        }

        System.out.println("Die Fakultaet von " + pZahl + " ist " + ergebnis);
        return ergebnis;
    }
}