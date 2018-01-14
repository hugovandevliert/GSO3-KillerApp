package main;

import fontyspublisher.IRemotePublisherForDomain;
import main.rmi.IAuthServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static main.util.constant.Constants.*;

public class AuthServer extends UnicastRemoteObject implements IAuthServer {
    private transient Registry authRegistry;
    private transient Registry messageRegistry;

    private AuthServer() throws RemoteException {
        super();

        System.setProperty("java.rmi.server.hostname", SERVER_IP);

        authRegistry = LocateRegistry.createRegistry(PORT_NUMBER_AUTH);
        //System.out.println("Created authentication authRegistry on port " + PORT_NUMBER_AUTH);

        authRegistry.rebind(SERVER_NAME_THAT_HANDLES_AUTHENTICATION, this);
        //System.out.println("Rebinded " + SERVER_NAME_THAT_HANDLES_AUTHENTICATION + " to publisher to receive authentication requests from clients");
    }

    public static void main(String[] args) {
        try {
            new AuthServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String registerClient(final int clientId) throws RemoteException, NotBoundException {
        if (messageRegistry == null) {
            locateMessageRegistry();
        }

        IRemotePublisherForDomain publisher = (IRemotePublisherForDomain) messageRegistry.lookup(SERVER_NAME_THAT_PUSHES_MESSAGES_TO_CLIENTS);
        publisher.registerProperty(CHANGED_PROPERTY + clientId);
        return CHANGED_PROPERTY + clientId;
    }

    private void locateMessageRegistry() throws RemoteException {
        messageRegistry = LocateRegistry.getRegistry(PORT_NUMBER_MESSAGE);
    }
}
