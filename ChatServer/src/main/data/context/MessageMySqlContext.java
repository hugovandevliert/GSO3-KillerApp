package main.data.context;

import main.data.model.Message;
import main.util.database.DatabaseHandler;

import java.net.ConnectException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class MessageMySqlContext implements IMessageContext {
    @Override
    public void addMessage(Message message) throws SQLException, ConnectException {
        final String query = "INSERT into message (chatId, senderId, fileId, `text`, `DATETIME`) values (?, ?, ?, ?, ?)";

        DatabaseHandler.setData(query, new String[]{String.valueOf(message.getChatId()), String.valueOf(message.getSenderId()),
                String.valueOf(message.getFileId()), message.getText(), String.valueOf(new Timestamp(new Date().getTime()))}, true);
    }
}
