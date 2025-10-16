package de.nscr.gui;

import de.nscr.blatt1.Aufgabe01;
import de.nscr.blatt1.Aufgabe02;
import de.nscr.blatt1.Aufgabe03;
import de.nscr.blatt1.Aufgabe04;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *
 */
public class AufgabenGUI {
    private JFrame fenster;
    public static JTextArea konsolenTestAusgabe;
    private JTextField konsolenTestEingabe;
    public GUI gui;
    public SchlangenEingabe eingabe;

    /**
     *
     * @param gui Der Frame, der übergeben wird
     * @param testat Das ausgewählte Testat wird übergeben
     * @param aufgabe Die ausgewählte Aufgabe wird übergeben
     */
    public AufgabenGUI(GUI gui, int testat, int aufgabe) {
        this.gui = gui;
        this.eingabe = gui.eingabe;
        setup();

        SwingUtilities.invokeLater(() -> new Thread(() -> {
            if (testat == 1) {
                switch (aufgabe) {
                    case 1 -> new Aufgabe01(this, eingabe);
                    case 2 -> new Aufgabe02(this, eingabe);
                    case 3 -> new Aufgabe03(this, eingabe);
                    case 4 -> new Aufgabe04(this, eingabe);
                }
            }
        }).start());
    }

    /**
     *
     */
    public void setup() {
        fenster = new JFrame("Aufgabe");
        fenster.setUndecorated(true);
        fenster.setResizable(false);
        fenster.setLayout(new BorderLayout());

        fenster.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        fenster.addWindowListener(new WindowAdapter() {
            /**
             * 
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                gui.togglevisible();
                fenster.dispose();
            }
        });

        fenster.setSize(800, 400);
        fenster.setLocationRelativeTo(null);

        JPanel konsolenContainer = new JPanel();
        konsolenTestAusgabe = new JTextArea(8,10);
        konsolenTestEingabe = new JTextField();
        konsolenTestAusgabe.setEditable(false);

        PrintStream konsolenOutput = new PrintStream(new OutputStream() {
            /**
             *
             * @param b   the {@code byte}.
             */
            @Override
            public void write(int b) {
                konsolenTestAusgabe.append(String.valueOf((char) b));
                konsolenTestAusgabe.setCaretPosition(konsolenTestAusgabe.getDocument().getLength()); // Auto-scroll
            }

            /**
             *
             * @param b     the data.
             * @param off   the start offset in the data.
             * @param len   the number of bytes to write.
             */
            @Override
            public void write(byte[] b, int off, int len) {
                konsolenTestAusgabe.append(new String(b, off, len, StandardCharsets.UTF_8));
                konsolenTestAusgabe.setCaretPosition(konsolenTestAusgabe.getDocument().getLength());
            }
        }, true, StandardCharsets.UTF_8); // Auto-flush, UTF-8
        System.setOut(konsolenOutput);

        konsolenContainer.setLayout(new BorderLayout());
        konsolenContainer.add(new JScrollPane(konsolenTestAusgabe), BorderLayout.CENTER);
        konsolenContainer.add(konsolenTestEingabe, BorderLayout.SOUTH);

        konsolenTestEingabe.addActionListener(e -> {
            String command = konsolenTestEingabe.getText().trim();
            if (command.isEmpty()) {
                konsolenTestEingabe.setText("");
                return;
            }

            konsolenTestAusgabe.append(">> " + command + "\n");
            inputAusfueren(command);

            if (!command.equalsIgnoreCase("exit") && !command.equalsIgnoreCase("help")) {
                if (eingabe == null) {
                    return;
                }
                String inputToAdd = command + "\n";
                eingabe.inputEinfuegen(inputToAdd);
            }

            konsolenTestEingabe.setText("");
        });
        konsolenContainer.setSize(600,400);
        konsolenTestAusgabe.setBackground(Color.BLACK);
        konsolenTestAusgabe.setForeground(Color.WHITE);
        konsolenTestEingabe.setBackground(Color.BLACK);
        konsolenTestEingabe.setForeground(Color.WHITE);
        konsolenContainer.setVisible(true);

        fenster.add(konsolenContainer, BorderLayout.CENTER);

        fenster.setVisible(true);
        fenster.repaint();
        fenster.revalidate();
    }

    /**
     *
     * @param eingabe Die Konsoleneingabe, welche möglicherweise ausgeführt wird
     */
    private void inputAusfueren(String eingabe){
        if(eingabe.equalsIgnoreCase("exit")){
            gui.togglevisible();
            fenster.dispose();
        }  else if(eingabe.equalsIgnoreCase("help")){ //console commands
            konsolenTestAusgabe.append("""
                    \
                    'exit' -> closes the Task
                    'help' -> displays this information
                    """);
        }
    }

    /**
     *
     */
    public void exit() {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setIn(new FileInputStream(FileDescriptor.in));
        fenster.dispose();
    }
}
