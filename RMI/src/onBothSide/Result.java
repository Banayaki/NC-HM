package onBothSide;

import java.io.Serializable;

public class Result implements Serializable {
    private String result;

    public Result(AbstractTask task) {
        this.result = String.valueOf(task.getOperand1()) + " " +
                String.valueOf(task.getOperation()) + " " +
                String.valueOf(task.getOperand2()) + " = " +
                String.valueOf(task.calculate());
    }

    public String getResult() {
        return result;
    }
}
