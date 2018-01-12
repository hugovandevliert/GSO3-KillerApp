package main.data.context;

import main.util.database.DatabaseHandler;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMySqlContext implements IUserContext {
    @Override
    public List<Integer> getUserIdsFromChatId(int chatId) throws SQLException, ConnectException {
        final String query = "SELECT u.id FROM `user` u " +
                "INNER JOIN userchats uc on uc.userId = u.id " +
                "WHERE uc.chatId = ?";

        ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(chatId)});

        List<Integer> userIds = new ArrayList<>();
        while (resultSet.next()) {
            userIds.add(resultSet.getInt("id"));
        }
        return userIds;
    }
}
