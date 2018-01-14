package main;

import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.RemotePublisher;
import main.data.model.Message;
import main.data.repository.MessageServerRepository;
import main.rmi.IMessageServer;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.util.constant.Constants.*;

public class MessageServer extends UnicastRemoteObject implements IMessageServer {
    private final static Logger LOGGER = Logger.getLogger(MessageServer.class.getName());

    private transient IRemotePublisherForDomain publisher;
    private transient MessageServerRepository messageServerRepository = new MessageServerRepository();

    private MessageServer() throws IOException {
        super();

        System.setProperty("java.rmi.server.hostname", SERVER_IP);

        Registry registry = LocateRegistry.createRegistry(PORT_NUMBER_MESSAGE);
        LOGGER.log(Level.INFO, String.format("Created message registry on port %d", PORT_NUMBER_MESSAGE));

        publisher = new RemotePublisher();
        registry.rebind(SERVER_NAME_THAT_PUSHES_MESSAGES_TO_CLIENTS, publisher);
        LOGGER.log(Level.INFO, String.format("Rebinded %s to publisher for message pushing to clients", SERVER_NAME_THAT_PUSHES_MESSAGES_TO_CLIENTS));

        registry.rebind(SERVER_NAME_THAT_RECEIVES_MESSAGES_FROM_CLIENTS, this);
        LOGGER.log(Level.INFO, String.format("Rebinded %s to publisher for message receiving from clients", SERVER_NAME_THAT_RECEIVES_MESSAGES_FROM_CLIENTS));
    }

    public static void main(String[] args) {
        try {
            new MessageServer();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            messageServerRepository.addMessage(message);
        } catch (SQLException | ConnectException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }

        try {
            for (int userId : messageServerRepository.getUserIdsFromChatId(message.getChatId())) {
                if (userId != message.getSenderId() && publisher.getProperties().contains(CHANGED_PROPERTY + userId)) {
                    publisher.inform(CHANGED_PROPERTY + userId, null, message);
                }
            }
        } catch (SQLException | ConnectException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        } catch (RemoteException ex) {
            LOGGER.log(Level.FINE, ex.toString(), ex);
        }
    }
}
