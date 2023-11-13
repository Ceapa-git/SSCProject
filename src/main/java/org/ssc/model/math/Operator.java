package org.ssc.model.math;

import org.ssc.model.Block;

public class Operator extends Block {
    private Operation operation;

    public Operator() {
        this.blockName = "Operator";
        this.operation = Operation.UNDEFINED;
        this.addConnection(null);
        this.addConnection(null);
    }

    public Operator(Operation operation) {
        this();
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public enum Operation {ADD, SUB, MUL, DIV, MOD, UNDEFINED}
}
