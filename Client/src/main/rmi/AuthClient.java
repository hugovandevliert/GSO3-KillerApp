package main.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import static main.util.constant.constants.SERVER_IP;
import static main.util.constant.constants.SERVER_NAME_THAT_HANDLES_AUTHENTICATION;

public class AuthClient {
    private IAuthServer server;

    public AuthClient(final Registry registry) throws NotBoundException, RemoteException {
        /* This is needed to assure we will not get a connection refused. The server also has this line of code. When the IP changes it should be edited in the server as well */
        System.setProperty("java.rmi.server.hostname", SERVER_IP);

        server = (IAuthServer) registry.lookup(SERVER_NAME_THAT_HANDLES_AUTHENTICATION);
    }

    public String registerClient(final int clientId) throws RemoteException {
        return server.registerClient(clientId);
    }
}
