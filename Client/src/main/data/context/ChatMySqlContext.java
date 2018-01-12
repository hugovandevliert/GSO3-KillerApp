package main.data.context;

import main.data.model.Chat;
import main.util.database.DatabaseHandler;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatMySqlContext implements IChatContext {
    @Override
    public List<Chat> getPrivateChatsByUserId(final int userId) throws SQLException, ConnectException {
        final String query = "SELECT c.id, c.name, c.chatType FROM chat c " +
                "INNER JOIN userchats uc ON uc.chatId = c.id " +
                "WHERE uc.userId = ? AND c.chatType = ?";
        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(userId), "PRIVATE"});
        List<Chat> privateChats = new ArrayList<>();

        while (resultSet.next()) {
            privateChats.add(new Chat(resultSet.getInt("id"), resultSet.getString("name"), Chat.ChatType.PRIVATE));
        }
        return privateChats;
    }

    @Override
    public List<Chat> getGroupChatsByUserId(final int userId) throws SQLException, ConnectException {
        final String query = "SELECT c.id, c.name, c.chatType FROM chat c " +
                "INNER JOIN userchats uc ON uc.chatId = c.id " +
                "WHERE uc.userId = ? AND c.chatType = ?";
        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(userId), "GROUP"});
        List<Chat> groupChats = new ArrayList<>();

        while (resultSet.next()) {
            final String chatType = resultSet.getString("chatType").toUpperCase();
            if (Chat.ChatType.valueOf(chatType) == Chat.ChatType.GROUP) {
                groupChats.add(new Chat(resultSet.getInt("id"), resultSet.getString("name"), Chat.ChatType.GROUP));
            }
        }
        return groupChats;
    }

    @Override
    public List<Chat> getMemosByUserId(final int userId) throws SQLException, ConnectException {
        final String query = "SELECT c.id, c.name, c.chatType FROM chat c " +
                "INNER JOIN userchats uc ON uc.chatId = c.id " +
                "WHERE uc.userId = ? AND c.chatType = ?";
        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(userId), "MEMO"});
        List<Chat> memos = new ArrayList<>();

        while (resultSet.next()) {
            final String chatType = resultSet.getString("chatType").toUpperCase();
            if (Chat.ChatType.valueOf(chatType) == Chat.ChatType.MEMO) {
                memos.add(new Chat(resultSet.getInt("id"), resultSet.getString("name"), Chat.ChatType.MEMO));
            }
        }
        return memos;
    }

    @Override
    public Chat getChatWithId(final int chatId) throws SQLException, ConnectException {
        final String qeury = "SELECT c.id, c.chatType, c.name FROM chat c " +
                "WHERE c.id = ?";
        final ResultSet resultSet = DatabaseHandler.getData(qeury, new String[]{String.valueOf(chatId)});

        if (resultSet.next()) {
            final String chatType = resultSet.getString("chatType").toUpperCase();
            return new Chat(resultSet.getInt("id"), resultSet.getString("name"), Chat.ChatType.valueOf(chatType));
        } else {
            return null;
        }
    }
}
