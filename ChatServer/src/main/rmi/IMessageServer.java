package main.rmi;

import main.data.model.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMessageServer extends Remote {
    void sendMessage(Message message) throws RemoteException;
}
