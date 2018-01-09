package main.data.context;

import main.data.model.Message;
import main.util.database.DatabaseHandler;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageMySqlContext implements IMessageContext {
    @Override
    public List<Message> getMessagesByChatId(final int chatId) throws SQLException, ConnectException {
        final String query = "SELECT m.senderId, m.fileId, u.name, m.text, m.datetime FROM message m " +
                "INNER JOIN `user` u on u.id = m.senderId " +
                "WHERE m.chatId = ?";
        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(chatId)});
        List<Message> messages = new ArrayList<>();

        while (resultSet.next()) {
            messages.add(new Message(resultSet.getString("text"), resultSet.getInt("senderId"),
                    resultSet.getString("name"), resultSet.getTime("datetime"), resultSet.getInt("fileId")));
        }
        return messages;
    }

    @Override
    public Message getLastMessageByChatId(int chatId) throws SQLException, ConnectException {
        final String query = "SELECT m.senderId, m.fileId, u.name, m.text, m.datetime FROM message m " +
                "INNER JOIN `user` u on u.id = m.senderId " +
                "WHERE m.chatId = ? " +
                "ORDER BY DATETIME DESC LIMIT 1";
        ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(chatId)});

        if (resultSet != null && resultSet.next()) {
            return new Message(resultSet.getString("text"), resultSet.getInt("senderId"),
                    resultSet.getString("name"), resultSet.getTime("datetime"), resultSet.getInt("fileId"));
        } else {
            return null;
        }
    }
}
