import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Account {
    private final String username;
    private int authToken;
    private final List<Message> messageBox;

    public Account(String username) {
        this.username = username;
        Random rand = new Random();
        authToken = rand.nextInt(10000); //authToken can be any integer value between 0 and 9999
        messageBox = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public int getAuthToken() {
        return authToken;
    }

    public void regenerateAuthToken() {
        Random rand = new Random();
        authToken = rand.nextInt(10000); //authToken can be any integer value between 0 and 9999
    }

    public List<Message> getMessageBox() {
        return messageBox;
    }

    public void addMessage(Message message) {
        messageBox.add(message);
    }

    public boolean deleteMessage(long messageID) {
        Iterator<Message> it = messageBox.iterator();
        while (it.hasNext()) {
            Message temp = it.next();
            if (messageID == temp.getMessageID()) {
                it.remove(); // βρέθηκε μήνυμα με messageID και διαγράφθηκε από το messageBox
                return true;
            }
        }
        return false;
    }
}
