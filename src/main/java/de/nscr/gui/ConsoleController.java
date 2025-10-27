package de.nscr.gui;

import de.nscr.blatt1.Aufgabe01;
import de.nscr.blatt1.Aufgabe02;
import de.nscr.blatt1.Aufgabe03;
import de.nscr.blatt1.Aufgabe04;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@RestController
public class ConsoleController {
    private final SchlangenEingabe eingabe = new SchlangenEingabe();
    private final ConsoleOutput output = new ConsoleOutput();
    private volatile boolean aufgabeAktiv = false;

    public ConsoleController() {
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    @PostConstruct
    public void init() {
        /// TODO neuen Thread für jeden user mithilfe von UserID ertsllen, damit jede KonsolenInsanz eigen ist
        /// TODO DEBUGs entfernen
        /// TODO Buttons für die Aufgabenauswahl benutzen
        System.out.println("DEBUG: init() aufgerufen, starte Lesethread...");
        new Thread(() -> {
            StringBuilder sb = new StringBuilder();
            try {
                int c;
                while ((c = eingabe.read()) != -1) {
                    if (c == '\n') {
                        String line = sb.toString().trim();
                        sb.setLength(0);

                        if (!aufgabeAktiv) {
                            System.out.println("DEBUG: Controller hat gelesen -> " + line);

                            if (line.equalsIgnoreCase("start 1-1")) {
                                System.out.println("DEBUG: Starte Aufgabe01");
                                aufgabeAktiv = true;

                                // Controller-Loop beenden, damit er nicht mehr liest
                                new Thread(() -> {
                                    new Aufgabe01(eingabe);
                                    // nach Ende wieder ins Menü zurück
                                    aufgabeAktiv = false;
                                    init(); // neuen Menü-Thread starten
                                }).start();
                                break; // Controller-Loop verlassen
                            } else if (line.equalsIgnoreCase("start 1-2")) {
                                System.out.println("DEBUG: Starte Aufgabe02");
                                aufgabeAktiv = true;

                                // Controller-Loop beenden, damit er nicht mehr liest
                                new Thread(() -> {
                                    new Aufgabe02(eingabe);
                                    // nach Ende wieder ins Menü zurück
                                    aufgabeAktiv = false;
                                    init(); // neuen Menü-Thread starten
                                }).start();
                                break; // Controller-Loop verlassen
                            } else if (line.equalsIgnoreCase("start 1-3")) {
                                System.out.println("DEBUG: Starte Aufgabe03");
                                aufgabeAktiv = true;

                                // Controller-Loop beenden, damit er nicht mehr liest
                                new Thread(() -> {
                                    new Aufgabe03(eingabe);
                                    // nach Ende wieder ins Menü zurück
                                    aufgabeAktiv = false;
                                    init(); // neuen Menü-Thread starten
                                }).start();
                                break; // Controller-Loop verlassen
                            } else if (line.equalsIgnoreCase("start 1-4")) {
                                System.out.println("DEBUG: Starte Aufgabe04");
                                aufgabeAktiv = true;

                                // Controller-Loop beenden, damit er nicht mehr liest
                                new Thread(() -> {
                                    new Aufgabe04(eingabe);
                                    // nach Ende wieder ins Menü zurück
                                    aufgabeAktiv = false;
                                    init(); // neuen Menü-Thread starten
                                }).start();
                                break; // Controller-Loop verlassen
                            } else if (line.equalsIgnoreCase("help")) {
                                System.out.println("""
                                        \
                                        'exit' -> schließt die Aufgabe
                                        'help' -> zeigt alle verfügbaren Befehle an
                                        """);
                            } else if (line.equalsIgnoreCase("exit")) {
                                System.out.println("Beende Konsole...");
                                break;
                            } else {
                                System.out.println("Unbekannter Befehl: " + line);
                            }
                        }
                    } else {
                        sb.append((char) c);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("DEBUG: Ende des Programms");
    }


    @PostMapping("/api/input")
    public void input(@RequestBody String command) {
        System.out.println("DEBUG: Eingabe erhalten -> " + command);
        eingabe.inputEinfuegen(command + "\n"); // wichtig: nur \n reicht hier
    }

    @GetMapping("/api/output")
    public String output() {
        return output.getAndClear();
    }
}





