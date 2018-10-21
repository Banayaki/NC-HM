package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class TaskClient {
    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        String url = "rmi://127.0.0.1:1099/";
        Task task = new Task(2, -9, 0);
        try {
            Calculate calculator = (Calculate) Naming.lookup(url + "binominal");
            System.out.println(calculator.getResult(task));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
