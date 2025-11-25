package de.nscr.blatt3;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

/**
 * Diese Klasse implementiert eine einfache Verschlüsselungs- und Entschlüsselungsroutine
 * nach dem Caesar-Verschiebeverfahren. Die Eingaben erfolgen über ein
 * {@link SchlangenEingabe}-Objekt, die Ausgaben über einen {@link PrintStream}.
 */
public class Aufgabe24 {
    private final SchlangenEingabe eingabe;
    private final PrintStream out;
    boolean verschluesseln = true;

    /**
     * Konstruktor, welcher keine Übergabeparameter bekommen hat. Dieser führt die Abfrage und ausführung in Anfang wiederholt durch, bis false zurückgegeben wird.
     * @param eingabe Eingabe ist eine selbsterstellte Klasse, welche die Eingabe aus der Html Konsole ausließt und übergibt. Hierbei wird bei der Aufgabe die instanz zum Auslesen übergeben. Agiert ähnlich wie System.in
     * @param printStream ist die Ausgabe aus dem Code, welche zu der Html Website Konsole geleitet und übergeben wird. Agiert ähnlich wie System.out
     */
    public Aufgabe24(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        boolean weiter = true;
        while (weiter) {
            weiter = anfang();
        }
    }

    /**
     * Liest eine einzelne Zeile von der Eingabequelle {@link SchlangenEingabe} ein.
     *
     * <p>Die Methode liest Zeichen so lange ein, bis ein Zeilenumbruch ('\n') oder
     * das Ende der Eingabe (-1) erreicht wird. Anschließend wird der gelesene String
     * getrimmt (führende und nachfolgende Leerzeichen entfernt) und zurückgegeben.</p>
     *
     * <p>Wenn die Zeile leer ist und das Ende der Eingabe erreicht wurde, wird null zurückgegeben.</p>
     *
     * @return die eingelesene und getrimmte Zeile, oder null wenn das Ende der Eingabe erreicht wurde
     * @throws IOException falls ein Ein-/Ausgabefehler beim Lesen auftritt
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
     * Startet die Abfrage, ob verschlüsselt oder entschlüsselt werden soll.
     *
     * @return
     */
    private boolean anfang() {
        if (Thread.currentThread().isInterrupted()) return false;
        while (!Thread.currentThread().isInterrupted()) {
            out.println("Wollen Sie einen Text Entschlüsseln oder Verschlüsseln? (ent/ver)");
            out.println("Bei keiner richtigen Eingabe wird als Standart auf Verschlüsseln gegangen");
            out.println("Mit 'exit' kann auch alternativ das Programm geschlossen werden.");
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                else if (zeile.equals("exit")) {
                    out.println("Programm beendet");
                    return false;
                } else if (zeile.equals("ent")) {
                    verschluesseln = false;
                }
            } catch (IOException e) {
                out.println(e.getMessage());
            }
            return verschl();
        }
        return false;
    }

    /**
     * Fragt den Text und den Schlüssel ab und führt anschließend die Verschlüsselung
     * oder Entschlüsselung durch.
     *
     * @return true, wenn erneut abgefragt werden soll, false bei Programmende
     */
    private boolean verschl() {
        out.println("Geben Sie den Text und den Schlüssel an: ");
        out.println("Sie können so viel Text eingeben wie Sie wollen. Leerzeichen werden dabei entfernt.");
        out.println("Es werden dabei alle Zeichen, welche nicht Basisbuchstaben des Lateinischen Alphabetes sind, ignoriert.");
        out.println("Der Schlüssel gibt die Verschiebung nach rechts an und wiederholt sich falls über 26");
        out.println("Zuerst bitte den Text: ");
        try {
            String zeile = auslesen();
            if (zeile == null || Thread.currentThread().isInterrupted()) return true;
            if (zeile.equals("exit")) {
                out.println("Programm beendet");
                return false;
            }
            String[] woerter = Objects.requireNonNull(zeile).split(" ");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    out.println("Gebe Sie nun die gewünschte Verschiebung in einer Ganzzahl an.");
                    String verschiebung = auslesen();
                    if (verschiebung == null || Thread.currentThread().isInterrupted()) return true;
                    if (verschiebung.equals("exit")) {
                        out.println("Programm beendet");
                        return false;
                    }
                    int schluessel = Integer.parseInt(verschiebung);
                    if (schluessel < 0) {
                        System.out.println("Bitte geben Sie eine Zahl über 0 an");
                        continue;
                    }
                    out.println("Hier ist Ihr veränderter Text: ");
                    berechnung(!verschluesseln ? schluessel * -1 : schluessel, woerter);
                    return true;
                } catch (IOException | NumberFormatException e) {
                    out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            out.println(e.getMessage());
        }
        return false;
    }


    /**
     *
     * Führt die Caesar-Verschlüsselung bzw. Entschlüsselung anhand des Schlüssels aus.
     *
     * @param schluessel Verschiebewert; positiv zum Verschlüsseln, negativ zum Entschlüsseln
     * @param woerter Array aus Textbestandteilen, die verarbeitet werden sollen
     */
    public void berechnung(int schluessel, String[] woerter) {
        try {
            for (String s : woerter) {
                int[] temp = Aufgabe23.aSCII(s.toLowerCase());
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < temp.length; i++) {
                    if (temp[i] > 122 || temp[i] < 97) {
                        result.append((char) temp[i]);
                        continue;
                    }
                    temp[i] = temp[i] + schluessel;
                    while (temp[i] > 122 || temp[i] < 97) {
                        if (temp[i] > 122) {
                            temp[i] -= 26;
                        } else if (temp[i] < 97) {
                            temp[i] += 26;
                        }
                    }
                    result.append((char) temp[i]);
                }
                out.print(result);
            }
            out.println();
        } catch (NumberFormatException e) {
            out.println(e.getMessage());
        }
    }
}
