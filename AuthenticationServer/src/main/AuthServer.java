package main;

import main.rmi.IAuthServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static main.util.constant.constants.*;

public class AuthServer extends UnicastRemoteObject implements IAuthServer {
    private AuthServer() throws RemoteException {
        super();

        System.setProperty("java.rmi.server.hostname", SERVER_IP);

        Registry registry = LocateRegistry.createRegistry(PORT_NUMBER_AUTH);
        System.out.println("Created authentication registry on port " + PORT_NUMBER_AUTH);

        registry.rebind(SERVER_NAME_THAT_HANDLES_AUTHENTICATION, this);
        System.out.println("Rebinded " + SERVER_NAME_THAT_HANDLES_AUTHENTICATION + " to publisher to receive authentication requests from clients");
    }

    public static void main(String[] args) {
        try {
            new AuthServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String announceClient() throws RemoteException {

        return "";
    }
}
