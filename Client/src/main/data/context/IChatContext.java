package main.data.context;

import main.data.model.Chat;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

public interface IChatContext {
    List<Chat> getPrivateChatsByUserId(final int userId) throws SQLException, ConnectException;
    List<Chat> getGroupChatsByUserId(final int userId) throws SQLException, ConnectException;
    List<Chat> getMemosByUserId(final int userId) throws SQLException, ConnectException;
}
