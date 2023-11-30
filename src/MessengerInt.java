import java.rmi.*;
import java.util.ArrayList;

public interface MessengerInt extends Remote{

    public int createAccount(String username) throws RemoteException;

    public String showAccounts() throws RemoteException;

    public String sendMessage(int authToken, String recipient, String messageBody) throws RemoteException;

    public String showInbox(int authToken) throws RemoteException;

    public String readMessage(int authToken, long messageID) throws RemoteException;

    public String deleteMessage(int authToken, long messageID) throws RemoteException;

    public boolean isValidUsername(String username) throws RemoteException;

    public boolean authTokenExists(int token) throws RemoteException;
}
