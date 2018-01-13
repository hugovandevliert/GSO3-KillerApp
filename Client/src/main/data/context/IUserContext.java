package main.data.context;

import main.data.model.User;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

public interface IUserContext {
    boolean registerUser(final String username, final String password, final String salt, final String name, final String functionName) throws SQLException, ConnectException;

    String[] getSaltAndHash(final String username) throws SQLException, ConnectException;

    User getUserByUsername(final String username) throws SQLException, ConnectException;

    List<User> getAllUsers() throws SQLException, ConnectException;

    List<User> getUsersByChatId(int chatId) throws SQLException, ConnectException;

    List<String> getFunctionNames() throws SQLException, ConnectException;

    boolean checkUsernameAvailability(final String username) throws SQLException, ConnectException;
}
