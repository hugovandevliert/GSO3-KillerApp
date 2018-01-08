package main;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthServer extends Remote {
    void AnnounceClient() throws RemoteException;
}
