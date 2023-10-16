package org.ssc.model.variable;

public interface Variable<T> {
    String name = null;

    public String getName() ;

    public void setName(String name) ;

    public T getValue();
    public void setValue(Object value);
    public String getPrint();
    public Class<?> getType();
}
