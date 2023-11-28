import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RemoteMessenger extends UnicastRemoteObject implements MessengerInt{
    protected RemoteMessenger() throws RemoteException {
        super();
    }
}
