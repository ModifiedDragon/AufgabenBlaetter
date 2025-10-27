package de.nscr.blatt1;

import de.nscr.archive.AufgabenGUI;
import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.util.Objects;

/**
 *
 */
public class Aufgabe03 {
    private final SchlangenEingabe eingabe;

    /**
     *
     * @param eingabe Die Eingabe, welche zum Auslesen benutzt wird
     */
    public Aufgabe03(SchlangenEingabe eingabe) {
        this.eingabe = eingabe;
        start();
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
    public void start() {
        ///  TODO while schleife mit weiter geht nicht
        System.out.println("Bitte geben Sie 0 für ein Kreis, 1 für ein Dreieck und 2 für ein Parallelogramm ein.");
        while (true) {
            try {
                switch (Integer.parseInt(Objects.requireNonNull(auslesen()))) {
                    case 0:
                        System.out.print("Bitte geben Sie den Radius des Kreises an: ");
                        berechneFlaecheninhaltKreis(Integer.parseInt(Objects.requireNonNull(auslesen())));
                        break;
                    case 1:
                        System.out.print("Bitte geben Sie die Größe der Grundfläche an: ");
                        double temp1 = Double.parseDouble(Objects.requireNonNull(auslesen()));
                        System.out.print("Bitte geben Sie die Größe der Höhe an: ");
                        double temp2 = Double.parseDouble(Objects.requireNonNull(auslesen()));
                        berechneFlaecheninhaltDreieck(temp1, temp2);
                        break;
                    case 2:
                        System.out.print("Bitte geben Sie die Größe der Grundfläche an: ");
                        double temp3 = Double.parseDouble(Objects.requireNonNull(auslesen()));
                        System.out.print("Bitte geben Sie die Größe der Höhe an: ");
                        double temp4 = Double.parseDouble(Objects.requireNonNull(auslesen()));
                        berechneFlaecheninhaltParallelogramm(temp3, temp4);
                        break;
                    default:
                        System.out.println("Bitte geben Sie eine Eingabe con 0 bis 2 ein.");
                        start();
                        break;
                }
            } catch (IOException e) {
                System.out.println("Es wurde keine Richtige Nummer eingegeben (Ganzzahl).");
            }
            boolean temp = weiter();
            if (!temp) {
                break;
            }
        }
    }

    /**
     *
     */
    private boolean weiter() {
        System.out.println("Wollen Sie noch eine Sache abfragen? (y/n)");
        while (true) {
            try {
                String zeile = auslesen();
                switch (zeile) {
                    case "y":
                        start();
                        return true;
                    case "n":
                        System.out.println("Aufgabe beendet.");
                        return false;
                    default:
                        System.out.println("Bitte geben Sie eine gültige Eingabe. (y/n)");
                }
            } catch (IOException e) {
                System.out.println("Bitte geben Sie eine gültige Eingabe. (y/n)");
            }
        }
    }

    /**
     *
     * @param pRadius ist die Eingabe vom User, die genutzt wird zur Berechnung des Flächeninhalts
     */
    public void berechneFlaecheninhaltKreis(double pRadius) {
        double flaecheninhalt;

        flaecheninhalt = Math.PI * pRadius * pRadius;

        System.out.println("Der Flächeninhalt des Kreises mit dem Radius " + pRadius + " beträgt " + flaecheninhalt);
    }

    /**
     *
     * @param pSeiteA ist die Eingabe vom User, die genutzt wird zur Berechnung des Flächeninhalts
     * @param pSeiteB ist die Eingabe vom User, die genutzt wird zur Berechnung des Flächeninhalts
     */
    public void berechneFlaecheninhaltDreieck(double pSeiteA, double pSeiteB) {
        double flaecheninhalt;

        flaecheninhalt = (pSeiteA * pSeiteB) / 2;

        System.out.println("Der Flächeninhalt des Dreiecks mit den Seitenlängen " + pSeiteA + " und " + pSeiteB + " beträgt " + flaecheninhalt);
    }

    /**
     *
     * @param pHoehe      ist die Eingabe vom User, die genutzt wird zur Berechnung des Flächeninhalts
     * @param pGrundseite ist die Eingabe vom User, die genutzt wird zur Berechnung des Flächeninhalts
     */
    public void berechneFlaecheninhaltParallelogramm(double pHoehe, double pGrundseite) {
        double flaecheninhalt;

        flaecheninhalt = pHoehe * pGrundseite;

        System.out.println("Der Flächeninhalt des Parallelogram mit der Höhe " + pHoehe +" und der Grundseitenlänge " + pGrundseite +" beträgt " + flaecheninhalt);
    }
}
