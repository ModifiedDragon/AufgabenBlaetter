package de.nscr.blatt1;

import de.nscr.gui.AufgabenGUI;
import de.nscr.gui.QueueInputStream;

import java.io.IOException;
import java.util.Objects;

/**
 *
 */
public class Aufgabe03 {
    private final AufgabenGUI gui;
    private final QueueInputStream qin;

    /**
     *
     */
    public Aufgabe03(AufgabenGUI frame, QueueInputStream qin) {
        gui = frame;
        this.qin = qin;
        start();
    }

    // Custom line reader: Reads bytes from qin until \n, no buffering or extra reads
    private String readLineFromQin() throws IOException {
        // Optional: Remove debug prints for production
        StringBuilder line = new StringBuilder();
        int b;
        while ((b = qin.read()) != -1) {  // Blocks on read() until data or EOF
            char c = (char) b;
            if (c == '\n') {
                break;  // Stop at newline
            }
            line.append(c);
        }
        String result = line.toString().trim();  // Trim extra spaces
        if (result.isEmpty() && b == -1) {
            return null;  // EOF reached
        }
        return result;
    }

    /**
     *
     */
    public void start() {
        System.out.println("Bitte geben Sie 0 für ein Kreis, 1 für ein Dreieck und 2 für ein Parallelogramm ein.");
        try {
            switch (Integer.parseInt(Objects.requireNonNull(readLineFromQin()))){
                case 0:
                    System.out.print("Bitte gebe den Radius des Kreises an: ");
                    berechneFlaecheninhaltKreis(Integer.parseInt(Objects.requireNonNull(readLineFromQin())));
                    break;
                case 1:
                    System.out.print("Bitte gebe die Größe der Grundlfläche an: ");
                    double temp1 = Double.parseDouble(Objects.requireNonNull(readLineFromQin()));
                    System.out.print("Bitte gebe die Größe der Höhe an: ");
                    double temp2 = Double.parseDouble(Objects.requireNonNull(readLineFromQin()));
                    berechneFlaecheninhaltDreieck(temp1, temp2);
                    break;
                case 2:
                    System.out.print("Bitte gebe die Größe der Grundlfläche an: ");
                    double temp3 = Double.parseDouble(Objects.requireNonNull(readLineFromQin()));
                    System.out.print("Bitte gebe die Größe der Höhe an: ");
                    double temp4 = Double.parseDouble(Objects.requireNonNull(readLineFromQin()));
                    berechneFlaecheninhaltParralelo(temp3, temp4);
                    break;
                default:
                    System.out.println("Bitte geben Sie eine Eingabe con 0 bis 2 ein.");
                    start();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        weiter();
    }

    /**
     *
     */
    private void weiter() {
        System.out.println("Willst du noch eine Sache abfragen? (y/n)");
        while (true) {
            try {
                String zeile = readLineFromQin();
                switch (zeile) {
                    case "y":
                        start();
                        return;
                    case "n":
                        gui.window.togglevisible();
                        gui.exit();
                        return;
                    default:
                        System.out.println("Bitte gebe eine gültige Eingabe. (y/n)");
                        weiter();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
