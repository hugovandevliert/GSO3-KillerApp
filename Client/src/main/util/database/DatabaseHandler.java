package main.util.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Properties;

public class DatabaseHandler {
    private static String server;
    private static String username;
    private static String password;
    private static Connection connection;

    private static String connectionError = "Could not connect to database.";

    public static Connection getConnection() throws ConnectException {
        FileInputStream fileInput;

        if (server == null || username == null || password == null){
            try {
                fileInput = new FileInputStream("Client/src/main/util/database/DatabaseCredentials.properties");

                Properties properties = new Properties();
                properties.load(fileInput);
                fileInput.close();

                server = properties.getProperty("server");
                username = properties.getProperty("username");
                password = properties.getProperty("password");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (connection == null){
            try {
                connection = DriverManager.getConnection(server, username, password);
            } catch (SQLException exception) {
                connection = null;
                exception.printStackTrace();
                throw new ConnectException(connectionError);
            }
        }
        return connection;
    }

    public static ResultSet getData(final String query, final String[] values) throws SQLException, ConnectException {
        PreparedStatement preparedStatement;

        final Connection connection = getConnection();
        preparedStatement = connection.prepareStatement(query);

        if (values != null && values.length > 0){
            for (int i = 0; i < values.length; i++){
                final int index = i + 1;

                fillPreparedStatementRowWithValue(preparedStatement, values[i], index);
            }
        }
        return preparedStatement.executeQuery();
    }

    public static ResultSet setData(final String query, final String[] values, final boolean isUpdateQuery) throws ConnectException, SQLException {
        ResultSet updateCount;
        PreparedStatement preparedStatement;

        final Connection connection = DatabaseHandler.getConnection();
        if(connection != null) {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } else {
            throw new ConnectException(connectionError);
        }

        if (values != null && values.length > 0){
            for (int i = 0; i < values.length; i++){
                final int index = i + 1;

                fillPreparedStatementRowWithValue(preparedStatement, values[i], index);
            }
        }

        if (isUpdateQuery) {
            preparedStatement.executeUpdate();
        } else {
            preparedStatement.executeQuery();
        }

        updateCount = preparedStatement.getGeneratedKeys();

        return updateCount;
    }

    private static void fillPreparedStatementRowWithValue(final PreparedStatement preparedStatement, final String value, final int index) throws SQLException {
        if (value.equals("null")) {
            preparedStatement.setNull(index, Types.INTEGER);
        } else if (isDouble(value)){
            preparedStatement.setDouble(index, Double.parseDouble(value));
        } else if (isInteger(value)){
            preparedStatement.setInt(index, Integer.parseInt(value));
        } else if (isDate(value)){
            preparedStatement.setTimestamp(index, Timestamp.valueOf(LocalDateTime.parse(value)));
        } else if (isBoolean(value)){
            preparedStatement.setBoolean(index, Boolean.parseBoolean(value));
        } else {
            preparedStatement.setString(index, value);
        }
    }

    private static boolean isDouble(final String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private static boolean isBoolean(final String paramValue) {
        final String value = paramValue.toLowerCase();

        return value.equals("true") || value.equals("false");
    }

    private static boolean isInteger(final String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private static boolean isDate(final String value) {
        try {
            LocalDateTime.parse(value);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }
}
