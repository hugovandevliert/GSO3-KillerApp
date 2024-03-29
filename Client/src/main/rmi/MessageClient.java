package main.rmi;

import fontyspublisher.IRemotePublisherForListener;
import javafx.application.Platform;
import main.data.model.Message;
import main.ui.controller.BaseController;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static main.util.constant.Constants.*;

public class MessageClient extends UnicastRemoteObject implements IMessageClient {
    private transient final BaseController baseController;
    private transient IMessageServer server;

    public MessageClient(final Registry registry, final BaseController baseController,
                         final String newMessageProperty) throws RemoteException, NotBoundException {
        super();

        this.baseController = baseController;

        /* This is needed to assure we will not get a connection refused. The server also has this line of code. When the IP changes it should be edited in the server as well */
        System.setProperty("java.rmi.server.hostname", SERVER_IP);

        IRemotePublisherForListener messageListener = (IRemotePublisherForListener) registry.lookup(SERVER_NAME_THAT_PUSHES_MESSAGES_TO_CLIENTS);
        messageListener.subscribeRemoteListener(this, newMessageProperty);

        server = (IMessageServer) registry.lookup(SERVER_NAME_THAT_RECEIVES_MESSAGES_FROM_CLIENTS);
    }

    @Override
    public void sendMessage(final Message message) {
        try {
            server.sendMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        final Message message = (Message) propertyChangeEvent.getNewValue();

        Platform.runLater(() -> {
            try {
                baseController.displayMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
