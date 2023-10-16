package org.ssc.model.variable;

import org.ssc.model.TypeMismatchException;

public interface Variable<T> {
    String name = null;

    public String getName() ;

    public void setName(String name) ;

    public T getValue();
    public void setValue(Object value) throws TypeMismatchException;
    public void changeValue(Object value) throws TypeMismatchException;
    public String getPrint();
    public Class<?> getType();
}
