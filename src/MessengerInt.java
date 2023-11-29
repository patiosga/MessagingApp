import java.rmi.*;
import java.util.ArrayList;

public interface MessengerInt extends Remote{

    public int createAccount(String username) throws RemoteException;

    public ArrayList<String> showAccounts() throws RemoteException;

    public String sendMessage(int authToken, String recipient, String messageBody) throws RemoteException;

    public ArrayList<String> showInbox(int authToken) throws RemoteException;

    public String readMessage(int authToken, long messageID) throws RemoteException;

    public int deleteMessage(int authToken, long messageID) throws RemoteException;
}
