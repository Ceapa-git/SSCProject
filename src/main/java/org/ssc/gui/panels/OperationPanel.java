package org.ssc.gui.panels;

import javax.swing.*;
import java.awt.*;

public class OperationPanel extends CategoryPanel {
    public OperationPanel(){
        super();
        addButton("+",null);
        addButton("-",null);
        addButton("*",null);
        addButton("/",null);
        addButton("%",null);
    }
}
