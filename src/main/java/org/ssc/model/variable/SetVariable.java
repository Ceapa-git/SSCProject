package org.ssc.model.variable;

import org.ssc.model.Block;

public class SetVariable extends Block {
    private String name;

    public SetVariable() {
        this.name = null;
    }

    public SetVariable(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
