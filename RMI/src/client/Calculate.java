package client;

import client.Task;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for RMI
 */
public interface Calculate extends Remote {

    /**
     * Get the result of math operation
     *
     * @param task - task to be solved
     * @return - result of the equation
     * @throws RemoteException
     */
    double getResult(Task task) throws RemoteException;
}
