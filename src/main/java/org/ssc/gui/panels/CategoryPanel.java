package org.ssc.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class CategoryPanel extends JPanel {
    private final JPanel innerPanel;
    private final SpringLayout layout;
    private final HashMap<String,JButton> buttons;
    public CategoryPanel(){
        innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(0,1));
        layout = new SpringLayout();
        setLayout(layout);
        add(innerPanel);
        setBackground(Color.DARK_GRAY);

        buttons = new HashMap<>();

        layout.putConstraint(SpringLayout.NORTH,innerPanel,0,SpringLayout.NORTH,this);
        layout.putConstraint(SpringLayout.WEST,innerPanel,0,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.EAST,innerPanel,200,SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.SOUTH,innerPanel,innerPanel.getPreferredSize().height,SpringLayout.NORTH,this);

        setPreferredSize(new Dimension(200,0));
    }

    protected void addButton(String name, ActionListener listener){
        JButton button = new JButton(name);
        button.addActionListener(listener);
        buttons.put(name,button);
        innerPanel.add(button);
        layout.putConstraint(SpringLayout.SOUTH,innerPanel,innerPanel.getPreferredSize().height,SpringLayout.NORTH,this);
        revalidate();
        repaint();
    }

    protected JButton getButton(String name){
        return buttons.get(name);
    }

    protected void clearButtons(){
        for(String k : buttons.keySet()){
            innerPanel.remove(buttons.get(k));
        }
        buttons.clear();
        layout.putConstraint(SpringLayout.SOUTH,innerPanel,innerPanel.getPreferredSize().height,SpringLayout.NORTH,this);
        revalidate();
        repaint();
    }
}
