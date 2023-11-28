import java.rmi.*;
import java.util.ArrayList;

public interface MessengerInt extends Remote{

    public int createAccount(String username);

    public ArrayList<String> showAccounts(int authToken);

    public String sendMessage(int authToken, String recipient, String messageBody);

    public ArrayList<String> showInbox(int authToken);

    public void readMessage(int authToken, long messageID);

    public boolean deleteMessage(int authToken, long messageID);
}
