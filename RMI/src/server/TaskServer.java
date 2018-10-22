package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TaskServer {
    public static void main(String[] args) {
        final String localhost = "127.0.0.1";
        final String RMI_HOSTNAME = "java.rmi.server.hostname";
        try {
            System.setProperty(RMI_HOSTNAME, localhost);

            CalculateImpl calculateService = new CalculateImpl();
            String serviceName = "binominal";
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(serviceName, calculateService);
            System.out.println("client.Calculate service is initialized");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
