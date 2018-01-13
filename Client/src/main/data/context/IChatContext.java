package main.data.context;

import main.data.model.Chat;
import main.data.model.User;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

public interface IChatContext {
    List<Chat> getPrivateChatsByUserId(final int userId) throws SQLException, ConnectException;
    List<Chat> getGroupChatsByUserId(final int userId) throws SQLException, ConnectException;
    List<Chat> getMemosByUserId(final int userId) throws SQLException, ConnectException;
    Chat getChatWithId(final int chatId) throws SQLException, ConnectException;
    void resetUnreadCount(final int chatId, final int userId) throws SQLException, ConnectException;
    Chat createChat(final String chatName, final Chat.ChatType chatType, final List<User> chatUsers) throws SQLException, ConnectException;
}
