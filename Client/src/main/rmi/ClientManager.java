package main.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static main.util.constant.constants.*;

public class ClientManager {
    private Registry registryAuth;
    private Registry registryMessage;
    private AuthClient authClient;
    private MessageClient messageClient;


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

    public void setAuthClient(AuthClient authClient) {
        if (authClient != null) {
            this.authClient = authClient;
        }
    }

    public MessageClient getMessageClient() {
        return messageClient;
    }

    public AuthClient getAuthClient() {
        return authClient;
    }

    public Registry getRegistryMessage() {
        return registryMessage;
    }

    public Registry getRegistryAuth() {
        return registryAuth;
    }
}
