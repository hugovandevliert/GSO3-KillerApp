package main;

import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AuthServer extends UnicastRemoteObject implements IAuthServer {
    
    private static final String SERVER_NAME_THAT_PUSHES_TO_CLIENTS = "Server_Pusher";
    private static final String SERVER_NAME_THAT_RECEIVES_FROM_CLIENTS = "Server_Receiver";
    private static final String CHANGED_PROPERTY = "newBid";
    private static final String SERVER_IP = "localhost";
    private static final int PORT_NUMBER = 1098;

    private Registry registry = null;
    private IRemotePublisherForDomain publisher;

    protected AuthServer() throws RemoteException {
        super();

        System.setProperty("java.rmi.server.hostname", SERVER_IP);

        publisher = new RemotePublisher();
        this.publisher.registerProperty(CHANGED_PROPERTY);
        System.out.println("Started publisher and registered " + CHANGED_PROPERTY + " property");

        registry = LocateRegistry.createRegistry(PORT_NUMBER);
        System.out.println("Created registry on port " + PORT_NUMBER);

        registry.rebind(SERVER_NAME_THAT_PUSHES_TO_CLIENTS, publisher);
        System.out.println("Rebinded " + SERVER_NAME_THAT_PUSHES_TO_CLIENTS + " to publisher for message pushing towards clients");

        registry.rebind(SERVER_NAME_THAT_RECEIVES_FROM_CLIENTS, this);
        System.out.println("Rebinded " + SERVER_NAME_THAT_RECEIVES_FROM_CLIENTS + " to publisher for message receiving from clients");
    }

    public static void main(String[] args) {
        try {
            final AuthServer authServer = new AuthServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void AnnounceClient() throws RemoteException {

    }

//    @Override
//    public void sendBid(final Bid bid) throws RemoteException {
//        // A client has sent a new bid. We should send this bid to all the clients that are registered for this auction
//        publisher.inform(CHANGED_PROPERTY, null, bid);
//    }
}
