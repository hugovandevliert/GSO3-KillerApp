package main.data.context;

import main.data.model.Message;

import java.net.ConnectException;
import java.sql.SQLException;

public interface IMessageContext {
    void addMessage(final Message message) throws SQLException, ConnectException;
}
