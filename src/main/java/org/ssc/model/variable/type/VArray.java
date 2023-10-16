package org.ssc.model.variable.type;

import org.ssc.model.Block;
import org.ssc.model.variable.Variable;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class VArray<T extends Variable<?>> extends Block implements Variable<ArrayList<T>> {
    private ArrayList<T> value;
    private String name;

    public VArray() {
        this.value = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public ArrayList<T> getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if(this.value.getClass().isInstance(value)) this.value = (ArrayList<T>) value;
    }

    @Override
    public String getPrint() {
        if(value.size()==0)
            return "[]";
        StringBuilder msgBuilder = new StringBuilder("[");
        for(Variable<?> element:value) {
            msgBuilder.append(element.getPrint()).append(", ");
        }
        msgBuilder.deleteCharAt(msgBuilder.length()-1);
        return msgBuilder.append("]").toString();
    }

    @Override
    public Class<?> getType() {
        return value.getClass();
    }
}
