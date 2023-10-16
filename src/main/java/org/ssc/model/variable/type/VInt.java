package org.ssc.model.variable.type;

import org.ssc.model.Block;
import org.ssc.model.TypeMismatchException;
import org.ssc.model.variable.Variable;

public class VInt extends Block implements Variable<Integer> {
    private Integer value;
    private String name;

    public VInt() {
        this.value = 0;
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
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) throws TypeMismatchException {
        if(value instanceof Integer) this.value = (Integer) value;
        else throw new TypeMismatchException();
    }

    @Override
    public void changeValue(Object value) throws TypeMismatchException {
        if(value instanceof Integer) this.value += (Integer) value;
        else throw new TypeMismatchException();
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
