package main.data.repository;

import main.data.context.IUserContext;
import main.data.context.UserMySqlContext;
import main.data.model.User;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

public class UserRepository {
    private final IUserContext context = new UserMySqlContext();

    public boolean registerUser(final String username, final String password, final String salt, final String name, final String functionName) throws SQLException, ConnectException {
        return context.registerUser(username, password, salt, name, functionName);
    }

    public String[] getSaltAndHash(final String username) throws SQLException, ConnectException {
        return context.getSaltAndHash(username);
    }

    public User getUserByUsername(final String username) throws SQLException, IOException, ClassNotFoundException {
        return context.getUserByUsername(username);
    }

    public List<String> getFunctionNames() throws SQLException, ConnectException {
        return context.getFunctionNames();
    }

    public boolean checkUsernameAvailability(final String username) throws SQLException, ConnectException {
        return context.checkUsernameAvailability(username);
    }
}
