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
import java.util.Scanner;

public class AufgabenGUI {
    ///  IDEA: In GUI open Task -> opens AufgabenGUI with new Aufgabe and scanner as Param and keep GUI open but hidden.
    ///  After closing Task it will show the GUI again and everything can start again
    ///  This frame should show a Console Input and a Text output (with possibly buttons for options)
    ///  Acts as kind of daughter-class of the Jframe?
    private JFrame frame;
    private JPanel consoleContainer;
    public static JTextArea consoleTextOutput;
    private JTextField consoleTextInputField;
    public GUI window;
    private int testat;
    private int task;
    public QueueInputStream qin;

    public AufgabenGUI(GUI window, int testat, int task) {
        this.window = window;
        this.testat = testat;
        this.task = task;
        this.qin = window.qin;
        setup();

        SwingUtilities.invokeLater(() -> {
            new Thread(() -> {
                if (testat == 1) {
                    switch (task) {
                        case 1 -> new Aufgabe01(this, qin);
                        case 2 -> new Aufgabe02(this, qin);
                        case 3 -> new Aufgabe03(this, qin);
                        case 4 -> new Aufgabe04(this, qin);
                    }
                }
                // For other testats/tasks, add similar switches
            }).start();
        });
    }

    public void setup() {
        frame = new JFrame("Task");
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Just close this window, don't exit app
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                window.togglevisible();  // Show parent GUI when task closes
                frame.dispose();
            }
        });

        frame.setSize(800, 400); // Adjust width if more buttons (e.g., 1000 for 6+ buttons)
        frame.setLocationRelativeTo(null);

        consoleContainer = new JPanel();
        consoleTextOutput = new JTextArea(8,10);
        consoleTextInputField = new JTextField();
        consoleTextOutput.setEditable(false);

        PrintStream consoleOut = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                consoleTextOutput.append(String.valueOf((char) b));
                consoleTextOutput.setCaretPosition(consoleTextOutput.getDocument().getLength()); // Auto-scroll
            }

            @Override
            public void write(byte[] b, int off, int len) {
                consoleTextOutput.append(new String(b, off, len, StandardCharsets.UTF_8));
                consoleTextOutput.setCaretPosition(consoleTextOutput.getDocument().getLength());
            }
        }, true, StandardCharsets.UTF_8); // Auto-flush, UTF-8
        System.setOut(consoleOut);

        consoleContainer.setLayout(new BorderLayout());
        consoleContainer.add(new JScrollPane(consoleTextOutput), BorderLayout.CENTER);
        consoleContainer.add(consoleTextInputField, BorderLayout.SOUTH);
        consoleTextInputField.addActionListener(e -> {
            String command = consoleTextInputField.getText().trim();
            if (command.isEmpty()) {
                consoleTextInputField.setText("");
                return;
            }

            consoleTextOutput.append(">> " + command + "\n");
            executeInput(command);

            if (!command.equalsIgnoreCase("exit") && !command.equalsIgnoreCase("help")) {
                if (qin == null) {
                    return;
                }
                String inputToAdd = command + "\n";

                qin.addInput(inputToAdd);  // Only real input
            }

            consoleTextInputField.setText("");
        });
        consoleContainer.setSize(600,400);
        consoleTextOutput.setBackground(Color.BLACK);
        consoleTextOutput.setForeground(Color.WHITE);
        consoleTextInputField.setBackground(Color.BLACK);
        consoleTextInputField.setForeground(Color.WHITE);
        consoleContainer.setVisible(true);

        frame.add(consoleContainer, BorderLayout.CENTER);

        // Make frame visible after setup
        frame.setVisible(true);
        frame.repaint();
        frame.revalidate();
    }


    private void executeInput(String input){
        if(input.equalsIgnoreCase("exit")){
            consoleTextOutput.append("Program is closing ...\n");
            window.togglevisible();
            frame.dispose();
        }  else if(input.equalsIgnoreCase("help")){ //console commands
            consoleTextOutput.append("""
                    \
                    'exit' -> closes the Task
                    'help' -> displays this information
                    """);
        } else if (input.toLowerCase().startsWith("recreateitem")) {

        }
    }

    public void exit() {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));  // Restore original
        System.setIn(new FileInputStream(FileDescriptor.in));
        frame.dispose();
    }

    public void togglevisible() {
        frame.setVisible(!frame.isVisible());
    }

    public void addText(String text) {
        consoleTextOutput.append(text + "\n");
        consoleTextOutput.setCaretPosition(consoleTextOutput.getDocument().getLength());
    }
}
