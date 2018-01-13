package main.rmi;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthServer extends Remote {
    String registerClient(final int clientId) throws RemoteException, NotBoundException;
}
