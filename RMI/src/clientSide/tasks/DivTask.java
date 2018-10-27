package clientSide.tasks;

import onBothSide.AbstractTask;

public class DivTask extends AbstractTask {

    public DivTask(double operand1, double operand2) {
        super(operand1, operand2, "/");
    }

    @Override
    public double calculate() {
        return Double.compare(0, getOperand2()) == 0 ? Double.POSITIVE_INFINITY : getOperand1() / getOperand2();
    }
}
