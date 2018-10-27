package clientSide.tasks;

import onBothSide.AbstractTask;

// Subtraction
public class SubTask extends AbstractTask {

    public SubTask(double operand1, double operand2) {
        super(operand1, operand2, "-");
    }

    @Override
    public double calculate() {
        return getOperand1() - getOperand2();
    }
}
