package onBothSide;

import java.io.Serializable;

public abstract class AbstractTask implements Serializable {
    private double operand1;
    private double operand2;
    private String operation;

    protected AbstractTask() {
        this(0, 0, "");
    }

    protected AbstractTask(double operand1, double operand2, String operation) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
    }

    public abstract double calculate();

    public double getOperand1() {
        return operand1;
    }

    public double getOperand2() {
        return operand2;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return operand1 + operation + operand2;
    }
}
