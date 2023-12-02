import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


public class RemoteMessenger extends UnicastRemoteObject implements MessengerInt{
    private final ArrayList<Account> accounts;
    private final ArrayList<Message> messages;

    protected RemoteMessenger() throws RemoteException {
        super();
        accounts = new ArrayList<>();
        messages = new ArrayList<>();
    }

    private boolean usernameExists(String username) {
        if (accounts.isEmpty())
            return false;
        for (Account account : accounts) {
            if (Objects.equals(account.getUsername(), username))
                return true;
        }
        return false;
    }

    public boolean authTokenExists(int authToken) {
        if (accounts.isEmpty())
            return false;
        for (Account account : accounts) {
            if (authToken == account.getAuthToken())
                return true;
        }
        return false;
    }

    private String getUsernameFromAuthToken(int authToken) {
        if (accounts.isEmpty())
            return null;
        else {
            for (Account account : accounts) {
                if (account.getAuthToken() == authToken)
                    return account.getUsername();
            }
        }
        return null; // authToken δεν υπάρχει
    }

    @Override
    public boolean isValidUsername(String username) {
        //trust me bro
        return username.matches("[\\w]*"); //uses regex for word characters --> a-zA-Z_0-9
    }

    @Override
    public int createAccount(String username) {
        synchronized (this) {
            // πρέπει να είναι synchronized για να αποφευχθεί η κατά λάθος δημιουργία δύο λογαριασμών με το ίδιο authToken
            if (usernameExists(username))
                return -1; // κωδικός existing username (-1)
            else {
                synchronized (accounts) {
                    Account new_acc = new Account(username);
                    while (authTokenExists(new_acc.getAuthToken()))
                        new_acc.regenerateAuthToken();
                    accounts.add(new_acc);
                    return new_acc.getAuthToken();
                }
            }
        }
    }

    @Override
    public String showAccounts(int authToken) {
        String sender = getUsernameFromAuthToken(authToken);
        if (sender == null)
            return "Invalid Auth Token"; // authToken δεν αντιστοιχεί σε χρήστη
        ArrayList<String> usernames = new ArrayList<>();
        int counter = 1;
        if (accounts.isEmpty())
            return "";
        for (Account account : accounts) {
            usernames.add(counter + ". " + account.getUsername()); // για να τυπώνει 1. <username1> κτλ
            counter++;
        }
        return String.join("\n", usernames);
    }

    @Override
    public String sendMessage(int authToken, String recipient, String messageBody) {
        String sender = getUsernameFromAuthToken(authToken);
        if (sender == null)
            return "Invalid Auth Token"; // authToken δεν αντιστοιχεί σε χρήστη
        if (!usernameExists(recipient))
            return "User does not exist"; // δεν υπάρχει recipient με αυτό το username
        synchronized (messages) {
            Message message = new Message(sender, recipient, messageBody);
            messages.add(message);
            return "OK";
        }
    }

    @Override
    public String showInbox(int authToken) {
        String username = getUsernameFromAuthToken(authToken);
        if (username == null)
            return "Invalid Auth Token"; // authToken δεν αντιστοιχεί σε χρήστη
        // authToken exists
        ArrayList<String> inboxMessages = new ArrayList<>();
        if (messages.isEmpty())
            return "";
        for (Message message : messages) {
            if (message.getReceiver().equals(username))
                //παίρνω μηνύματα όπου το authToken αντιστοιχεί στον receiver
                inboxMessages.add(message.toString());
        }
        return String.join("\n", inboxMessages);
    }

    @Override
    public String readMessage(int authToken, long messageID) {
        String username = getUsernameFromAuthToken(authToken); // αντιστοίχιση authToken με username
        if (username == null)
            return "Invalid Auth Token";
        // το authToken δεν αντιστοιχεί σε υπαρκτό χρήστη (κωδικός 2) /
        // Παραδοχή ότι δε θα προκύψει ποτέ αλλά υπάρχει παντού για να μη κρασάρει πουθενά κατά λάθος
        if (messages.isEmpty())
            return "Message ID does not exist";  // το μήνυμα δεν υπάρχει (κωδικός 1)
        for (int i = 0; i < messages.size(); i++) {
            if (username.equals(messages.get(i).getReceiver()) && messageID == messages.get(i).getMessageID()) {
                messages.get(i).readMessage(); //!!!!!!!!!
                return "(" + messages.get(i).getSender() + ") " +  messages.get(i).getBody(); // (<sender>) <message>
            }
        }
        return "Message ID does not exist";
    }

    @Override
    public String deleteMessage(int authToken, long messageID) {
        String username = getUsernameFromAuthToken(authToken);
        if (username == null)
            return "Invalid Auth Token"; // το authToken δεν αντιστοιχεί σε υπαρκτό χρήστη (κωδικός 2)
        if (messages.isEmpty())
            return "Message ID does not exist";  // το μήνυμα δεν υπάρχει (κωδικός 1)
        Iterator<Message> it = messages.iterator();
        while (it.hasNext()) {
            Message temp = it.next();
            if (messageID == temp.getMessageID() && username.equals(temp.getReceiver())) { // διαγράφω μήνυμα του receiver ωστε να μην εμφανίζεται στο inbox του
                synchronized (messages) { // συγχρονισμός πεδίου πίνακα μηνυμάτων κατά τη διαγραφή
                    it.remove(); // βρέθηκε μήνυμα με messageID και διαγράφθηκε από τα messages
                    return "OK"; // όλα καλά (κωδικός 0)
                }
            }
        }
        return "Message ID does not exist";
    }
}
