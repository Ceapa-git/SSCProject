package org.ssc.gui;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private final JPanel viewPanel;
    public View(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Graphical Programing");
        setBackground(Color.BLACK);

        viewPanel = new JPanel();
        viewPanel.setBackground(Color.BLACK);
        viewPanel.setLayout(new FlowLayout());

        add(viewPanel);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}
