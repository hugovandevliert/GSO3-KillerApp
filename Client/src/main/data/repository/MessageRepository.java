package main.data.repository;

import main.data.context.FileMySqlContext;
import main.data.context.IFileContext;
import main.data.context.IMessageContext;
import main.data.context.MessageMySqlContext;
import main.data.model.Message;
import main.data.model.MessageFile;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;

public class MessageRepository {
    private IMessageContext messageContext = new MessageMySqlContext();
    private IFileContext fileContext = new FileMySqlContext();

    public List<Message> getMessagesByChatId(final int chatId) throws SQLException, ConnectException {
        return messageContext.getMessagesByChatId(chatId);
    }

    public Message getLastMessageByChatId(final int chatId) throws SQLException, ConnectException {
        return messageContext.getLastMessageByChatId(chatId);
    }

    public Integer addFile(final MessageFile messsageFile) throws SQLException, ConnectException {
        return fileContext.addFile(messsageFile);
    }

    public byte[] getFile(final int fileId) throws SQLException, ConnectException {
        return fileContext.getFile(fileId);
    }

    public String getFileName(final int fileId) throws SQLException, ConnectException {
        return fileContext.getFileName(fileId);
    }

    public String getFileExtension(final int fileId) throws SQLException, ConnectException {
        return fileContext.getFileExtension(fileId);
    }
}
