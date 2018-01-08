package main.rmi;

import fontyspublisher.IRemotePropertyListener;

import java.rmi.RemoteException;

public interface IMessageClient extends IRemotePropertyListener {
    int getMessageId() throws RemoteException;
}
