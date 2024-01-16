package org.ssc;

import org.ssc.gui.MainWindow;
import org.ssc.model.Block;
import org.ssc.model.logic.If;
import org.ssc.model.logic.While;
import org.ssc.model.math.ComputeException;
import org.ssc.model.math.InvalidOperation;
import org.ssc.model.math.Operator;
import org.ssc.model.variable.ChangeVariable;
import org.ssc.model.variable.PrintVariable;
import org.ssc.model.variable.SetVariable;
import org.ssc.model.variable.Variable;
import org.ssc.model.variable.type.*;

import java.util.HashMap;
import java.util.Stack;

public class Controller {
    private enum CondType {
        IF,
        WHILE
    }

    private static class StackEntry {
        public StackEntry(Block block, CondType type) {
            this.block = block;
            this.type = type;
        }

        public Block block;
        public CondType type;
    }

    private static HashMap<String, Variable<?>> variables;
    private static Stack<StackEntry> stack;

    public static void main(String[] args) {
        variables = new HashMap<>();
        stack = new Stack<>();
        MainWindow mainWindow = new MainWindow();
        Block start = new Block();
        mainWindow.addRunActionListener(e -> run(start, mainWindow));
        mainWindow.addBlocks(start);
        mainWindow.addDebugStartTreeActionListener(e -> {
            printTree(mainWindow, start, 0);
        });
    }

    private static void printTree(MainWindow mw, Block s, int indent) {
        if (s == null) return;
        while (s != null && !s.getBlockName().equals("While") && !s.getBlockName().equals("If")) {
            for (int i = 0; i < indent; i++) mw.addText("   ");
            mw.addTextNL(s.getBlockName());
            s = s.getNext();
        }
        if (s != null) {
            for (int i = 0; i < indent; i++) mw.addText("   ");
            mw.addTextNL(s.getBlockName());
            printTree(mw, s.getConnection(0), indent + 1);
            printTree(mw, s.getNext(), indent);
        }
    }

    private static void run(Block start, MainWindow mainWindow) {
        variables.clear();
        Block current = start;
        mainWindow.addTextNL("Running");
        try {
            while (current != null) {
                boolean nextFromCond = false;
                switch (current.getBlockName()) {
                    case "SetVariable": {
                        SetVariable block = (SetVariable) current;
                        String name = block.getName();
                        Variable<?> value = compute(block.getConnection(0));
                        variables.put(name, value.cloneVariable());
                        break;
                    }
                    case "PrintVariable": {
                        PrintVariable block = (PrintVariable) current;
                        String name = block.getName();
                        if (variables.containsKey(name))
                            mainWindow.addTextNL(variables.get(name).getPrint());
                        else {
                            mainWindow.addTextNL("Error");
                            mainWindow.addTextNL(name + " does not exist");
                            return;
                        }
                        break;
                    }
                    case "ChangeVariable": {
                        ChangeVariable block = (ChangeVariable) current;
                        String name = block.getName();
                        Variable<?> value = compute(block.getConnection(0));
                        if (variables.containsKey(name)) {
                            if (variables.get(name).getType().equals(value.getType()))
                                variables.get(name).changeValue(value.getValue());
                            else {
                                mainWindow.addTextNL("Error");
                                mainWindow.addTextNL(name + " is not " + value.getType().getName());
                                return;
                            }
                        } else {
                            mainWindow.addTextNL("Error");
                            mainWindow.addTextNL(name + " does not exist");
                            return;
                        }
                        break;
                    }
                    case "If": {
                        If block = (If) current;
                        Variable<?> value = compute(block.getConnection(1));
                        if (value instanceof VInt vInt) {
                            nextFromCond = true;
                            if (vInt.getValue() != 0) {
                                stack.push(new StackEntry(current, CondType.IF));
                                current = current.getConnection(0);
                            } else {
                                current = current.getNext();
                            }
                        } else {
                            mainWindow.addTextNL("Error");
                            mainWindow.addTextNL("Invalid type for if condition");
                            return;
                        }
                        break;
                    }
                    case "While": {
                        While block = (While) current;
                        Variable<?> value = compute(block.getConnection(1));
                        if (value instanceof VInt vInt) {
                            nextFromCond = true;
                            if (vInt.getValue() != 0) {
                                stack.push(new StackEntry(current, CondType.WHILE));
                                current = current.getConnection(0);
                            } else {
                                current = current.getNext();
                            }
                        } else {
                            mainWindow.addTextNL("Error");
                            mainWindow.addTextNL("Invalid type for while condition");
                            return;
                        }
                        break;
                    }
                }
                if (!nextFromCond)
                    current = current.getNext();
                while(current == null && !stack.isEmpty()){
                    StackEntry entry = stack.pop();
                    if (entry.type == CondType.IF){
                        current = entry.block.getNext();
                    }
                    else {
                        current = entry.block;
                    }
                }
            }
        } catch (ComputeException e) {
            mainWindow.addTextNL("Error");
            mainWindow.addTextNL(e.getMessageString());
            return;
        }catch (InvalidOperation e){
            mainWindow.addTextNL("Error");
            mainWindow.addTextNL("Invalid Operation");
            return;
        } catch (Exception e) {
            mainWindow.addTextNL("Error");
            mainWindow.addTextNL(e.getMessage());
            throw e;
        }
        mainWindow.addTextNL("Finished Successfully");
    }

    private static Variable<?> compute(Block current) throws ComputeException {
        if (current == null) throw new ComputeException("Current null");
        if (current instanceof VName vName) {
            if (variables.containsKey(vName.getName()))
                return variables.get(vName.getName()).cloneVariable();
            else {
                throw new ComputeException(vName.getName() + " not initialized");
            }
        }
        if (current instanceof Variable<?>) return (Variable<?>) current;
        Variable<?> value1, value2;
        Variable<?> result = new VInt();

        if (current.getConnection(0) == null) throw new ComputeException("No connections");
        if (current.getConnection(1) == null) throw new ComputeException("Not enough connections");

        if (!(current.getConnection(0) instanceof Variable<?>)) value1 = compute(current.getConnection(0));
        else if (current.getConnection(0) instanceof VName) value1 = compute(current.getConnection(0));
        else value1 = (Variable<?>) current.getConnection(0);
        if (!(current.getConnection(1) instanceof Variable<?>)) value2 = compute(current.getConnection(1));
        else if (current.getConnection(1) instanceof VName) value2 = compute(current.getConnection(1));
        else value2 = (Variable<?>) current.getConnection(1);

        if (current.getBlockName().equals("Operator")) {
            switch (((Operator) current).getOperation()) {
                case ADD: {
                    result = value1.add(value2);
                    break;
                }
                case SUB: {
                    result = value1.sub(value2);
                    break;
                }
                case MUL: {
                    result = value1.mul(value2);
                    break;
                }
                case DIV: {
                    result = value1.div(value2);
                    break;
                }
                case MOD: {
                    result = value1.mod(value2);
                    break;
                }
                case UNDEFINED: {
                }
            }
        } else {
            throw new ComputeException("Wrong block");
        }
        return result;
    }
}
