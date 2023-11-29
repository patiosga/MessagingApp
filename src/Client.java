import java.io.*;
import java.net.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Client {
    public static void main(String[] args) {
        try {
            // connect to the RMI registry
            Registry rmiRegistry = LocateRegistry.getRegistry("localhost",5000);
            // get reference for remote object
            MessengerInt stub = (MessengerInt) rmiRegistry.lookup("messenger");
//            Account account = new Account("tester0");
            //int token = stub.createAccount("tester0")
            int token0 = stub.createAccount("tester0");
            int token1 = stub.createAccount("tester1");
            int token2 = stub.createAccount("tester2");
            System.out.println("Accounts   :\n" + stub.showAccounts());

            //Send messages
            System.out.println(stub.sendMessage(token0, "tester1", "hello tester1"));
            System.out.println(stub.sendMessage(token1, "tester0", "hello tester0"));
            System.out.println(stub.sendMessage(token2, "tester0", "hello tester0"));
            System.out.println(stub.sendMessage(-5, "tester1", "hello tester1"));
            System.out.println(stub.sendMessage(token0, "tester3", "hello tester1"));

            //Show inbox
//            System.out.println(stub.showInbox(token0) + "\n");
//            System.out.println(stub.showInbox(token1) + "\n");
//            System.out.println(stub.showInbox(token2) + "\n");
//            System.out.println(stub.showInbox(-5));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

