import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        //Create account : java client <ip> <port number> 1 <username> --> -1 : Sorry, the user already exists
        //Show accounts : java client <ip> <port number> 2 <authToken>
        //Send message : java client <ip> <port number> 3 <authToken> <recipient> <message_body>
        //Show inbox : java client <ip> <port number> 4 <authToken>
        //Read message : java client <ip> <port number> 5 <authToken> <message_id>
        //Delete message : java client <ip> <port number> 6 <authToken> <message_id>


        int fnID = 1;
        String ip = "localhost"; // args[0]
        int port = 5000; // args[1]
        String username = "tester_123"; // args[3] για 1
        String recipient = "receiver"; // args[4] για 3
        int authToken = 1024; // args[3] εκτός από fn_id = 1 που δε χρησιμοποιείται
        long message_id = 5; // args[4] για fnID = 5, 6
        String body = " hello world "; // args[5] για 3
        // Establish connection to RMI registry
        try {
            // connect to the RMI registry
            Registry rmiRegistry = LocateRegistry.getRegistry(ip,port); // --> αλλαγή με args (!)
            // get reference for remote object
            MessengerInt stub = (MessengerInt) rmiRegistry.lookup("messenger");


            switch(fnID) {
                case 1: // Create account
                    if (!stub.isValidUsername(username))
                        System.out.println("Invalid Username");
                    else {
                        int token = stub.createAccount(username);
                        if (token == -1) // κωδικός για υπάρχον username
                            System.out.println("Sorry, the user already exists");
                        else
                            System.out.println(token); //όλα πήγαν καλά και επιστρέφεται το authToken
                    }
                    break;
                case 2: // Show accounts
                    if (!stub.authTokenExists(authToken))
                        System.out.println("Invalid authentication token");
                    else
                        System.out.println(stub.showAccounts());
                    break;
                case 3:
                    // code block
                    break;
                case 4:
                    // code block
                    break;
                case 5:
                    // code block
                    break;
                case 6:
                    // code block
                    break;
                default:
                    // code block
            }
        } catch (Exception e) {
            System.out.println(e);
        }



    }
}

