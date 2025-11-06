package de.nscr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebconsoleApplication {
    // Prob needed
    ///  TODO Testate Icons ändern
    ///  TODO Erklärungen der Aufgaben mit auf den Seiten einfügen
    // DEBUGs entfernen

    // Extra Shit
    ///  TODO Einstellungen einfügen mit änderungen (font, text colours, colours (slide down menu?))
    ///  TODO Dev-Mode Passwort einfügen für DEBUG ausgaben und extra befehle? (Server side auth)
    ///  TODO Visuelle Darstellung der geforderten Aufgabe unter Konsole einfügen mit Erklärung

    // Super-Extra Shit
    ///  TODO Message Konsole mit jedem User als ID oder Username
    ///  TODO User Logins mit Namen und Passwort?
    ///  TODO User Liste, welche alle registrierten Usernames anzeigt
    ///  TODO Links zu anderen Projekten oder so

    // Why do you want this - Shit
    ///  TODO 2FA?
    ///  TODO Usage Analytics

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(WebconsoleApplication.class, args);
    }
}
