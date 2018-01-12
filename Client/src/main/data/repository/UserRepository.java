package main.data.repository;

import main.data.context.IUserContext;
import main.data.context.UserMySqlContext;
import main.data.model.User;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

public class UserRepository {
    private final IUserContext userContext = new UserMySqlContext();

    public boolean registerUser(final String username, final String password, final String salt, final String name, final String functionName) throws SQLException, ConnectException {
        return userContext.registerUser(username, password, salt, name, functionName);
    }

    public String[] getSaltAndHash(final String username) throws SQLException, ConnectException {
        return userContext.getSaltAndHash(username);
    }

    public User getUserByUsername(final String username) throws SQLException, ConnectException {
        return userContext.getUserByUsername(username);
    }

    public List<User> getAllUsers() throws SQLException, ConnectException {
        return userContext.getAllUsers();
    }

    public List<User> getUsersByChatId(final int chatId) throws SQLException, ConnectException {
        return userContext.getUsersByChatId(chatId);
    }

    public List<String> getFunctionNames() throws SQLException, ConnectException {
        return userContext.getFunctionNames();
    }

    public boolean checkUsernameAvailability(final String username) throws SQLException, ConnectException {
        return userContext.checkUsernameAvailability(username);
    }
}
