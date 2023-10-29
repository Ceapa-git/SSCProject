package org.ssc.gui;

import org.ssc.gui.panels.BlockPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
    private final JMenuBar menuBar;
    private final JButton runButton;

    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mediu de Programare Grafic");
        setSize(new Dimension(640, 500));
        setPreferredSize(new Dimension(640, 500));
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.black);

        SpringLayout mainLayout = new SpringLayout();
        mainPanel.setLayout(mainLayout);

        menuBar = new JMenuBar();

        runButton = new JButton("Run");
        runButton.setMnemonic(KeyEvent.VK_R);
        runButton.setFocusable(false);
        menuBar.add(runButton);

        BlockPanel blockPanel = new BlockPanel();
        mainPanel.add(blockPanel);
        mainLayout.putConstraint(SpringLayout.NORTH, blockPanel, 0, SpringLayout.NORTH, mainPanel);
        mainLayout.putConstraint(SpringLayout.SOUTH, blockPanel, 0, SpringLayout.SOUTH, mainPanel);
        mainLayout.putConstraint(SpringLayout.WEST, blockPanel, 0, SpringLayout.WEST, mainPanel);
        mainLayout.putConstraint(SpringLayout.EAST, blockPanel, blockPanel.getPreferredSize().width, SpringLayout.WEST, mainPanel);

        blockPanel.addOperationActionListener(e -> {
            blockPanel.showOperation();
            categoryUpdate(mainLayout, blockPanel, mainPanel);
        });
        blockPanel.addVariableActionListener(e -> {
            blockPanel.showVariable();
            categoryUpdate(mainLayout, blockPanel, mainPanel);
        });
        blockPanel.addCloseActionListener(e -> {
            blockPanel.showClose();
            categoryUpdate(mainLayout, blockPanel, mainPanel);
        });

        setJMenuBar(menuBar);
        add(mainPanel);
        mainPanel.setVisible(true);
        setVisible(true);
    }

    private static void categoryUpdate(SpringLayout mainLayout, BlockPanel blockPanel, JPanel mainPanel) {
        mainLayout.putConstraint(SpringLayout.EAST, blockPanel, blockPanel.getPreferredSize().width, SpringLayout.WEST, mainPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void addRunActionListener(ActionListener listener) {
        runButton.addActionListener(listener);
    }
}
