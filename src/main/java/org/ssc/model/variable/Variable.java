package org.ssc.model.variable;

import org.ssc.model.TypeMismatchException;
import org.ssc.model.math.InvalidOperation;

public interface Variable<T> {
    public T getValue();
    public void setValue(Object value) throws TypeMismatchException;
    public void changeValue(Object value) throws TypeMismatchException;
    public Variable<T> add(Object value) throws TypeMismatchException, InvalidOperation;
    public Variable<T> sub(Object value) throws TypeMismatchException, InvalidOperation;
    public Variable<T> mul(Object value) throws TypeMismatchException, InvalidOperation;
    public Variable<T> div(Object value) throws TypeMismatchException, InvalidOperation;
    public Variable<T> mod(Object value) throws TypeMismatchException, InvalidOperation;
    public String getPrint();
    public Class<?> getType();
}
