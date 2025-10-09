package de.nscr.blatt1;

import java.util.Scanner;

public class Aufgabe03 {
    Scanner sc = new Scanner(System.in);

    public Aufgabe03() {
        start();
    }

    public void start() {
        System.out.println("Bitte geben Sie 0 für ein Kreis, 1 für ein Dreieck und 2 für ein Parallelogramm ein.");
        switch (sc.nextInt()) {
            case 0 :
                System.out.print("Bitte gebe den Radius des Kreises an: ");
                berechneFlaecheninhaltKreis(sc.nextInt());
                break;
            case 1 :
                System.out.print("Bitte gebe die Größe der Grundlfläche an: ");
                double temp1 = sc.nextDouble();
                System.out.print("Bitte gebe die Größe der Höhe an: ");
                double temp2 = sc.nextDouble();
                berechneFlaecheninhaltDreieck(temp1, temp2);
                break;
            case 2 :
                System.out.print("Bitte gebe die Größe der Grundlfläche an: ");
                double temp3 = sc.nextDouble();
                System.out.print("Bitte gebe die Größe der Höhe an: ");
                double temp4 = sc.nextDouble();
                berechneFlaecheninhaltParralelo(temp3, temp4);
                break;
            default :
                System.out.println("Bitte geben Sie eine Eingabe con 0 bis 2 ein.");
                start();
                break;
        }
        weiter();
    }

    private void weiter() {
        System.out.println("Willst du noch eine Sache abfragen? (y/n)");
        String zeile = sc.next();
        switch (zeile) {
            case "y" :
                start();
            case "n" :
                sc.close();
                break;
            default :
                System.out.println("Bitte gebe eine gültige Eingabe. (y/n)");
                weiter();
        }
    }

    public void berechneFlaecheninhaltKreis(double pRadius){
        double flaecheninhalt;

        flaecheninhalt = Math.PI * pRadius * pRadius;

        System.out.println("Der Flaecheninhalt des Kreises mit dem Radius " + pRadius +" beträgt " + flaecheninhalt);
    }

    public void berechneFlaecheninhaltDreieck(double pSeiteA,  double pSeiteB){
        double flaecheninhalt;

        flaecheninhalt = (pSeiteA * pSeiteB) / 2;

        System.out.println("Der Flaecheninhalt des Dreiecks mit den Seitenlängen " + pSeiteA +" und " + pSeiteB +" beträgt " + flaecheninhalt);
    }

    public void berechneFlaecheninhaltParralelo(double pHöhe, double pGrundseite) {
        double flaecheninhalt;

        flaecheninhalt = pHöhe *  pGrundseite;

        System.out.println("Der Flaecheninhalt des Parralelogram mit der höhe " + pHöhe +" und der Grundseitenlänge " + pGrundseite +" beträgt " + flaecheninhalt);
    }
}
