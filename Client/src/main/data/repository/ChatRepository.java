package main.data.repository;

import main.data.context.ChatMySqlContext;
import main.data.context.IChatContext;
import main.data.model.Chat;

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
}
