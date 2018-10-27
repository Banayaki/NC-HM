package serverSide;

import onBothSide.AbstractTask;
import onBothSide.Result;

public class TaskResult implements Result {
    private String result;

    public TaskResult(AbstractTask task) {
        result = String.valueOf(task.getOperand1()) + " " +
                String.valueOf(task.getOperation()) + " " +
                String.valueOf(task.getOperand2()) + " = " +
                String.valueOf(task.calculate());
    }

    @Override
    public String getResult() {
        return result;
    }
}
