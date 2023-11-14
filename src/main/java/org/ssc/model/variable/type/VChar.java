package org.ssc.model.variable.type;

import org.ssc.model.Block;
import org.ssc.model.TypeMismatchException;
import org.ssc.model.math.InvalidOperation;
import org.ssc.model.variable.Variable;

public class VChar extends Block implements Variable<Character> {
    private Character value;

    public VChar() {
        this.value = ' ';
        this.blockName = "Variable";
    }

    @Override
    public Character getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) throws TypeMismatchException {
        if (value instanceof Character) this.value = (Character) value;
        else if (value instanceof Integer) this.value = (char) (int) (Integer) value;
        else throw new TypeMismatchException();
    }

    @Override
    public void changeValue(Object value) throws TypeMismatchException {
        if (value instanceof Integer) this.value = (char) (this.value + ((Integer) value));
        else throw new TypeMismatchException();
    }

    @Override
    public Variable<Character> add(Object value) throws TypeMismatchException, InvalidOperation {
        if (!(value instanceof VInt)) throw new TypeMismatchException();
        VChar result = new VChar();
        result.value = (char) (this.value + ((VInt) value).getValue());
        return null;
    }

    @Override
    public Variable<Character> sub(Object value) throws TypeMismatchException, InvalidOperation {
        if (!(value instanceof VInt)) throw new TypeMismatchException();
        VChar result = new VChar();
        result.value = (char) (this.value - ((VInt) value).getValue());
        return null;
    }

    @Override
    public Variable<Character> mul(Object value) throws TypeMismatchException, InvalidOperation {
        throw new InvalidOperation();
    }

    @Override
    public Variable<Character> div(Object value) throws TypeMismatchException, InvalidOperation {
        throw new InvalidOperation();
    }

    @Override
    public Variable<Character> mod(Object value) throws TypeMismatchException, InvalidOperation {
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

    @Override
    public Variable<Character> cloneVariable() {
        VChar clone = new VChar();
        clone.value = this.value;
        return clone;
    }

    @Override
    public boolean setName(String name) {
        try {
            if (name.length() != 1) {
                System.out.println("\u001B[31m" + name + " of length " + name.length() + " not char" + "\u001B[0m");
                return false;
            } else {
                value = name.charAt(0);
                return true;
            }
        } catch (Exception e) {
            System.out.println("\u001B[31m" + name + " not char" + "\u001B[0m");
            return false;
        }
    }
}
