package de.nscr.gui;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GUI {
    private JFrame fenster;
    private JPanel aufgaben;
    private JButton[] aufgabenknoepfe;
    private int testat;
    private int[] aufgabenPerTestat;
    private final String[] optionen = {"Testat 1", "Testat 2", "Testat 3", "Wähle das Testat", "Exit"};
    private final JComboBox<String> dropdownbox = new JComboBox<>(optionen);
    public SchlangenEingabe eingabe = new SchlangenEingabe();

    /**
     *
     */
    public GUI() {
        setup();
    }

    /**
     *
     */
    public void setup() {
        aufgabenPerTestat = new int[]{4, 0, 0, 0};

        // Calculate total bottom buttons
        int totalAufgaben = 0;
        for (int anzahl : aufgabenPerTestat) {
            totalAufgaben += anzahl;
        }

        fenster = new JFrame("GUI");
        fenster.setUndecorated(true);
        fenster.setResizable(false);
        fenster.setLayout(new BorderLayout());
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setSize(800, 400); // Adjust width if more buttons (e.g., 1000 for 6+ buttons)
        fenster.setLocationRelativeTo(null);

        dropdownbox.setSelectedIndex(3); // Set default selection
        // Optional: Add an action listener for when an item is selected
        dropdownbox.addActionListener(e -> {
            String ausgewaehlt = (String) dropdownbox.getSelectedItem();
            assert ausgewaehlt != null;
            if (ausgewaehlt.equals("Exit")) {
                System.exit(0);
            }
            testat = dropdownbox.getSelectedIndex() + 1; // Set selected testat (1-4)
            aufgaben.setVisible(true); // Show bottom panel
            aufgabenAnzeigen(); // Populate with variable number of bottom buttons (overlay effect)
        });
        // Add the dropdown to the frame
        dropdownbox.setVisible(true);

        // Bottom panel: Variable total buttons, grouped by testat
        aufgabenknoepfe = new JButton[totalAufgaben]; // Dynamic size
        aufgaben = new JPanel(new BorderLayout()); // CHANGED: Use BorderLayout for Back button placement
        aufgaben.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding
        aufgaben.setVisible(false); // Initially hidden and empty

        // Create all bottom buttons with cumulative indexing
        int nummer = 0;
        for (int testat = 1; testat <= 4; testat++) { // For each Testat
            int numberTestat = aufgabenPerTestat[testat - 1];
            for (int aufgabe = 1; aufgabe <= numberTestat; aufgabe++) {
                int knopfnummer = nummer;
                aufgabenknoepfe[knopfnummer] = new JButton("Aufgabe " + testat + "-" + aufgabe);
                aufgabenknoepfe[knopfnummer].setFocusable(false);
                aufgabenknoepfe[knopfnummer].setPreferredSize(new Dimension(180, 30));
                aufgabenknoepfe[knopfnummer].addActionListener(e -> {
                    this.togglevisible();
                    switch (knopfnummer) {
                        case 0 -> new AufgabenGUI(this, 1, 1);
                        case 1 -> new AufgabenGUI(this, 1, 2);
                        case 2 -> new AufgabenGUI(this, 1, 3);
                        case 3 -> new AufgabenGUI(this, 1, 4);
                        default ->  System.exit(0);
                    }
                });
                nummer++;
            }
        }
        testat = dropdownbox.getSelectedIndex() + 1; // Set initial testat
        fenster.add(dropdownbox, BorderLayout.NORTH); // Places it at the top, visible and non-overlapping

        // Add panels to frame
        fenster.add(aufgaben, BorderLayout.CENTER);

        // Make frame visible after setup
        fenster.setVisible(true);
        fenster.repaint();
        fenster.revalidate();
    }

    /**
     *
     */
    private void aufgabenAnzeigen() {
        aufgaben.removeAll(); // Clear previous buttons (overlay: same position)

        // Calculate start index for this testat
        int anfangsIndex = 0;
        for (int t = 1; t < testat; t++) {
            anfangsIndex += aufgabenPerTestat[t - 1];
        }

        int knopfNummer = aufgabenPerTestat[testat - 1]; // Variable number for this testat

        JPanel aufgabenReihe = new JPanel(new GridLayout(1, knopfNummer, 10, 10));
        aufgabenReihe.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Small bottom padding before Back

        // Add only the relevant aufgaben buttons to the row
        for (int i = 0; i < knopfNummer; i++) {
            int buttonIndex = anfangsIndex + i;
            aufgabenReihe.add(aufgabenknoepfe[buttonIndex]);
        }

        // Add to aufgaben panel: Row in CENTER
        aufgaben.add(aufgabenReihe, BorderLayout.CENTER);

        fenster.revalidate(); // Refresh layout
        fenster.repaint(); // Redraw
    }

    /**
     *
     */
    public void togglevisible() {
        fenster.setVisible(!fenster.isVisible());
    }
}