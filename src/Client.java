import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) {
        // Παραδοχή : ΟΛΑ ΤΑ ARGUMENTS ΘΕΩΡΟΥΝΤΑΙ ΣΩΣΤΑ ΓΙΑ ΤΟ ΕΚΑΣΤΟΤΕ fn_id
        //Create account : java client <ip> <port number> 1 <username> --> -1 : Sorry, the user already exists
        //Show accounts : java client <ip> <port number> 2 <authToken>
        //Send message : java client <ip> <port number> 3 <authToken> <recipient> <message_body>
        //Show inbox : java client <ip> <port number> 4 <authToken>
        //Read message : java client <ip> <port number> 5 <authToken> <message_id>
        //Delete message : java client <ip> <port number> 6 <authToken> <message_id>


        int fnID = Integer.parseInt(args[2]);
        String ip = args[0]; // args[0]
        int port = Integer.parseInt(args[1]); // args[1]
        String username = args[3]; // args[3] για 1
        String recipient; // args[4] για 3
        String body; // args[5] για 3
        int message_id; // ορίζεται παρακάτω γιατί πρέπει να γίνει parsed

        int authToken = -1; // ήθελε αρχικοποίηση για λόγους debugging
        if (fnID != 1)
            authToken = Integer.parseInt(args[3]); // args[3] εκτός από fn_id = 1 που δε χρησιμοποιείται

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
                    System.out.println(stub.showAccounts(authToken));
                    break;
                case 3: // Send message
                    recipient = args[4]; // args[4] για 3
                    body = args[5]; // args[5] για 3
                    System.out.println(stub.sendMessage(authToken, recipient, body));
                    break;
                case 4: // Show inbox
                    System.out.println(stub.showInbox(authToken));
                    break;
                case 5: // Read message
                    message_id = Integer.parseInt(args[4]); // args[4] για fnID = 5, 6
                    System.out.println(stub.readMessage(authToken, message_id));
                    break;
                case 6: // Delete message
                    message_id = Integer.parseInt(args[4]); // args[4] για fnID = 5, 6
                    System.out.println(stub.deleteMessage(authToken, message_id));
                    break;
                default:
                    // code block
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }



    }
}

