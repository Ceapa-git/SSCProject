package org.ssc;

import org.ssc.model.Block;
import org.ssc.model.variable.ChangeVariable;
import org.ssc.model.variable.PrintVariable;
import org.ssc.model.variable.SetVariable;
import org.ssc.model.variable.Variable;
import org.ssc.model.variable.type.VInt;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;

public class Controller {
    private static HashMap<String, Variable<?>> variables;

    public static void main(String[] args) {
        variables = new HashMap<>();
        //new View();
        Block start = new Block();
        Block current = start;

        VInt a = new VInt();
        a.setName("a");
        VInt amount = new VInt();
        amount.setValue(10);

        current.setNext(new SetVariable(amount));
        current=current.getNext();
        current.addConnection(a);

        current.setNext(new PrintVariable());
        current=current.getNext();
        current.addConnection(a);

        run(start.getNext());

    }

    private static void run(Block start){
        Block current = start;
        while(current != null){
            switch (current.getClass().getSimpleName()) {
                case "SetVariable": {
                    SetVariable block = (SetVariable) current;
                    String name = ((Variable<?>) (block.getConnection(0))).getName();
                    Variable<?> value = block.getValue();
                    if(variables.get(name) == null) variables.put(name,value);
                    else variables.get(name).setValue(value);
                    break;
                }
                case "PrintVariable": {
                    PrintVariable block = (PrintVariable) current;
                    String name = ((Variable<?>) (block.getConnection(0))).getName();
                    System.out.println(variables.get(name).getPrint());
                    break;
                }
                case "ChangeVariable": {
                    ///TODO
                    /*ChangeVariable block = (ChangeVariable) current;
                    String name = ((Variable<?>) (block.getConnection(0))).getName();
                    Variable<?> value = block.getValue();
                    System.out.println(variables.get(name).getPrint());*/
                    break;
                }
            }
            current= current.getNext();
        }
    }
}
