package org.ssc.model.variable;

import org.ssc.model.Block;

public class ChangeVariable extends Block {
    private String name;

    public ChangeVariable() {
        this.blockName = "ChangeVariable";
        this.name = null;
        this.addConnection(null);
    }

    public ChangeVariable(String name) {
        this();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
