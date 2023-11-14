package org.ssc.model.variable.type;

import org.ssc.model.Block;
import org.ssc.model.TypeMismatchException;
import org.ssc.model.math.InvalidOperation;
import org.ssc.model.variable.Variable;

import java.util.ArrayList;

public class VArray<T extends Variable<?>> extends Block implements Variable<ArrayList<T>> {
    private final boolean isChar;
    private ArrayList<T> value;

    public VArray() {
        this.isChar = false;
        this.value = new ArrayList<>();
        this.blockName = "Variable";
    }

    public VArray(T charType) {
        if (!(charType instanceof VChar)) {
            throw new RuntimeException("not valid string format");
        }
        this.isChar = true;
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
        if (value.isEmpty()) {
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

    @Override
    public Variable<ArrayList<T>> cloneVariable() {
        VArray<T> clone;
        if (isChar) clone = (VArray<T>) new VArray<>(new VChar());
        else clone = new VArray<>();
        clone.value = new ArrayList<>(this.value);
        return clone;
    }

    @Override
    public boolean setName(String name) {
        try {
            if (isChar) {
                value = new ArrayList<>(name.length());
                for (Character c : name.toCharArray()) {
                    VChar vc = new VChar();
                    vc.setValue(c);
                    value.add((T) vc);
                }
                return true;
            } else if (name.length() >= 2 && name.charAt(0) == '[' && name.charAt(name.length() - 1) == ']') {
                //todo
                return true;
            }
            System.out.println("\u001B[31m" + name + " not array" + "\u001B[0m");
            return false;
        } catch (Exception e) {
            System.out.println("\u001B[31m" + name + " not array" + "\u001B[0m");
            return false;
        }
    }
}
