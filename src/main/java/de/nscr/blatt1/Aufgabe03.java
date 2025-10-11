package de.nscr.blatt1;

import de.nscr.gui.GUI;

import java.util.Scanner;

/**
 *
 */
public class Aufgabe03 {
    Scanner scanner;

    /**
     *
     */
    public Aufgabe03(GUI frame) {
        frame.exit();
        scanner = frame.input;
        start();
    }

    /**
     *
     */
    public void start() {
        System.out.println("Bitte geben Sie 0 für ein Kreis, 1 für ein Dreieck und 2 für ein Parallelogramm ein.");
        switch (scanner.nextInt()) {
            case 0 :
                System.out.print("Bitte gebe den Radius des Kreises an: ");
                berechneFlaecheninhaltKreis(scanner.nextInt());
                break;
            case 1 :
                System.out.print("Bitte gebe die Größe der Grundlfläche an: ");
                double temp1 = scanner.nextDouble();
                System.out.print("Bitte gebe die Größe der Höhe an: ");
                double temp2 = scanner.nextDouble();
                berechneFlaecheninhaltDreieck(temp1, temp2);
                break;
            case 2 :
                System.out.print("Bitte gebe die Größe der Grundlfläche an: ");
                double temp3 = scanner.nextDouble();
                System.out.print("Bitte gebe die Größe der Höhe an: ");
                double temp4 = scanner.nextDouble();
                berechneFlaecheninhaltParralelo(temp3, temp4);
                break;
            default :
                System.out.println("Bitte geben Sie eine Eingabe con 0 bis 2 ein.");
                start();
                break;
        }
        weiter();
    }

    /**
     *
     */
    private void weiter() {
        System.out.println("Willst du noch eine Sache abfragen? (y/n)");
        String zeile = scanner.next();
        switch (zeile) {
            case "y" :
                start();
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
     * @param pRadius
     */
    public void berechneFlaecheninhaltKreis(double pRadius){
        double flaecheninhalt;

        flaecheninhalt = Math.PI * pRadius * pRadius;

        System.out.println("Der Flaecheninhalt des Kreises mit dem Radius " + pRadius +" beträgt " + flaecheninhalt);
    }

    /**
     *
     * @param pSeiteA
     * @param pSeiteB
     */
    public void berechneFlaecheninhaltDreieck(double pSeiteA,  double pSeiteB){
        double flaecheninhalt;

        flaecheninhalt = (pSeiteA * pSeiteB) / 2;

        System.out.println("Der Flaecheninhalt des Dreiecks mit den Seitenlängen " + pSeiteA +" und " + pSeiteB +" beträgt " + flaecheninhalt);
    }

    /**
     *
     * @param pHoehe
     * @param pGrundseite
     */
    public void berechneFlaecheninhaltParralelo(double pHoehe, double pGrundseite) {
        double flaecheninhalt;

        flaecheninhalt = pHoehe *  pGrundseite;

        System.out.println("Der Flaecheninhalt des Parralelogram mit der höhe " + pHoehe +" und der Grundseitenlänge " + pGrundseite +" beträgt " + flaecheninhalt);
    }
}
