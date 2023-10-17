package org.ssc.model.variable;

import org.ssc.model.Block;

public class PrintVariable extends Block {
    private String name;

    public PrintVariable() {
        this.blockName = "PrintVariable";
        this.name= null;
        this.addConnection(null);
    }

    public PrintVariable(String name) {
        this();
        this.name= name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
