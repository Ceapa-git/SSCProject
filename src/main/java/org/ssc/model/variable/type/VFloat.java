package org.ssc.model.variable.type;

import org.ssc.model.Block;
import org.ssc.model.TypeMismatchException;
import org.ssc.model.math.InvalidOperation;
import org.ssc.model.variable.Variable;

public class VFloat extends Block implements Variable<Float> {
    private Float value;

    public VFloat() {
        this.value = 0.f;
        this.blockName = "Variable";
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) throws TypeMismatchException {
        if (value instanceof Float) this.value = (Float) value;
        else if (value instanceof Double) this.value = ((Double) value).floatValue();
        else if (value instanceof Integer) this.value = ((Integer) value).floatValue();
        else throw new TypeMismatchException();
    }

    @Override
    public void changeValue(Object value) throws TypeMismatchException {
        if (value instanceof Float) this.value += (Float) value;
        else if (value instanceof Double) this.value += ((Double) value).floatValue();
        else if (value instanceof Integer) this.value += ((Integer) value).floatValue();
        else throw new TypeMismatchException();
    }

    @Override
    public Variable<Float> add(Object value) throws TypeMismatchException, InvalidOperation {
        if (!(value instanceof VFloat) && !(value instanceof VInt)) throw new TypeMismatchException();
        VFloat result = new VFloat();
        result.value = this.value;
        if (value instanceof VFloat) result.value += ((VFloat) value).getValue();
        else result.value += ((VInt) value).getValue();
        return result;
    }

    @Override
    public Variable<Float> sub(Object value) throws TypeMismatchException, InvalidOperation {
        if (!(value instanceof VFloat) && !(value instanceof VInt)) throw new TypeMismatchException();
        VFloat result = new VFloat();
        result.value = this.value;
        if (value instanceof VFloat) result.value -= ((VFloat) value).getValue();
        else result.value -= ((VInt) value).getValue();
        return result;
    }

    @Override
    public Variable<Float> mul(Object value) throws TypeMismatchException, InvalidOperation {
        if (!(value instanceof VFloat) && !(value instanceof VInt)) throw new TypeMismatchException();
        VFloat result = new VFloat();
        result.value = this.value;
        if (value instanceof VFloat) result.value *= ((VFloat) value).getValue();
        else result.value *= ((VInt) value).getValue();
        return result;
    }

    @Override
    public Variable<Float> div(Object value) throws TypeMismatchException, InvalidOperation {
        if (!(value instanceof VFloat) && !(value instanceof VInt)) throw new TypeMismatchException();
        VFloat result = new VFloat();
        result.value = this.value;
        if (value instanceof VFloat) result.value /= ((VFloat) value).getValue();
        else result.value /= ((VInt) value).getValue();
        return result;
    }

    @Override
    public Variable<Float> mod(Object value) throws TypeMismatchException, InvalidOperation {
        throw new InvalidOperation();
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
