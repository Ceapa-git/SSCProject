package org.ssc.gui;

import org.ssc.gui.panels.canvas.MainCanvasPanel;
import org.ssc.gui.panels.console.MainConsolePanel;
import org.ssc.gui.panels.menu.MainMenuPanel;
import org.ssc.model.Block;
import org.ssc.model.math.Operator;
import org.ssc.model.variable.ChangeVariable;
import org.ssc.model.variable.PrintVariable;
import org.ssc.model.variable.SetVariable;
import org.ssc.model.variable.Variable;
import org.ssc.model.variable.type.VArray;
import org.ssc.model.variable.type.VChar;
import org.ssc.model.variable.type.VFloat;
import org.ssc.model.variable.type.VInt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
    private final JButton runButton;
    private final SpringLayout mainLayout;
    private final JPanel mainPanel;
    private final MainConsolePanel mainConsolePanel;
    private final MainCanvasPanel mainCanvasPanel;

    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mediu de Programare Grafic");
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setPreferredSize(new Dimension(640, 500));
        setSize(new Dimension(640, 500));

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.black);

        mainLayout = new SpringLayout();
        mainPanel.setLayout(mainLayout);

        JMenuBar menuBar = new JMenuBar();

        runButton = new JButton("Run");
        runButton.setMnemonic(KeyEvent.VK_R);
        runButton.setFocusable(false);
        menuBar.add(runButton);

        mainConsolePanel = new MainConsolePanel();
        mainPanel.add(mainConsolePanel);
        mainLayout.putConstraint(SpringLayout.NORTH, mainConsolePanel, -mainConsolePanel.getPreferredSize().height, SpringLayout.SOUTH, mainPanel);
        mainLayout.putConstraint(SpringLayout.SOUTH, mainConsolePanel, 0, SpringLayout.SOUTH, mainPanel);
        mainLayout.putConstraint(SpringLayout.WEST, mainConsolePanel, 0, SpringLayout.WEST, mainPanel);
        mainLayout.putConstraint(SpringLayout.EAST, mainConsolePanel, 0, SpringLayout.EAST, mainPanel);

        MainMenuPanel mainMenuPanel = getMainMenuPanel();
        mainPanel.add(mainMenuPanel);
        mainLayout.putConstraint(SpringLayout.NORTH, mainMenuPanel, 0, SpringLayout.NORTH, mainPanel);
        mainLayout.putConstraint(SpringLayout.SOUTH, mainMenuPanel, 0, SpringLayout.NORTH, mainConsolePanel);
        mainLayout.putConstraint(SpringLayout.WEST, mainMenuPanel, 0, SpringLayout.WEST, mainPanel);
        mainLayout.putConstraint(SpringLayout.EAST, mainMenuPanel, mainMenuPanel.getPreferredSize().width, SpringLayout.WEST, mainPanel);

        mainCanvasPanel = new MainCanvasPanel();
        mainPanel.add(mainCanvasPanel);
        mainLayout.putConstraint(SpringLayout.NORTH, mainCanvasPanel, 0, SpringLayout.NORTH, mainPanel);
        mainLayout.putConstraint(SpringLayout.SOUTH, mainCanvasPanel, 0, SpringLayout.NORTH, mainConsolePanel);
        mainLayout.putConstraint(SpringLayout.WEST, mainCanvasPanel, 0, SpringLayout.EAST, mainMenuPanel);
        mainLayout.putConstraint(SpringLayout.EAST, mainCanvasPanel, 0, SpringLayout.EAST, mainPanel);

        setJMenuBar(menuBar);
        add(mainPanel);
        mainPanel.setVisible(true);
        setVisible(true);
    }

    private MainMenuPanel getMainMenuPanel() {
        MainMenuPanel blockPanel = new MainMenuPanel();
        blockPanel.addOperationActionListener(e -> {
            mainConsolePanel.addTextNL("operation");
            blockPanel.showOperation();
            categoryUpdate(blockPanel);
        });
        blockPanel.getOperationPanel().addButtonActionListener("+",e-> addBlock(new Operator(Operator.Operation.ADD)));
        blockPanel.getOperationPanel().addButtonActionListener("-",e-> addBlock(new Operator(Operator.Operation.SUB)));
        blockPanel.getOperationPanel().addButtonActionListener("*",e-> addBlock(new Operator(Operator.Operation.MUL)));
        blockPanel.getOperationPanel().addButtonActionListener("/",e-> addBlock(new Operator(Operator.Operation.DIV)));
        blockPanel.getOperationPanel().addButtonActionListener("%",e-> addBlock(new Operator(Operator.Operation.MOD)));
        blockPanel.addVariableActionListener(e -> {
            mainConsolePanel.addTextNL("variable");
            blockPanel.showVariable();
            categoryUpdate(blockPanel);
        });
        blockPanel.getVariablePanel().addButtonActionListener("Int",e-> addBlock(new VInt()));
        blockPanel.getVariablePanel().addButtonActionListener("Float",e-> addBlock(new VFloat()));
        blockPanel.getVariablePanel().addButtonActionListener("Char",e-> addBlock(new VChar()));
        blockPanel.getVariablePanel().addButtonActionListener("Array",e-> addBlock(new VArray<>()));
        blockPanel.getVariablePanel().addButtonActionListener("Set",e-> addBlock(new SetVariable()));
        blockPanel.getVariablePanel().addButtonActionListener("Change",e-> addBlock(new ChangeVariable()));
        blockPanel.getVariablePanel().addButtonActionListener("Print",e-> addBlock(new PrintVariable()));
        blockPanel.addCloseActionListener(e -> {
            mainConsolePanel.addTextNL("close");
            blockPanel.showClose();
            categoryUpdate(blockPanel);
        });
        return blockPanel;
    }

    private void categoryUpdate(MainMenuPanel blockPanel) {
        mainLayout.putConstraint(SpringLayout.EAST, blockPanel, blockPanel.getPreferredSize().width, SpringLayout.WEST, mainPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void addRunActionListener(ActionListener listener) {
        runButton.addActionListener(listener);
    }

    private void addBlockRecursive(Block block,int offsetX,int offsetY){
        BlockPanel blockPanel = new BlockPanel(block);
        blockPanel.setPosition(offsetX,offsetY);
        mainCanvasPanel.addBlock(blockPanel);
        for(int i=0;i<block.getNumberOfConnections();i++){
            if(block.getConnection(i) != null)
                addBlockRecursive(block.getConnection(i),offsetX+200,offsetY+i*200);
        }
        if(block.getNext()!=null){
            addBlockRecursive(block.getNext(),offsetX,offsetY+200);
        }
    }
    public void addBlock(Block block) {
        addBlockRecursive(block,0,0);
        mainCanvasPanel.revalidate();
        mainCanvasPanel.repaint();
    }
}
