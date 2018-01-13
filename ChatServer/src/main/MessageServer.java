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

import static main.util.constant.constants.*;

public class MessageServer extends UnicastRemoteObject implements IMessageServer {
    private transient IRemotePublisherForDomain publisher;
    private transient MessageServerRepository messageServerRepository = new MessageServerRepository();

    private MessageServer() throws IOException {
        super();

        System.setProperty("java.rmi.server.hostname", SERVER_IP);

        Registry registry = LocateRegistry.createRegistry(PORT_NUMBER_MESSAGE);
        System.out.println("Created message registry on port " + PORT_NUMBER_MESSAGE);

        publisher = new RemotePublisher();
        registry.rebind(SERVER_NAME_THAT_PUSHES_MESSAGES_TO_CLIENTS, publisher);
        System.out.println("Rebinded " + SERVER_NAME_THAT_PUSHES_MESSAGES_TO_CLIENTS + " to publisher for message pushing to clients");

        registry.rebind(SERVER_NAME_THAT_RECEIVES_MESSAGES_FROM_CLIENTS, this);
        System.out.println("Rebinded " + SERVER_NAME_THAT_RECEIVES_MESSAGES_FROM_CLIENTS + " to publisher for message receiving from clients");
    }

    public static void main(String[] args) {
        try {
            new MessageServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            messageServerRepository.addMessage(message);
        } catch (SQLException | ConnectException e) {
            e.printStackTrace();
        }

        try {
            for (int userId : messageServerRepository.getUserIdsFromChatId(message.getChatId())) {
                if (userId != message.getSenderId() && publisher.getProperties().contains(CHANGED_PROPERTY + userId)) {
                    publisher.inform(CHANGED_PROPERTY + userId, null, message);
                }
            }
        } catch (SQLException | ConnectException e) {
            e.printStackTrace();
        } catch (RemoteException ignored) {}
    }
}
