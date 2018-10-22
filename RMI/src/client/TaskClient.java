package client;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class TaskClient {
    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        String url = "rmi://127.0.0.1:1099/";
        double[] result = inputData();
        Task task = new Task(result[0], result[1], result[2]);
        try {
            Calculate calculator = (Calculate) Naming.lookup(url + "binominal");
            System.out.println(calculator.getResult(task));
        } catch (ConnectException e) {
            System.err.println("Something wrong with server. Maybe you're use a wrong" +
                    "address or server is offline???");
            System.err.println(e.getMessage());
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static double[] inputData() {
        Scanner scanner = new Scanner(System.in);
        double[] result = new double[3];
        System.out.println("Enter the constant A:");
        result[0] = scanner.nextDouble();
        System.out.println("Enter the constant B:");
        result[1] = scanner.nextDouble();
        System.out.println("Enter the variable X:");
        result[2] = scanner.nextDouble();
        return result;
    }
}
