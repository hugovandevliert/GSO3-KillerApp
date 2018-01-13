package main.data.context;

import main.data.model.MessageFile;
import main.util.database.DatabaseHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileMySqlContext implements IFileContext {
    @Override
    public Integer addFile(final MessageFile messageFile) throws SQLException, ConnectException {
        final String query = "INSERT INTO file (file, name, extension) VALUES (?, ?, ?)";
        ResultSet resultSet = null;
        try {
            resultSet = DatabaseHandler.setData(query, messageFile, messageFile.getName(), messageFile.getExtension());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            return null;
        }
    }

    @Override
    public byte[] getFile(final int fileId) throws SQLException, ConnectException {
        final String query = "SELECT file FROM file " +
                "WHERE id = ?";
        ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(fileId)});

        if (resultSet.next()) {
            return resultSet.getBytes("file");
        } else {
            return null;
        }
    }

    @Override
    public String getFileName(int fileId) throws SQLException, ConnectException {
        final String query = "SELECT name FROM file " +
                "WHERE id = ?";
        ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(fileId)});

        if (resultSet.next()) {
            return resultSet.getString("name");
        } else {
            return null;
        }
    }

    @Override
    public String getFileExtension(int fileId) throws SQLException, ConnectException {
        final String query = "SELECT extension FROM file " +
                "WHERE id = ?";
        ResultSet resultSet = DatabaseHandler.getData(query, new String[]{String.valueOf(fileId)});

        if (resultSet.next()) {
            return resultSet.getString("extension");
        } else {
            return null;
        }
    }
}
