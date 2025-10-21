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
     * Erstellen des GUI mit Buttons und Combobox
     */
    public void setup() {
        aufgabenPerTestat = new int[]{4, 0, 0, 0};

        int totalAufgaben = 0;
        for (int anzahl : aufgabenPerTestat) {
            totalAufgaben += anzahl;
        }

        fenster = new JFrame("GUI");
        fenster.setUndecorated(true);
        fenster.setResizable(false);
        fenster.setLayout(new BorderLayout());
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setSize(800, 400);
        fenster.setLocationRelativeTo(null);

        dropdownbox.setSelectedIndex(3);
        dropdownbox.addActionListener(e -> {
            String ausgewaehlt = (String) dropdownbox.getSelectedItem();
            assert ausgewaehlt != null;
            if (ausgewaehlt.equals("Exit")) {
                System.exit(0);
            }
            testat = dropdownbox.getSelectedIndex() + 1;
            aufgaben.setVisible(true);
            aufgabenAnzeigen();
        });
        dropdownbox.setVisible(true);

        aufgabenknoepfe = new JButton[totalAufgaben];
        aufgaben = new JPanel(new BorderLayout());
        aufgaben.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding
        aufgaben.setVisible(false);

        int nummer = 0;
        for (int testat = 1; testat <= 4; testat++) {
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
        testat = dropdownbox.getSelectedIndex() + 1;
        fenster.add(dropdownbox, BorderLayout.NORTH);

        fenster.add(aufgaben, BorderLayout.CENTER);

        fenster.setVisible(true);
        fenster.repaint();
        fenster.revalidate();
    }

    /**
     * Dafür da, damit die richtigen Aufgaben von dem jeweiligen Testat angezeigt werden und nicht die anderen Aufgaben, da diese sich überschneiden
     */
    private void aufgabenAnzeigen() {
        aufgaben.removeAll();

        int anfangsIndex = 0;
        for (int t = 1; t < testat; t++) {
            anfangsIndex += aufgabenPerTestat[t - 1];
        }

        int knopfNummer = aufgabenPerTestat[testat - 1];

        JPanel aufgabenReihe = new JPanel(new GridLayout(1, knopfNummer, 10, 10));
        aufgabenReihe.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        for (int i = 0; i < knopfNummer; i++) {
            int buttonIndex = anfangsIndex + i;
            aufgabenReihe.add(aufgabenknoepfe[buttonIndex]);
        }

        aufgaben.add(aufgabenReihe, BorderLayout.CENTER);

        fenster.revalidate();
        fenster.repaint();
    }

    /**
     *
     */
    public void togglevisible() {
        fenster.setVisible(!fenster.isVisible());
    }
}