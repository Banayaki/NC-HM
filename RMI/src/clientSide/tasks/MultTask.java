package clientSide.tasks;

import onBothSide.AbstractTask;

public class MultTask extends AbstractTask {

    public MultTask(double operand1, double operand2) {
        super(operand1, operand2, "*");
    }

    @Override
    public double calculate() {
        return getOperand1() * getOperand2();
    }
}
