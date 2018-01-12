package main.rmi;

import fontyspublisher.IRemotePublisherForListener;
import javafx.application.Platform;
import main.data.model.Message;
import main.ui.controller.ChatController;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import static main.util.constant.constants.*;


public class MessageClient extends UnicastRemoteObject implements IMessageClient {
    private transient IMessageServer server;

    private final int auctionId;
    private final int currentUserId;
    private transient final ChatController chatController;
    //private final MusicPlayer musicPlayer;

    public MessageClient(final Registry registry, final int auctionId, final int currentUserId, final ClientManager clientManager, final ChatController chatController) throws IOException, NotBoundException {
        super();
        this.auctionId = auctionId;
        this.currentUserId = currentUserId;
        this.chatController = chatController;
        //this.musicPlayer = new MusicPlayer(Constants.BEEP_NEW_BID_MP3);

        /* This is needed to assure we will not get a connection refused. The server also has this line of code. When the IP changes it should be edited in the server as well */
        System.setProperty("java.rmi.server.hostname", SERVER_IP);

        IRemotePublisherForListener messageListener = (IRemotePublisherForListener) registry.lookup(SERVER_NAME_THAT_PUSHES_TO_CLIENTS);
        messageListener.subscribeRemoteListener(this, CHANGED_PROPERTY + currentUserId);
        clientManager.addBidServerMessageListener(messageListener, this);

        server = (IMessageServer) registry.lookup(SERVER_NAME_THAT_RECEIVES_FROM_CLIENTS);
    }

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

        Platform.runLater(() -> chatController.loadMessage(message));
        //musicPlayer.playSound();
    }

    private boolean isCurrentUser(final int userId) {
        return userId == this.currentUserId;
    }

    @Override
    public int getMessageId() throws RemoteException {
        return 0;
    }
}
