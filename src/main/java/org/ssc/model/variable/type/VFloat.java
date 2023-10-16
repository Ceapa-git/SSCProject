package org.ssc.model.variable.type;

import org.ssc.model.Block;
import org.ssc.model.TypeMismatchException;
import org.ssc.model.variable.Variable;

public class VFloat extends Block implements Variable<Float> {
    private Float value;
    private String name;

    public VFloat() {
        this.value = 0.f;
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
    public Float getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) throws TypeMismatchException {
        if(value instanceof Float) this.value = (Float) value;
        else if(value instanceof Double) this.value = ((Double) value).floatValue();
        else if(value instanceof Integer) this.value = ((Integer) value).floatValue();
        else throw new TypeMismatchException();
    }

    @Override
    public void changeValue(Object value) throws TypeMismatchException {
        if(value instanceof Float) this.value += (Float) value;
        else if(value instanceof Double) this.value += ((Double) value).floatValue();
        else if(value instanceof Integer) this.value += ((Integer) value).floatValue();
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
