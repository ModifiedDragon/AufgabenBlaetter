package de.nscr.note;

import de.nscr.gui.SchlangenEingabe;

import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

public class Notes {

    private final SchlangenEingabe eingabe;
    private final PrintStream out;
    private HashMap<String, Note> notes;

    public Notes(SchlangenEingabe eingabe, PrintStream printStream) {
        this.eingabe = eingabe;
        this.out = printStream;
        notes = new HashMap<>();
        boolean weiter = true;
        while (weiter) {
            weiter = anfang();
        }
    }

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


    private boolean anfang() {
        if (Thread.currentThread().isInterrupted()) return false;
        while (!Thread.currentThread().isInterrupted()) {
            out.println("Geben Sie an, was Sie tun wollen:");
            out.println("1. Hinzufügen einer Notiz");
            out.println("2. Auflistung aller Notizen (Titel + Datum)");
            out.println("3. Ansicht einer bestimmten Notiz (alle Daten) ");
            out.println("4. Löschen einer Notiz");
            out.println("5. Optional: Bearbeiten einer Notiz");
            try {
                String zeile = auslesen();
                if (zeile == null || Thread.currentThread().isInterrupted()) return false;
                return switch (zeile) {
                    case "exit" -> {
                        out.println("Programm beendet");
                        yield false;
                    }
                    case "1" -> add();
                    case "2" -> list();
                    case "3" -> show();
                    case "4" -> remove();
                    case "5" -> edit();
                    default -> {
                        out.println("Bitte gebe eine gültige Eingabe an!");
                        yield true;
                    }
                };
            } catch (IOException e) {
                out.println("Fehler beim Lesen: " + e.getMessage());
            }
        }
        return true;
    }

    private boolean add() throws IOException {
        if (Thread.currentThread().isInterrupted()) return false;
        cleanupExpired();
        out.println("Bitte geben Sie den Titel an. Dieser wird als Key verwendet: ");
        String key = Objects.requireNonNull(auslesen()).toLowerCase();
        out.println("Geben Sie nun den Text an: ");
        return visual(key);
    }

    private boolean visual(String key) throws IOException {
        if (Thread.currentThread().isInterrupted()) return false;
        String value = Objects.requireNonNull(auslesen()).toLowerCase();

        LocalDate date = until();
        notes.put(key, new Note(key, value, LocalDate.now(), date));
        out.println("===== NOTIZ =====");
        Note n = notes.get(key);
        if (n == null) {
            out.println("Keine Notiz gefunden.");
            return true;
        }

        out.println("┌──────────────────────────────────────────┐");
        out.println("│ Titel: " + key);
        out.println("│ Erstellt: " + n.geteDate());
        out.println("│ Fällig bis: " + n.getfDate());
        out.println("├──────────────────────────────────────────┤");
        out.println("│ " + n.getNote().replace("\n", "\n│ "));
        out.println("└──────────────────────────────────────────┘");
        return true;
    }

    private void cleanupExpired() {
        LocalDate today = LocalDate.now();
        notes.entrySet().removeIf(entry -> {
            Note n = entry.getValue();
            return n.getfDate().isBefore(today);
        });
    }

    private boolean list() {
        if (Thread.currentThread().isInterrupted()) return false;
        cleanupExpired();
        out.println("===== NOTIZEN =====");
        for (var entry : notes.entrySet()) {
            out.println("┌───────────────────────────────┐");
            out.println("│ Titel: " + entry.getKey());
            out.println("├───────────────────────────────┤");
            out.println("│ " + entry.getValue());
            out.println("└───────────────────────────────┘");
            out.println("┌──────────────────────────────────────────┐");
            out.println("│ Titel: " + entry.getKey());
            out.println("│ Erstellt: " + entry.getValue().geteDate());
            out.println("│ Fällig bis: " + entry.getValue().getfDate());
            out.println("├──────────────────────────────────────────┤");
            out.println("│ " + entry.getValue().getNote().replace("\n", "\n│ "));
            out.println("└──────────────────────────────────────────┘");
        }
        return true;
    }

    private boolean show() throws IOException {
        if (Thread.currentThread().isInterrupted()) return false;
        cleanupExpired();
        out.println("Geben Sie den Titel der Notiz an, welche Sie sehen wollen: ");
        String key = Objects.requireNonNull(auslesen()).toLowerCase();
        return visual(key);
    }

    private boolean remove() throws IOException {
        if (Thread.currentThread().isInterrupted()) return false;
        cleanupExpired();
        out.println("Geben Sie den Titel der Notiz an, welche Sie löschen wollen: ");
        String key = Objects.requireNonNull(auslesen()).toLowerCase();
        out.println("===== NOTIZ =====");
        Note n = notes.get(key);
        if (n == null) {
            out.println("Keine Notiz gefunden.");
            return true;
        }

        out.println("┌──────────────────────────────────────────┐");
        out.println("│ Titel: " + key);
        out.println("│ Erstellt: " + n.geteDate());
        out.println("│ Fällig bis: " + n.getfDate());
        out.println("├──────────────────────────────────────────┤");
        out.println("│ " + n.getNote().replace("\n", "\n│ "));
        out.println("└──────────────────────────────────────────┘");
        notes.remove(key);
        return true;
    }

    private boolean edit() throws IOException {
        if (Thread.currentThread().isInterrupted()) return false;
        cleanupExpired();
        out.println("Geben Sie den Titel der Notiz an, welche Sie ändern wollen: ");
        String key = Objects.requireNonNull(auslesen()).toLowerCase();
        out.println("Geben Sie nun den neuen Text an: ");
        return visual(key);
    }

    private LocalDate until() throws IOException {
        if (Thread.currentThread().isInterrupted()) return null;
        out.println("Geben Sie das Fälligkeitsdatum an: (Format: dd.MM.yyyy)");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String out = auslesen();
        return LocalDate.parse(Objects.requireNonNull(out), fmt);
    }

}
