package org.ssc.gui.panels.menu;

public class OperationPanel extends CategoryPanel {
    public OperationPanel() {
        super();
        addButton("+");
        addButton("-");
        addButton("*");
        addButton("/");
        addButton("%");
    }
}
