package org.ssc.model.variable;

import org.ssc.model.Block;

public class SetVariable extends Block {
    private String name;

    public SetVariable() {
        this.blockName = "SetVariable";
        this.name = null;
        this.addConnection(null);
    }

    public SetVariable(String name) {
        this();
        this.name = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
