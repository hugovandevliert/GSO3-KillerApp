package main.data.context;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

public interface IUserContext {
    List<Integer> getUserIdsFromChatId(final int chatId) throws SQLException, ConnectException;
}
