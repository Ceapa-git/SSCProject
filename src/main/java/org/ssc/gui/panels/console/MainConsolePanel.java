package org.ssc.gui.panels.console;

import javax.swing.*;
import java.awt.*;

public class MainConsolePanel extends JPanel {
    private final JTextArea textArea;
    private String text;

    public MainConsolePanel() {
        setBackground(Color.DARK_GRAY);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        ConsoleMenuPanel consoleMenuPanel = new ConsoleMenuPanel();
        consoleMenuPanel.addClearActionListener(e -> clear());
        add(consoleMenuPanel);

        layout.putConstraint(SpringLayout.NORTH, consoleMenuPanel, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, consoleMenuPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, consoleMenuPanel, consoleMenuPanel.getPreferredSize().width, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, consoleMenuPanel, consoleMenuPanel.getPreferredSize().height, SpringLayout.NORTH, this);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.SOUTH, consoleMenuPanel);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);

        setPreferredSize(new Dimension(0, 170));
    }

    private void clear() {
        textArea.setText("");
    }

    public void addTextNL(String text) {
        addText(text + "\n");
    }

    public void addText(String text) {
        textArea.setText(textArea.getText() + text);
    }
}
