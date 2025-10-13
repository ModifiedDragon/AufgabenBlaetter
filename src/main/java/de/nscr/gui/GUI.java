package de.nscr.gui;

import de.nscr.blatt1.Aufgabe01;
import de.nscr.blatt1.Aufgabe02;
import de.nscr.blatt1.Aufgabe03;
import de.nscr.blatt1.Aufgabe04;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

/**
 *
 */
public class GUI {
    /// TODO rename everything
    /// TODO remove comments
    private JFrame frame;
    private JPanel aufgaben;
    private JButton[] aufgabenb;
    private int testat;
    private int[] aufgabenPerTestat; // Configurable number per Testat (index 0 = Testat 1)
    private int totalAufgaben; // Total bottom buttons (sum of aufgabenPerTestat)
    private String[] options = {"Testat 1", "Testat 2", "Testat 3", "Wähle das Testat", "Exit"};
    private JComboBox<String> dropdown = new JComboBox<>(options);
    public QueueInputStream qin = new QueueInputStream();

    public GUI() {
        setup();
    }

    public void setup() {
        aufgabenPerTestat = new int[]{4, 0, 0, 0};

        // Calculate total bottom buttons
        totalAufgaben = 0;
        for (int count : aufgabenPerTestat) {
            totalAufgaben += count;
        }

        frame = new JFrame("GUI");
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400); // Adjust width if more buttons (e.g., 1000 for 6+ buttons)
        frame.setLocationRelativeTo(null);

        dropdown.setSelectedIndex(3); // Set default selection
        // Optional: Add an action listener for when an item is selected
        dropdown.addActionListener(e -> {
            String selected = (String) dropdown.getSelectedItem();
            if (selected.equals("Exit")) {
                System.exit(0);
            }
            testat = dropdown.getSelectedIndex() + 1; // Set selected testat (1-4)
            aufgaben.setVisible(true); // Show bottom panel
            populateAufgaben(); // Populate with variable number of bottom buttons (overlay effect)
        });
        // Add the dropdown to the frame
        dropdown.setVisible(true);

        // Bottom panel: Variable total buttons, grouped by testat
        aufgabenb = new JButton[totalAufgaben]; // Dynamic size
        aufgaben = new JPanel(new BorderLayout()); // CHANGED: Use BorderLayout for Back button placement
        aufgaben.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding
        aufgaben.setVisible(false); // Initially hidden and empty

        // Create all bottom buttons with cumulative indexing
        int currentIndex = 0;
        for (int t = 1; t <= 4; t++) { // For each Testat
            int numForThisTestat = aufgabenPerTestat[t - 1];
            for (int sub = 1; sub <= numForThisTestat; sub++) {
                int buttonIndex = currentIndex;
                aufgabenb[buttonIndex] = new JButton("Aufgabe " + t + "-" + sub);
                aufgabenb[buttonIndex].setFocusable(false);
                // Reduced height to 30px for aufgaben buttons (shorter)
                aufgabenb[buttonIndex].setPreferredSize(new Dimension(180, 30));
                aufgabenb[buttonIndex].addActionListener(e -> {
                    this.togglevisible();
                    switch (buttonIndex) {
                        case 0 -> new AufgabenGUI(this, 1, 1);
                        case 1 -> new AufgabenGUI(this, 1, 2);
                        case 2 -> new AufgabenGUI(this, 1, 3);
                        case 3 -> new AufgabenGUI(this, 1, 4);
                        default ->  System.exit(0);
                    }
                });
                currentIndex++;
            }
        }
        // Trigger the initial/default selection logic (so it shows on startup)
        testat = dropdown.getSelectedIndex() + 1; // Set initial testat

        frame.add(dropdown, BorderLayout.NORTH); // Places it at the top, visible and non-overlapping

        // Add panels to frame
        frame.add(aufgaben, BorderLayout.CENTER);

        // Make frame visible after setup
        frame.setVisible(true);
        frame.repaint();
        frame.revalidate();
    }

    private void populateAufgaben() {
        aufgaben.removeAll(); // Clear previous buttons (overlay: same position)

        // Calculate start index for this testat
        int startIndex = 0;
        for (int t = 1; t < testat; t++) {
            startIndex += aufgabenPerTestat[t - 1];
        }

        int numButtons = aufgabenPerTestat[testat - 1]; // Variable number for this testat

        // NEW: Create sub-panel for aufgaben buttons (horizontal row)
        JPanel aufgabenRow = new JPanel(new GridLayout(1, numButtons, 10, 10));
        aufgabenRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Small bottom padding before Back

        // Add only the relevant aufgaben buttons to the row
        for (int i = 0; i < numButtons; i++) {
            int buttonIndex = startIndex + i;
            aufgabenRow.add(aufgabenb[buttonIndex]);
        }

        // Add to aufgaben panel: Row in CENTER
        aufgaben.add(aufgabenRow, BorderLayout.CENTER);

        frame.revalidate(); // Refresh layout
        frame.repaint(); // Redraw
    }


    public void exit() {
        frame.dispose();
    }

    public void togglevisible() {
        frame.setVisible(!frame.isVisible());
    }
}