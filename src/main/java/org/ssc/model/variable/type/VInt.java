package org.ssc.model.variable.type;

import org.ssc.model.Block;
import org.ssc.model.TypeMismatchException;
import org.ssc.model.math.InvalidOperation;
import org.ssc.model.variable.Variable;

public class VInt extends Block implements Variable<Integer> {
    private Integer value;

    public VInt() {
        this.value = 0;
        this.blockName = "Variable";
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) throws TypeMismatchException {
        if (value instanceof Integer) this.value = (Integer) value;
        else throw new TypeMismatchException();
    }

    @Override
    public void changeValue(Object value) throws TypeMismatchException {
        if (value instanceof Integer) this.value += (Integer) value;
        else throw new TypeMismatchException();
    }

    @Override
    public Variable<Integer> add(Object value) throws TypeMismatchException, InvalidOperation {
        if (!(value instanceof VInt)) throw new TypeMismatchException();
        VInt result = new VInt();
        result.value = this.value;
        result.value += ((VInt) value).getValue();
        return result;
    }

    @Override
    public Variable<Integer> sub(Object value) throws TypeMismatchException, InvalidOperation {
        if (!(value instanceof VInt)) throw new TypeMismatchException();
        VInt result = new VInt();
        result.value = this.value;
        result.value -= ((VInt) value).getValue();
        return result;
    }

    @Override
    public Variable<Integer> mul(Object value) throws TypeMismatchException, InvalidOperation {
        if (!(value instanceof VInt)) throw new TypeMismatchException();
        VInt result = new VInt();
        result.value = this.value;
        result.value *= ((VInt) value).getValue();
        return result;
    }

    @Override
    public Variable<Integer> div(Object value) throws TypeMismatchException, InvalidOperation {
        if (!(value instanceof VInt)) throw new TypeMismatchException();
        VInt result = new VInt();
        result.value = this.value;
        result.value /= ((VInt) value).getValue();
        return result;
    }

    @Override
    public Variable<Integer> mod(Object value) throws TypeMismatchException, InvalidOperation {
        if (!(value instanceof VInt)) throw new TypeMismatchException();
        VInt result = new VInt();
        result.value = this.value;
        result.value %= ((VInt) value).getValue();
        return result;
    }

    @Override
    public String getPrint() {
        return value.toString();
    }

    @Override
    public Class<?> getType() {
        return value.getClass();
    }

    @Override
    public void setName(String name) {
        try{
            value = Integer.parseInt(name);
        }
        catch (Exception e){
            System.out.println("\u001B[31m" + name + " not int" + "\u001B[0m");
        }
    }
}
