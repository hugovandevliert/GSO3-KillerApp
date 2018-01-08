package main;

import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.RemotePublisher;
import main.data.model.Message;
import main.rmi.IMessageServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MessageServer extends UnicastRemoteObject implements IMessageServer {

    private static final String SERVER_NAME_THAT_PUSHES_TO_CLIENTS = "Server_Pusher";
    private static final String SERVER_NAME_THAT_RECEIVES_FROM_CLIENTS = "Server_Receiver";
    private static final String CHANGED_PROPERTY = "newMessage";
    private static final String SERVER_IP = "localhost";
    private static final int PORT_NUMBER = 1098;

    private Registry registry = null;
    private IRemotePublisherForDomain publisher;

    protected MessageServer() throws IOException {
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
            final MessageServer messageServer = new MessageServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        publisher.inform(CHANGED_PROPERTY, null, message);
    }
}
