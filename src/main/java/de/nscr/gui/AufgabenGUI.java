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
    ///  IDEA: In GUI open Task -> opens AufgabenGUI with new Aufgabe and scanner as Param and keep GUI open but hidden.
    ///  After closing Task it will show the GUI again and everything can start again
    ///  This frame should show a Console Input and a Text output (with possibly buttons for options)
    ///  Acts as kind of daughter-class of the Jframe?
    private JFrame frame;
    private JPanel consoleContainer;
    public static JTextArea consoleTextOutput;
    private JTextField consoleTextInputField;
    public GUI window;
    public QueueInputStream qin;

    /**
     *
     * @param window
     * @param testat
     * @param task
     */
    public AufgabenGUI(GUI window, int testat, int task) {
        this.window = window;
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
            }).start();
        });
    }

    /**
     *
     */
    public void setup() {
        frame = new JFrame("Task");
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                window.togglevisible();
                frame.dispose();
            }
        });

        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        consoleContainer = new JPanel();
        consoleTextOutput = new JTextArea(8,10);
        consoleTextInputField = new JTextField();
        consoleTextOutput.setEditable(false);

        PrintStream consoleOut = new PrintStream(new OutputStream() {
            /**
             *
             * @param b   the {@code byte}.
             */
            @Override
            public void write(int b) {
                consoleTextOutput.append(String.valueOf((char) b));
                consoleTextOutput.setCaretPosition(consoleTextOutput.getDocument().getLength()); // Auto-scroll
            }

            /**
             *
             * @param b     the data.
             * @param off   the start offset in the data.
             * @param len   the number of bytes to write.
             */
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

                qin.addInput(inputToAdd);
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

        frame.setVisible(true);
        frame.repaint();
        frame.revalidate();
    }

    /**
     *
     * @param input
     */
    private void executeInput(String input){
        if(input.equalsIgnoreCase("exit")){
            window.togglevisible();
            frame.dispose();
        }  else if(input.equalsIgnoreCase("help")){ //console commands
            consoleTextOutput.append("""
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
        frame.dispose();
    }
}
