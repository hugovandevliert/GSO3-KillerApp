package main.data.repository;

import main.data.context.ChatMySqlContext;
import main.data.context.IChatContext;
import main.data.model.Chat;
import main.data.model.User;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

public class ChatRepository {
    private IChatContext chatContext = new ChatMySqlContext();

    public List<Chat> getPrivateChatsByUserId(final int userId) throws SQLException, ConnectException {
        return chatContext.getPrivateChatsByUserId(userId);
    }

    public List<Chat> getGroupChatsByUserId(final int userId) throws SQLException, ConnectException {
        return chatContext.getGroupChatsByUserId(userId);
    }

    public List<Chat> getMemosByUserId(final int userId) throws SQLException, ConnectException {
        return chatContext.getMemosByUserId(userId);
    }

    public Chat getChatWithId(final int chatId) throws SQLException, ConnectException {
        return chatContext.getChatWithId(chatId);
    }

    public void resetUnreadCount(final int chatId, final int userId) throws SQLException, ConnectException {
        chatContext.resetUnreadCount(chatId, userId);
    }

    public Chat createChat(final String chatName, final Chat.ChatType chatType, final List<User> chatUsers) throws SQLException, ConnectException {
        return chatContext.createChat(chatName, chatType, chatUsers);
    }
}
