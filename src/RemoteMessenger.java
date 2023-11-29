import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


// AUTHENTICATION WHEN IT IS NEEDED

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

    private boolean authTokenExists(int authToken) {
        if (accounts.isEmpty())
            return false;
        for (Account account : accounts) {
            if (authToken == account.getAuthToken())
                return true;
        }
        return false;
    }

    @Override
    public int createAccount(String username) {
        if (usernameExists(username))
            return 1; // κωδικός existing username
        else {
            Account new_acc = new Account(username);
            while (authTokenExists(new_acc.getAuthToken()))
                new_acc.regenerateAuthToken();
            accounts.add(new_acc);
            return 0;
        }
    }

    @Override
    public ArrayList<String> showAccounts() {
        ArrayList<String> usernames = new ArrayList<>();
        int counter = 1;
        if (accounts.isEmpty())
            return usernames;
        for (Account account : accounts) {
            usernames.add(counter + ". " + account.getUsername()); // για να τυπώνει 1. <username1> κτλ
            counter++;
        }
        return usernames;
    }

    @Override
    public String sendMessage(int authToken, String recipient, String messageBody) {
        return null;
    }

    @Override
    public ArrayList<String> showInbox(int authToken) {
        ArrayList<String> inboxMessages = new ArrayList<>();
        if (messages.isEmpty())
            return inboxMessages;
        for (Message message : messages) {
            inboxMessages.add(message.toString());
        }
        return inboxMessages;
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
    public String readMessage(int authToken, long messageID) {
        String username = getUsernameFromAuthToken(authToken);
        if (username == null)
            return null; // το authToken δεν αντιστοιχεί σε υπαρκτό χρήστη (κωδικός 2) / Παραδοχή ότι δε θα προκύψει ποτέ
        if (messages.isEmpty())
            return null;  // το μήνυμα δεν υπάρχει (κωδικός 1)
        for (int i = 0; i < messages.size(); i++) {
            if (username.equals(messages.get(i).getReceiver()) && messageID == messages.get(i).getMessageID()) {
                messages.get(i).readMessage(); //!!!!!!!!!
                return "(" + messages.get(i).getSender() + ") " +  messages.get(i).getBody(); // (<sender>) <message>
            }
        }
        return null;
    }

    @Override
    public int deleteMessage(int authToken, long messageID) {
        String username = getUsernameFromAuthToken(authToken);
        if (username == null)
            return 2; // το authToken δεν αντιστοιχεί σε υπαρκτό χρήστη (κωδικός 2)
        if (messages.isEmpty())
            return 1;  // το μήνυμα δεν υπάρχει (κωδικός 1)
        Iterator<Message> it = messages.iterator();
        while (it.hasNext()) {
            Message temp = it.next();
            if (messageID == temp.getMessageID() && username.equals(temp.getReceiver())) { // διαγράφω μήνυμα του receiver ωστε να μην εμφανίζεται στο inbox του
                it.remove(); // βρέθηκε μήνυμα με messageID και διαγράφθηκε από τα messages
                return 0; // όλα καλά (κωδικός 0)
            }
        }
        return 1;
    }
}
