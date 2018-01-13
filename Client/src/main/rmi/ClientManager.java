package main.rmi;

import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static main.util.constant.constants.*;

public class ClientManager {
    private Registry registryAuth;
    private Registry registryMessage;
    private AuthClient authClient;
    private MessageClient messageClient;
    private IRemotePublisherForListener messageServerListener;
    private IRemotePropertyListener messageRemotePropertyListener;


    public ClientManager() {
        try {
            registryAuth = LocateRegistry.getRegistry(SERVER_IP, PORT_NUMBER_AUTH);
            registryMessage = LocateRegistry.getRegistry(SERVER_IP, PORT_NUMBER_MESSAGE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setMessageClient(final MessageClient messageClient) {
        if (messageClient != null){
            this.messageClient = messageClient;
        }
    }

    public void addServerMessageListener(final IRemotePublisherForListener serverMessageListener, final IRemotePropertyListener remotePropertyListener) {
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

    public Registry getRegistryMessage() {
        return registryMessage;
    }

    public Registry getRegistryAuth() {
        return registryAuth;
    }

    public AuthClient getAuthClient() {
        return authClient;
    }

    public void setAuthClient(AuthClient authClient) {
        if (authClient != null) {
            this.authClient = authClient;
        }
    }
}
