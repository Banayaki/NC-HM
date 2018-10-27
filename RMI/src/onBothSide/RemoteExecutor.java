package onBothSide;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteExecutor extends Remote, Serializable {
    Result execute(AbstractTask task) throws RemoteException;
}
