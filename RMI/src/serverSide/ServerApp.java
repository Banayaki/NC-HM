package serverSide;

import onBothSide.RemoteExecutor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerApp {
    private static final String SECURITY_POLICY_PATH = "./src/onBothSide/SecurityServer.policy";
    private static final String LOG_CALLS = "true";
    private static final int REGISTRY_PORT = 1099;
    private static final String REGISTRY_HOST = "127.0.1.1";
    private static final int EXECUTOR_PORT = 30003;
    private static final String EXECUTOR_NAME = "Calculator";

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", REGISTRY_HOST);
        System.setProperty("java.security.policy", SECURITY_POLICY_PATH);
        System.setProperty("java.rmi.server.logCalls", LOG_CALLS);
        System.setSecurityManager(new SecurityManager());
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            RemoteExecutor executor = new RemoteExecutorImpl();
            UnicastRemoteObject.exportObject(executor, EXECUTOR_PORT);
            registry.rebind(EXECUTOR_NAME, executor);
            System.out.println("Server is started");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
