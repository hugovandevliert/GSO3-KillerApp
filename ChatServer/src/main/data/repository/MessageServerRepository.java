package main.data.repository;

import main.data.context.IMessageContext;
import main.data.context.MessageMySqlContext;
import main.data.model.Message;

import java.net.ConnectException;
import java.sql.SQLException;

public class MessageServerRepository {
    private IMessageContext messageContext = new MessageMySqlContext();

    public void addMessage(Message message) throws SQLException, ConnectException {
        messageContext.addMessage(message);
    }
}
