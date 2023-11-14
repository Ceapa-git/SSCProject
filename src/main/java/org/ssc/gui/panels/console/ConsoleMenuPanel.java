package org.ssc.gui.panels.console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConsoleMenuPanel extends JPanel {
    private final JButton clear;

    public ConsoleMenuPanel() {
        setBackground(Color.WHITE);
        setLayout(new GridLayout(1, 0));
        setFocusable(false);

        clear = new JButton("Clear");
        clear.setFocusable(false);
        add(clear);
    }

    public void addClearActionListener(ActionListener listener) {
        clear.addActionListener(listener);
    }
}
