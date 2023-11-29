import java.rmi.*;
import java.util.ArrayList;

public interface MessengerInt extends Remote{

    public int createAccount(String username);

    public ArrayList<String> showAccounts();

    public String sendMessage(int authToken, String recipient, String messageBody);

    public ArrayList<String> showInbox(int authToken);

    public String readMessage(int authToken, long messageID);

    public int deleteMessage(int authToken, long messageID);
}
