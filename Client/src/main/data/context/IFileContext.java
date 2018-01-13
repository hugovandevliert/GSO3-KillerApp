package main.data.context;

import main.data.model.MessageFile;

import java.net.ConnectException;
import java.sql.SQLException;

public interface IFileContext {
    Integer addFile(final MessageFile messageFile) throws SQLException, ConnectException;
    byte[] getFile(final int fileId) throws SQLException, ConnectException;
    String getFileName(final int fileId) throws SQLException, ConnectException;
    String getFileExtension(final int fileId) throws SQLException, ConnectException;
}
