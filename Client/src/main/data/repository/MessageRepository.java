package main.data.repository;

import main.data.context.IMessageContext;
import main.data.context.MessageMySqlContext;
import main.data.model.Message;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

public class MessageRepository {
    private IMessageContext messageContext = new MessageMySqlContext();

    public List<Message> getMessagesByChatId(final int chatId) throws SQLException, ConnectException {
        return messageContext.getMessagesByChatId(chatId);
    }

    public Message getLastMessageByChatId(final int chatId) throws SQLException, ConnectException {
        return messageContext.getLastMessageByChatId(chatId);
    }
}
