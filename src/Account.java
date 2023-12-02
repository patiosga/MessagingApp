import java.util.*;

public class Account {
    private final String username;
    private int authToken;
    private final ArrayList<Message> messageBox;

    public Account(String username) {
        this.username = username;
        Random rand = new Random();
        authToken = rand.nextInt(2000); //authToken can be any integer value between 0 and 1999
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return getAuthToken() == account.getAuthToken();
    }
}
