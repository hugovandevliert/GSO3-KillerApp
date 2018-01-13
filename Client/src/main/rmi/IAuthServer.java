package main.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthServer extends Remote {
    String announceClient() throws RemoteException;
}
