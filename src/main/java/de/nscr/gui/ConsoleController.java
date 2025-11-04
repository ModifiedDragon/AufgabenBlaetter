package de.nscr.gui;

import de.nscr.blatt1.Aufgabe01;
import de.nscr.blatt1.Aufgabe02;
import de.nscr.blatt1.Aufgabe03;
import de.nscr.blatt1.Aufgabe04;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/console")
public class ConsoleController {

    // All active sessions stored here
    private final Map<String, ConsoleSession> sessions = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final ScheduledExecutorService cleanupExecutor = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init() {
        cleanupExecutor.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            sessions.entrySet().removeIf(entry -> now - entry.getValue().getLastActivity() > 30 * 60 * 1000);
        }, 5, 5, TimeUnit.MINUTES);
    }

    // Create a new session and return its ID
    @PostMapping("/create")
    public String createSession() {
        String id = UUID.randomUUID().toString();
        ConsoleSession session = new ConsoleSession();
        sessions.put(id, session);
        return id;
    }

    @PostMapping("/{id}/devmode")
    public String toggleDevMode(@PathVariable String id, @RequestBody String password) {
        ConsoleSession session = sessions.get(id);
        if (session == null) return "Invalid session";

        if ("MySecret123".equals(password)) {
            session.setDevMode(!session.isDevMode());
            return "DevMode " + (session.isDevMode() ? "enabled" : "disabled");
        }
        return "Incorrect password";
    }

    // Send input to a specific session
    @PostMapping("/{id}/input")
    public void input(@PathVariable String id, @RequestBody String command) {
        ConsoleSession session = sessions.get(id);
        if (session != null) {
            session.getEingabe().inputEinfuegen(command + "\n");
        }
        session.setLastActivity(System.currentTimeMillis());
    }

    @PostMapping("/{id}/start/{task}")
    public String startTask(@PathVariable String id, @PathVariable String task) {
        ConsoleSession session = sessions.get(id);
        System.out.println("Starting task: " + task);
        if (session == null) return "Invalid session";
        // Terminate any running task
        if (session.getCurrentTask() != null && !session.getCurrentTask().isDone()) {
            session.getCurrentTask().cancel(true);
            session.setAufgabeAktiv(false);
        }
        session.setAufgabeAktiv(true);
        PrintStream out = session.getPrintStream();
        Future<?> taskFuture = executor.submit(() -> {
            PrintStream originalOut = System.out;
            System.setOut(out);
            try {
                System.out.println("DEBUG: launching task " + task + " for session " + session.getId());
                // Small delay to allow frontend polling to sync
                Thread.sleep(100);  // Optional: Helps with initial output timing
                switch (task) {
                    case "1-1" -> new Aufgabe01(session.getEingabe(), out);
                    case "1-2" -> new Aufgabe02(session.getEingabe(), out);
                    case "1-3" -> new Aufgabe03(session.getEingabe(), out);
                    case "1-4" -> new Aufgabe04(session.getEingabe(), out);
                    // Add cases for 3-1, etc.
                    default -> System.out.println("Unknown task: " + task);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                session.setAufgabeAktiv(false);
            }
        });
        session.setCurrentTask(taskFuture);
        return "Task " + task + " started";
    }

    @GetMapping("/{id}/output")
    public String output(@PathVariable String id) {
        ConsoleSession session = sessions.get(id);
        session.setLastActivity(System.currentTimeMillis());
        if (session != null) {
            String result = session.getOutput().getAndClear();
            session.getPrintStream().flush();  // Ensure output is flushed immediately
            return result;
        }
        return "Invalid session";
    }

    @GetMapping("/{id}/status")
    public String getSessionStatus(@PathVariable String id) {
        return sessions.containsKey(id) ? "active" : "invalid";
    }

    @PostMapping("/{id}/exit")
    public String exitSession(@PathVariable String id) {
        sessions.remove(id);
        return "Session ended";
    }

    // Inner class representing a single console session
    private static class ConsoleSession {
        private final String id = UUID.randomUUID().toString();
        private final SchlangenEingabe eingabe = new SchlangenEingabe();
        private final ConsoleOutput output = new ConsoleOutput();
        private volatile boolean aufgabeAktiv = false;
        private volatile boolean devMode = false;
        private final PrintStream printStream;
        private volatile long lastActivity = System.currentTimeMillis();
        private volatile Future<?> currentTask;

        public ConsoleSession() {
            this.printStream = new PrintStream(output, true, StandardCharsets.UTF_8);
        }

        public Future<?> getCurrentTask() {
            return currentTask;
        }
        public void setCurrentTask(Future<?> currentTask) {
            this.currentTask = currentTask;
        }
        public PrintStream getPrintStream() { return printStream; }
        public String getId() { return id; }
        public SchlangenEingabe getEingabe() { return eingabe; }
        public ConsoleOutput getOutput() { return output; }
        public boolean isAufgabeAktiv() { return aufgabeAktiv; }
        public void setAufgabeAktiv(boolean aktiv) { this.aufgabeAktiv = aktiv; }
        public boolean isDevMode() { return devMode; }
        public void setDevMode(boolean devMode) { this.devMode = devMode; }
        public long getLastActivity() {
            return lastActivity;
        }
        public void setLastActivity(long lastActivity) {
            this.lastActivity = lastActivity;
        }
    }
}