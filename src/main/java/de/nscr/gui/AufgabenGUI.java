package de.nscr.gui;

import javax.swing.*;
import java.awt.*;

public class AufgabenGUI {
    ///  IDEA: In GUI open Task -> opens AufgabenGUI with new Aufgabe and scanner as Param and keep GUI open but hidden.
    ///  After closing Task it will show the GUI again and everything can start again
    ///  This frame should show a Console Input and a Text output (with possibly buttons for options)
    ///  Acts as kind of daughter-class of the Jframe?
    private JFrame frame;
    private JPanel consoleContainer;
    public static JTextArea consoleTextOutput;
    private JTextField consoleTextInputField;
    GUI window;

    public void setup(GUI window, int testat, int task) {
        this.window = window;
        frame = new JFrame("Task");
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400); // Adjust width if more buttons (e.g., 1000 for 6+ buttons)
        frame.setLocationRelativeTo(null);


        consoleContainer = new JPanel();
        consoleTextOutput = new JTextArea(8,10);
        consoleTextInputField = new JTextField();
        consoleTextOutput.setEditable(false);
        consoleContainer.setLayout(new BorderLayout());
        consoleContainer.add(new JScrollPane(consoleTextOutput), BorderLayout.CENTER);
        consoleContainer.add(consoleTextInputField, BorderLayout.SOUTH);
        consoleTextInputField.addActionListener(e -> {
            String command = consoleTextInputField.getText();
            consoleTextOutput.append(">> " + command + "\n");
            executeInput(command);
            consoleTextInputField.setText("");
        });
        consoleContainer.setSize(600,400);
        consoleTextOutput.setBackground(Color.BLACK);
        consoleTextOutput.setForeground(Color.WHITE);
        consoleTextInputField.setBackground(Color.BLACK);
        consoleTextInputField.setForeground(Color.WHITE);
        consoleContainer.setVisible(true);

        frame.add(consoleContainer, BorderLayout.NORTH);

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
}
