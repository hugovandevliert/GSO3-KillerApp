package main.rmi;

import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientManager {

    private static final String SERVER_NAME_THAT_PUSHES_TO_CLIENTS = "Server_Pusher";
    private static final String SERVER_NAME_THAT_RECEIVES_FROM_CLIENTS = "Server_Receiver";
    private static final String CHANGED_PROPERTY = "newMessage";
    private static final String SERVER_IP = "localhost";
    private static final int PORT_NUMBER = 1098;

    private Registry registry;
    private MessageClient messageClient;
    private IRemotePublisherForListener messageServerListener;
    private IRemotePropertyListener messageRemotePropertyListener;

    public ClientManager() {
        try {
            registry = LocateRegistry.getRegistry(SERVER_IP, PORT_NUMBER);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setMessageClient(final MessageClient messageClient) {
        if (messageClient != null){
            this.messageClient = messageClient;
        }
    }

    public void addBidServerMessageListener(final IRemotePublisherForListener serverMessageListener, final IRemotePropertyListener remotePropertyListener) {
        this.messageServerListener = serverMessageListener;
        this.messageRemotePropertyListener = remotePropertyListener;
    }

    public void unsubscribeRemoteListeners() {
        /* This is needed to assure we can re-subscribe ourselves without getting connection refused from the server */
        try {
            if (messageServerListener != null){
                messageServerListener.unsubscribeRemoteListener(messageRemotePropertyListener, CHANGED_PROPERTY);
            }

            this.messageServerListener = null;
            this.messageRemotePropertyListener = null;
            this.messageClient = null;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public MessageClient getMessageClient() {
        return messageClient;
    }

    public Registry getRegistry() {
        return registry;
    }
}
