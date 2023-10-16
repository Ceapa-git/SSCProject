package org.ssc.model.variable;

import org.ssc.model.Block;

public class PrintVariable extends Block {

    public PrintVariable() {
    }

    public void print() {
        Variable<?> value;
        if(this.getConnection(0) == null){
            System.out.println("null");
            return;
        }
        value = (Variable<?>) this.getConnection(0);
        if(value.getValue()== null ){
            System.out.println("null");
            return;
        }
        System.out.print(value.getPrint());
    }
}
