package clientSide;

import clientSide.tasks.SumTask;
import onBothSide.AbstractTask;
import onBothSide.RemoteExecutor;
import onBothSide.Result;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientApp {
    private static final String SECURITY_POLICY_PATH = "./src/onBothSide/SecurityServer.policy";
    private static final String REGISTRY_HOST = "127.0.1.1";
    private static final int REGISTRY_PORT = 1099;
    private static final String EXECUTOR_NAME = "Calculator";

    public static void main(String[] args) {
        System.setProperty("java.security.policy", SECURITY_POLICY_PATH);
        System.setProperty("java.rmi.server.hostname", REGISTRY_HOST);
        System.setSecurityManager(new SecurityManager());
        Registry registry;
        RemoteExecutor executor;
        try {
            registry = LocateRegistry.getRegistry(REGISTRY_HOST, REGISTRY_PORT);
            executor = (RemoteExecutor) registry.lookup(EXECUTOR_NAME);
            AbstractTask task = new SumTask(1, 3);
            Result result = executor.execute(task);
            System.out.println(result.getResult());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
