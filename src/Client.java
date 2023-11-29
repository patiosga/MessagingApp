import java.io.*;
import java.net.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Client {
    public static void main(String[] args) {
        try {
            // connect to the RMI registry
            Registry rmiRegistry = LocateRegistry.getRegistry(5000);
            // get reference for remote object
            MessengerInt stub = (MessengerInt) rmiRegistry.lookup("messenger");
//            Account account = new Account("tester0");
            //int token = stub.createAccount("tester0")
            System.out.println("Token     : " + stub.createAccount("tester0"));
            System.out.println("Token     : " + stub.createAccount("tester1"));
            System.out.println("Token     : " + stub.createAccount("tester2"));
            System.out.println("Tester0   : " + stub.showAccounts().get(0));
            System.out.println("Tester0   : " + stub.showAccounts().get(1));
            System.out.println("Tester0   : " + stub.showAccounts().get(2));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

