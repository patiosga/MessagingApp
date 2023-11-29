import java.net.*;
import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server {
    public static void main(String[] args) {
        try {
            RemoteMessenger stub = new RemoteMessenger();
            // create the RMI registry on port 5000
            Registry rmiRegistry = LocateRegistry.createRegistry(Integer.parseInt(args[0])); // port num στο args[0]
            // path to access is rmi://localhost:5000/messenger
            rmiRegistry.rebind("messenger", stub);
            System.out.println("Server is ready");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
