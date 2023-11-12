package org.ssc.model.variable.type;

import org.ssc.model.Block;
import org.ssc.model.TypeMismatchException;
import org.ssc.model.math.InvalidOperation;
import org.ssc.model.variable.Variable;

import java.util.ArrayList;

public class VArray<T extends Variable<?>> extends Block implements Variable<ArrayList<T>> {
    private ArrayList<T> value;
    private final boolean isChar;

    public VArray() {
        this.isChar = false;
        this.value = new ArrayList<>();
        this.blockName = "Variable";
    }

    public VArray(boolean isChar) {
        this.isChar = isChar;
        this.value = new ArrayList<>();
        this.blockName = "Variable";
    }

    @Override
    public ArrayList<T> getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) throws TypeMismatchException {
        if (this.value.getClass().isInstance(value)) this.value = (ArrayList<T>) value;
        else throw new TypeMismatchException();
    }

    @Override
    public void changeValue(Object value) throws TypeMismatchException {
        throw new TypeMismatchException();
    }

    @Override
    public Variable<ArrayList<T>> add(Object value) throws TypeMismatchException, InvalidOperation {
        throw new InvalidOperation();
    }

    @Override
    public Variable<ArrayList<T>> sub(Object value) throws TypeMismatchException, InvalidOperation {
        throw new InvalidOperation();
    }

    @Override
    public Variable<ArrayList<T>> mul(Object value) throws TypeMismatchException, InvalidOperation {
        throw new InvalidOperation();
    }

    @Override
    public Variable<ArrayList<T>> div(Object value) throws TypeMismatchException, InvalidOperation {
        throw new InvalidOperation();
    }

    @Override
    public Variable<ArrayList<T>> mod(Object value) throws TypeMismatchException, InvalidOperation {
        throw new InvalidOperation();
    }

    @Override
    public String getPrint() {
        if (value.size() == 0) {
            if (isChar) return "";
            else return "[]";
        }
        StringBuilder msgBuilder;
        if (isChar) msgBuilder = new StringBuilder();
        else msgBuilder = new StringBuilder("[");

        for (Variable<?> element : value) {
            msgBuilder.append(element.getPrint());
            if (!isChar) msgBuilder.append(", ");
        }
        if (!isChar) msgBuilder.delete(msgBuilder.length() - 2, msgBuilder.length()).append("]");
        return msgBuilder.toString();
    }

    @Override
    public Class<?> getType() {
        return value.getClass();
    }
}
