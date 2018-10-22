package server;

import client.Calculate;
import client.Task;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation of a remote object interface
 */
public class CalculateImpl extends UnicastRemoteObject implements Calculate {

    CalculateImpl() throws RemoteException {
    }

    /**
     * @see Calculate#getResult(Task)
     * @param task - task to be solved
     * @return - result of the equation
     * @throws RemoteException
     */
    @Override
    public double getResult(Task task) throws RemoteException{
        double a = task.getA();
        double b = task.getB();
        double x = task.getX();
        return a * x * x + b * x;
    }
}
