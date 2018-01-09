package main.data.context;

import main.data.model.Message;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

public interface IMessageContext {
    public List<Message> getMessagesByChatId(final int chatId) throws SQLException, ConnectException;
    public Message getLastMessageByChatId(final int chatId) throws SQLException, ConnectException;
}
