package org.ssc.model.variable;

import org.ssc.model.TypeMismatchException;
import org.ssc.model.math.InvalidOperation;

public interface Variable<T> {
    T getValue();

    void setValue(Object value) throws TypeMismatchException;

    void changeValue(Object value) throws TypeMismatchException;

    Variable<T> add(Object value) throws TypeMismatchException, InvalidOperation;

    Variable<T> sub(Object value) throws TypeMismatchException, InvalidOperation;

    Variable<T> mul(Object value) throws TypeMismatchException, InvalidOperation;

    Variable<T> div(Object value) throws TypeMismatchException, InvalidOperation;

    Variable<T> mod(Object value) throws TypeMismatchException, InvalidOperation;

    String getPrint();

    Class<?> getType();

    Variable<T> cloneVariable();
}
