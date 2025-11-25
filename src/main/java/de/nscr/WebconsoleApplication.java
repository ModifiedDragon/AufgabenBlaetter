package de.nscr;

import de.nscr.blatt3.Aufgabe21;
import de.nscr.blatt3.Aufgabe22;
import de.nscr.blatt3.Aufgabe23;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hauptklasse der Webconsole-Anwendung.
 * <p>
 * Diese Spring Boot Anwendung dient als Einstiegspunkt für die Aufgaben
 * (z. B. {@link Aufgabe21}, {@link Aufgabe22}, {@link Aufgabe23}), die über
 * die HTML-Konsole ausgeführt werden. Sie startet den eingebetteten
 * Spring-Kontext und stellt die Infrastruktur bereit, sodass die einzelnen
 * Aufgaben-Klassen ihre Eingabe- und Ausgabeströme nutzen können.
 * </p>
 *
 * <h3>Startmöglichkeiten</h3>
 * <ul>
 *   <li><b>Lokal:</b> Die Anwendung kann über die Kommandozeile gestartet werden,
 *       z. B. mit <code>java -jar webconsole.jar</code>. Danach ist sie unter
 *       <code>http://localhost:8080</code> erreichbar.</li>
 *   <li><b>Online:</b> Alternativ ist die Anwendung als gehostete Website verfügbar
 *       unter <a href="https://aufgabenblaetter.onrender.com">https://aufgabenblaetter.onrender.com</a>.</li>
 * </ul>
 *
 * <p>
 * Ablauf:
 * <ol>
 *   <li>Start der Anwendung über {@link #main(String[])}.</li>
 *   <li>Spring Boot initialisiert die Umgebung.</li>
 *   <li>Die Aufgaben-Klassen werden über die Konsole ausgeführt und
 *       interaktiv gesteuert.</li>
 * </ol>
 * </p>
 */
@SpringBootApplication
public class WebconsoleApplication {
    /**
     * Einstiegspunkt der Anwendung.
     * <p>
     * Startet den Spring Boot Kontext und damit die gesamte Webconsole-Anwendung.
     * </p>
     *
     * @param args optionale Kommandozeilenargumente
     */
    public static void main(String[] args) {
        SpringApplication.run(WebconsoleApplication.class, args);
    }
}
