package org.ssc.model.variable.type;

import org.ssc.model.Block;
import org.ssc.model.TypeMismatchException;
import org.ssc.model.math.InvalidOperation;
import org.ssc.model.variable.Variable;

public class VName extends Block implements Variable<String> {
    private String name;

    public VName() {
        this.blockName = "Variable";
        this.name = "x";
    }

    public VName(String name) {
        this();
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean setName(String name) {
        this.name = name;
        return true;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(Object value) throws TypeMismatchException {

    }

    @Override
    public void changeValue(Object value) throws TypeMismatchException {

    }

    @Override
    public Variable<String> add(Object value) throws TypeMismatchException, InvalidOperation {
        return null;
    }

    @Override
    public Variable<String> sub(Object value) throws TypeMismatchException, InvalidOperation {
        return null;
    }

    @Override
    public Variable<String> mul(Object value) throws TypeMismatchException, InvalidOperation {
        return null;
    }

    @Override
    public Variable<String> div(Object value) throws TypeMismatchException, InvalidOperation {
        return null;
    }

    @Override
    public Variable<String> mod(Object value) throws TypeMismatchException, InvalidOperation {
        return null;
    }

    @Override
    public String getPrint() {
        return this.name;
    }

    @Override
    public Class<?> getType() {
        return null;
    }

    @Override
    public Variable<String> cloneVariable() {
        return null;
    }
}
