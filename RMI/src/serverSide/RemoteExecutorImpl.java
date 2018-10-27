package serverSide;

import onBothSide.AbstractTask;
import onBothSide.RemoteExecutor;
import onBothSide.Result;

import java.rmi.RemoteException;

@SuppressWarnings("RedundantThrows")
public class RemoteExecutorImpl implements RemoteExecutor {
    @Override
    public Result execute(AbstractTask task) throws RemoteException {
        return new TaskResult(task);
    }
}
