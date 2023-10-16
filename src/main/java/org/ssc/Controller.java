package org.ssc;

import org.ssc.model.Block;
import org.ssc.model.variable.ChangeVariable;
import org.ssc.model.variable.PrintVariable;
import org.ssc.model.variable.SetVariable;
import org.ssc.model.variable.Variable;
import org.ssc.model.variable.type.VArray;
import org.ssc.model.variable.type.VChar;
import org.ssc.model.variable.type.VFloat;
import org.ssc.model.variable.type.VInt;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Controller {
    private static HashMap<String, Variable<?>> variables;

    public static void main(String[] args) {
        variables = new HashMap<>();
        //new View();
        Block start = new Block();
        Block current = start;

        VInt amount1 = new VInt();
        amount1.setValue(10);
        VInt amount2 = new VInt();
        amount2.setValue(20);
        VInt amount3 = new VInt();
        amount3.setValue(20);

        current.setNext(new SetVariable("a"));
        current=current.getNext();
        current.addConnection(amount1);

        current.setNext(new PrintVariable("a"));
        current=current.getNext();

        current.setNext(new SetVariable("a"));
        current=current.getNext();
        current.addConnection(amount2);

        current.setNext(new PrintVariable("a"));
        current=current.getNext();

        current.setNext(new ChangeVariable("a"));
        current=current.getNext();
        current.addConnection(amount3);

        current.setNext(new PrintVariable("a"));
        current=current.getNext();

        //----------------------------------------------------

        VFloat floatAmount1 = new VFloat();
        floatAmount1.setValue(1.234f);
        VFloat floatAmount2 = new VFloat();
        floatAmount2.setValue(5.678);

        current.setNext(new SetVariable("b"));
        current=current.getNext();
        current.addConnection(floatAmount1);

        current.setNext(new PrintVariable("b"));
        current=current.getNext();

        current.setNext(new ChangeVariable("b"));
        current=current.getNext();
        current.addConnection(floatAmount2);

        current.setNext(new PrintVariable("b"));
        current=current.getNext();

        //----------------------------------------------------

        VChar c = new VChar();
        c.setValue('c');
        VInt charAmount = new VInt();
        charAmount.setValue(1);

        current.setNext(new SetVariable("c"));
        current=current.getNext();
        current.addConnection(c);

        current.setNext(new PrintVariable("c"));
        current=current.getNext();

        current.setNext(new ChangeVariable("c"));
        current=current.getNext();
        current.addConnection(charAmount);

        current.setNext(new PrintVariable("c"));
        current=current.getNext();

        //----------------------------------------------------

        VArray<VChar> string = new VArray<>(true);
        string.setValue(new ArrayList<VChar>());
        string.getValue().add(c);
        string.getValue().add(c);
        string.getValue().add(c);

        current.setNext(new SetVariable("d"));
        current=current.getNext();
        current.addConnection(string);

        current.setNext(new PrintVariable("d"));
        current=current.getNext();

        //----------------------------------------------------

        VArray<VInt> intArray = new VArray<>();
        intArray.setValue(new ArrayList<VInt>());
        intArray.getValue().add(amount1);
        intArray.getValue().add(amount2);
        intArray.getValue().add(amount3);

        current.setNext(new SetVariable("e"));
        current=current.getNext();
        current.addConnection(intArray);

        current.setNext(new PrintVariable("e"));
        current=current.getNext();


        run(start.getNext());

    }

    private static void run(Block start){
        Block current = start;
        while(current != null){
            switch (current.getClass().getSimpleName()) {
                case "SetVariable": {
                    SetVariable block = (SetVariable) current;
                    String name = block.getName();
                    Variable<?> value =((Variable<?>) (block.getConnection(0)));
                    if(!variables.containsKey(name)) variables.put(name,value);
                    else variables.get(name).setValue(value.getValue());
                    break;
                }
                case "PrintVariable": {
                    PrintVariable block = (PrintVariable) current;
                    String name = block.getName();
                    System.out.println(variables.get(name).getPrint());
                    break;
                }
                case "ChangeVariable": {
                    ChangeVariable block = (ChangeVariable) current;
                    String name = block.getName();
                    Variable<?> value =((Variable<?>) (block.getConnection(0)));
                    if(variables.containsKey(name)) variables.get(name).changeValue(value.getValue());
                    break;
                }
            }
            current= current.getNext();
        }
    }
}
