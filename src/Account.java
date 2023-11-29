import java.util.*;

public class Account {
    private final String username;
    private int authToken;
    private final ArrayList<Message> messageBox;

    public Account(String username) {
        this.username = username;
        Random rand = new Random();
        authToken = rand.nextInt(20000); //authToken can be any integer value between 0 and 19999
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
        authToken = rand.nextInt(20000); //authToken can be any integer value between 0 and 9999
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return getAuthToken() == account.getAuthToken();
    }
}
