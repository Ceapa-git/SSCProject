package org.ssc.model.variable.type;

import org.ssc.model.Block;
import org.ssc.model.variable.Variable;

import java.util.ArrayList;

public class VChar extends Block implements Variable<Character> {
    private Character value;
    private String name;

    public VChar() {
        this.value = ' ';
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public Character getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if(this.value.getClass().isInstance(value)) this.value = (Character) value;
    }

    @Override
    public String getPrint() {
        return value.toString();
    }

    @Override
    public Class<?> getType() {
        return value.getClass();
    }
}
