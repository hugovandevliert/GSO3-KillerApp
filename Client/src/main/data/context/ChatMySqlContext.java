package main.data.context;

import main.data.model.Chat;
import main.data.model.User;
import main.util.database.DatabaseHandler;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatMySqlContext implements IChatContext {
    @Override
    public List<Chat> getPrivateChatsByUserId(final int userId) throws SQLException, ConnectException {
        final String query = "SELECT c.id, c.name, c.chatType, uc.unreadCount FROM chat c " +
                "INNER JOIN userchats uc ON uc.chatId = c.id " +
                "WHERE uc.userId = ? AND c.chatType = ?";
        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(userId), "PRIVATE"});
        List<Chat> privateChats = new ArrayList<>();

        while (resultSet.next()) {
            Chat chat = new Chat(resultSet.getInt("id"), resultSet.getString("name"), Chat.ChatType.PRIVATE);
            chat.setUnreadCount(resultSet.getInt("unreadCount"));
            privateChats.add(chat);
        }
        return privateChats;
    }

    @Override
    public List<Chat> getGroupChatsByUserId(final int userId) throws SQLException, ConnectException {
        final String query = "SELECT c.id, c.name, c.chatType, uc.unreadCount FROM chat c " +
                "INNER JOIN userchats uc ON uc.chatId = c.id " +
                "WHERE uc.userId = ? AND c.chatType = ?";
        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(userId), "GROUP"});
        List<Chat> groupChats = new ArrayList<>();

        while (resultSet.next()) {
            final String chatType = resultSet.getString("chatType").toUpperCase();
            if (Chat.ChatType.valueOf(chatType) == Chat.ChatType.GROUP) {
                Chat chat = new Chat(resultSet.getInt("id"), resultSet.getString("name"), Chat.ChatType.GROUP);
                chat.setUnreadCount(resultSet.getInt("unreadCount"));
                groupChats.add(chat);
            }
        }
        return groupChats;
    }

    @Override
    public List<Chat> getMemosByUserId(final int userId) throws SQLException, ConnectException {
        final String query = "SELECT c.id, c.name, c.chatType, uc.unreadCount FROM chat c " +
                "INNER JOIN userchats uc ON uc.chatId = c.id " +
                "WHERE uc.userId = ? AND c.chatType = ?";
        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(userId), "MEMO"});
        List<Chat> memos = new ArrayList<>();

        while (resultSet.next()) {
            final String chatType = resultSet.getString("chatType").toUpperCase();
            if (Chat.ChatType.valueOf(chatType) == Chat.ChatType.MEMO) {
                Chat chat = new Chat(resultSet.getInt("id"), resultSet.getString("name"), Chat.ChatType.MEMO);
                chat.setUnreadCount(resultSet.getInt("unreadCount"));
                memos.add(chat);
            }
        }
        return memos;
    }

    @Override
    public Chat getChatWithId(final int chatId) throws SQLException, ConnectException {
        final String query = "SELECT c.id, c.chatType, c.name FROM chat c " +
                "WHERE c.id = ?";
        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(chatId)});

        if (resultSet.next()) {
            final String chatType = resultSet.getString("chatType").toUpperCase();
            return new Chat(resultSet.getInt("id"), resultSet.getString("name"), Chat.ChatType.valueOf(chatType));
        } else {
            return null;
        }
    }

    @Override
    public void resetUnreadCount(final int chatId, final int userId) throws SQLException, ConnectException {
        final String query = "UPDATE userchats SET unreadCount = 0 " +
                "WHERE chatId = ? AND userId = ?";
        DatabaseHandler.setData(query, new String[]{String.valueOf(chatId), String.valueOf(userId)}, true);
    }

    @Override
    public Chat createChat(final String chatName, final Chat.ChatType chatType, final List<User> chatUsers) throws SQLException, ConnectException {
        StringBuilder query = new StringBuilder("INSERT INTO chat (chatType, name) VALUES (?, ?);");
        ArrayList<String> values = new ArrayList<>();
        values.add(chatType.toString());
        values.add(chatName);
        for (User u : chatUsers) {
            query.append("INSERT INTO userchats (chatId, userId) VALUES (LAST_INSERT_ID(), ?);");
            values.add(String.valueOf(u.getId()));
        }

        ResultSet resultSet = DatabaseHandler.setData(query.toString(), values.toArray(new String[0]), true);
        if (resultSet.next()) {
            Chat chat = new Chat(resultSet.getInt(1), chatName, chatType);
            chat.setUsers(chatUsers);
            return chat;
        } else {
            return null;
        }
    }
}
