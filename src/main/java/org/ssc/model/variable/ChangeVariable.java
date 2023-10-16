package org.ssc.model.variable;

import org.ssc.model.Block;

public class ChangeVariable extends Block {
    private String name;

    public ChangeVariable() {
        this.name = null;
    }

    public ChangeVariable(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
