import java.net.*;
import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

// Παραδοχή : ΟΛΑ ΤΑ ARGUMENTS ΘΕΩΡΟΥΝΤΑΙ ΣΩΣΤΑ
public class Server {
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]); // θεωρείται ότι έχει δοθεί σωστά ακέραιος
            RemoteMessenger stub = new RemoteMessenger();
            // create the RMI registry on port 5000
            Registry rmiRegistry = LocateRegistry.createRegistry(port); // port num στο args[0]
            // path to access is rmi://localhost:5000/messenger
            rmiRegistry.rebind("messenger", stub);
            System.out.println("Server is ready");

        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
