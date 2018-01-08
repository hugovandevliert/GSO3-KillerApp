package main.data.context;

import main.data.model.User;

import java.net.ConnectException;
import java.sql.SQLException;

public interface IUserContext {
    boolean registerUser(final String username, final String password, final String salt, final String name) throws SQLException, ConnectException;
    String[] getSaltAndHash(final String username) throws SQLException, ConnectException;
    boolean setPassword(final String newPassword, final String salt, final String username) throws SQLException, ConnectException;
    User getUserByUsername(final String username) throws SQLException, ClassNotFoundException, ConnectException;
}
