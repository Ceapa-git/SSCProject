package org.ssc.model.variable;

import org.ssc.model.Block;

public class SetVariable extends Block {
    private Variable<?> value;

    public SetVariable() {
        this.value = null;
    }

    public SetVariable(Variable<?> value) {
        this.value = value;
    }

    public void setValue(Variable<?> value) {
        this.value = value;
    }

    public Variable<?> getValue() {
        return value;
    }
}
