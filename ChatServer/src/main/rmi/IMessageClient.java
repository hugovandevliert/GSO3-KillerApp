package main.rmi;

import fontyspublisher.IRemotePropertyListener;
import main.data.model.Message;

import java.rmi.RemoteException;

public interface IMessageClient extends IRemotePropertyListener {
    void sendMessage(final Message message) throws RemoteException;
}
