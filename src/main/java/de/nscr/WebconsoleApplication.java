package de.nscr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebconsoleApplication {
    ///  TODO jede neue Zeile in Konsole
    ///  TODO exit usw fixen mit nur programm schließen usw.
    ///  TODO Angabe am Anfang mit Help befehl
    ///  TODO beenden der Aufgabe mit exit
    ///  TODO Testate Icons ändern
    ///  TODO Buttons verbinden mit Konsolen Link
    ///  TODO Konsolen für jede aufgabe als eigene Instanz?
    ///  TODO Erklärungen der Aufgaben mit auf den Seiten einfügen

    // Extra Shit
    ///  TODO Links zu anderen Projekten oder so
    ///  TODO Einstellungen einfügen mit änderungen
    ///  TODO Dev-Mode Passwort einfügen für DEBUG ausgaben und extra befehle?
    ///  TODO Visuelle Darstellung der geforderten Aufgabe unter Konsole einfügen mit Erklärung

    // Super-Extra Shit
    ///  TODO Message Konsole mit jedem User als ID oder Username
    ///  TODO User Logins mit Namen und Passwort?
    ///  TODO User Liste, welche alle registrierten Usernames anzeigt

    // Why do you want this - Shit
    ///  TODO 2FA?
    ///  TODO Usage Analytics
    public static void main(String[] args) {
        SpringApplication.run(WebconsoleApplication.class, args);
    }
}
