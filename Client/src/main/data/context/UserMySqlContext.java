package main.data.context;

import main.data.model.User;
import main.util.database.DatabaseHandler;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMySqlContext implements IUserContext {
    @Override
    public boolean registerUser(final String username, final String password, final String salt, final String name) throws SQLException, ConnectException {
        final String query = "INSERT INTO `User` (`Username`, `Password`, `Salt`, `Name`) VALUES (?, ?, ?, ?)";

        return 1 == DatabaseHandler.setData(query, new String[]{ username, password, salt, name }, true);
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
//        final String query = "SELECT id, username, name FROM `User` WHERE username = ?";
//        final ResultSet resultSet = DatabaseHandler.getData(query, new String[]{username});
//
//        if (resultSet != null && resultSet.next()) {
//            return new User
//                    (
//                            resultSet.getInt("id"),
//                            resultSet.getString("username"),
//                            resultSet.getString("name"),
//
//                    );
//        }
        return null;
    }
}

