package org.ssc.model.math;

public class ComputeException extends Exception {
    private final String message;

    public ComputeException(String message) {
        super();
        this.message = message;
    }

    public String getMessageString() {
        return message;
    }
}
