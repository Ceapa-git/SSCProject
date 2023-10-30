package org.ssc.gui.panels.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    private final JPanel blockCategoryPanel;
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private final VariablePanel variablePanel;
    private final OperationPanel operationPanel;
    private final JButton operation;
    private final JButton variable;
    private final JButton close;
    public MainMenuPanel(){
        setBackground(Color.WHITE);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        blockCategoryPanel = new JPanel();
        blockCategoryPanel.setBackground(Color.blue);
        SpringLayout blockCategoryLayout = new SpringLayout();
        blockCategoryPanel.setLayout(blockCategoryLayout);
        JPanel blockCategoryInnerPanel = new JPanel();
        blockCategoryInnerPanel.setBackground(Color.CYAN);
        blockCategoryInnerPanel.setLayout(new GridLayout(0,1));

        operation = new JButton("Operation");
        blockCategoryInnerPanel.add(operation);
        variable = new JButton("Variable");
        blockCategoryInnerPanel.add(variable);
        close = new JButton("Close");
        blockCategoryInnerPanel.add(close);

        blockCategoryPanel.add(blockCategoryInnerPanel);
        blockCategoryLayout.putConstraint(SpringLayout.NORTH, blockCategoryInnerPanel,0,SpringLayout.NORTH,blockCategoryPanel);
        blockCategoryLayout.putConstraint(SpringLayout.WEST, blockCategoryInnerPanel,0,SpringLayout.WEST,blockCategoryPanel);
        blockCategoryLayout.putConstraint(SpringLayout.EAST, blockCategoryInnerPanel, blockCategoryInnerPanel.getPreferredSize().width,SpringLayout.WEST,blockCategoryPanel);
        blockCategoryLayout.putConstraint(SpringLayout.SOUTH, blockCategoryInnerPanel, blockCategoryInnerPanel.getPreferredSize().height,SpringLayout.NORTH,blockCategoryPanel);
        blockCategoryPanel.setPreferredSize(blockCategoryInnerPanel.getPreferredSize());
        add(blockCategoryPanel);

        layout.putConstraint(SpringLayout.NORTH, blockCategoryPanel,0,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.SOUTH, blockCategoryPanel,0,SpringLayout.SOUTH,this);
        layout.putConstraint(SpringLayout.WEST, blockCategoryPanel,0,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.EAST, blockCategoryPanel, blockCategoryPanel.getPreferredSize().width,SpringLayout.WEST,this);
        setPreferredSize(blockCategoryPanel.getPreferredSize());

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        variablePanel = new VariablePanel();
        cardPanel.add(variablePanel,"variable");
        operationPanel = new OperationPanel();
        cardPanel.add(operationPanel,"operation");

        add(cardPanel);

        layout.putConstraint(SpringLayout.NORTH, cardPanel,0,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.SOUTH, cardPanel,0,SpringLayout.SOUTH,this);
        layout.putConstraint(SpringLayout.WEST, cardPanel,0,SpringLayout.EAST,blockCategoryPanel);
        layout.putConstraint(SpringLayout.EAST, cardPanel, 0,SpringLayout.EAST,this);
    }

    public void addOperationActionListener(ActionListener listener){
        operation.addActionListener(listener);
    }
    public void addVariableActionListener(ActionListener listener){
        variable.addActionListener(listener);
    }
    public void addCloseActionListener(ActionListener listener){
        close.addActionListener(listener);
    }

    public void showOperation(){
        int width = blockCategoryPanel.getPreferredSize().width+cardPanel.getPreferredSize().width;
        cardLayout.show(cardPanel,"operation");
        setPreferredSize(new Dimension(width,getPreferredSize().height));
    }

    public void showVariable(){
        int width = blockCategoryPanel.getPreferredSize().width+cardPanel.getPreferredSize().width;
        cardLayout.show(cardPanel,"variable");
        setPreferredSize(new Dimension(width,getPreferredSize().height));
    }

    public void showClose(){
        int width = blockCategoryPanel.getPreferredSize().width;
        setPreferredSize(new Dimension(width,getPreferredSize().height));
    }

    public VariablePanel getVariablePanel() {
        return variablePanel;
    }

    public OperationPanel getOperationPanel() {
        return operationPanel;
    }
}
