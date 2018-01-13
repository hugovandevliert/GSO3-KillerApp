package main.data.repository;

import main.data.context.IMessageContext;
import main.data.context.IUserContext;
import main.data.context.MessageMySqlContext;
import main.data.context.UserMySqlContext;
import main.data.model.Message;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

public class MessageServerRepository {
    private final IMessageContext messageContext = new MessageMySqlContext();
    private final IUserContext userContext = new UserMySqlContext();

    public void addMessage(final Message message) throws SQLException, ConnectException {
        messageContext.addMessage(message);
    }

    public List<Integer> getUserIdsFromChatId(final int chatId) throws SQLException, ConnectException {
        return userContext.getUserIdsFromChatId(chatId);
    }
}
