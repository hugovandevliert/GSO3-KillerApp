package main.data.context;

import main.data.model.User;
import main.util.database.DatabaseHandler;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserMySqlContext implements IUserContext {
    @Override
    public boolean registerUser(final String username, final String password, final String salt, final String name, final String functionName) throws SQLException, ConnectException {
        final String query = "INSERT INTO `User` (`Username`, `Password`, `Salt`, `Name`) VALUES (?, ?, ?, ?);" +
                "INSERT INTO `userfunction` (userId, functionId) VALUES (LAST_INSERT_ID(), (SELECT id FROM `Function` WHERE `name` = ?));";

        return 1 == DatabaseHandler.setData(query, new String[]{ username, password, salt, name, functionName}, true);
    }

    @Override
    public String[] getSaltAndHash(final String username) throws SQLException, ConnectException {
        final String query = "SELECT salt, password FROM `User` WHERE username = ?";
        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{username});

        if (resultSet.next()) {
            //Return the username's saltAndHash
            return new String[]{resultSet.getString(1), resultSet.getString(2)};
        }
        //There is no user with this username
        return new String[]{};
    }

    @Override
    public boolean setPassword(final String newPassword, final String salt, final String username) throws SQLException, ConnectException {
        final String query = "UPDATE `User` SET `password` = ?, salt = ? WHERE username = ?";

        return 1 == DatabaseHandler.setData(query, new String[] { newPassword, salt, username }, true);
    }

    @Override
    public User getUserByUsername(final String username) throws SQLException, ConnectException {
        final String query = "SELECT u.id, u.username, u.name, f.name AS `function` FROM `User` u " +
                "INNER JOIN userfunction uf ON uf.userid = u.id " +
                "INNER JOIN `function` f ON uf.functionId = f.id " +
                "WHERE username = ?";
        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{username});

        if (resultSet != null && resultSet.next()) {
            return new User(resultSet.getInt("id"), resultSet.getString("username"),
                    resultSet.getString("name"), resultSet.getString("function"),null);
        }
        return null;
    }

    @Override
    public List<String> getFunctionNames() throws SQLException, ConnectException {
        final String query = "SELECT name FROM `Function`";

        ResultSet resultSet = DatabaseHandler.getData(query, new String[]{});
        List<String> functionNames = new ArrayList<>();

        while (resultSet != null && resultSet.next()) {
            functionNames.add(resultSet.getString(1));
        }

        Collections.sort(functionNames);
        return functionNames;
    }

    @Override
    public boolean checkUsernameAvailability(final String username) throws SQLException, ConnectException {
        final String query = "SELECT id FROM `User` WHERE username = ?";
        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{username});

        //return true if username is available
        return !resultSet.next();
    }
}
