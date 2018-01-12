package main.data.model;

import java.io.File;
import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Message implements Serializable {
    private String text;
    private MessageFile file;
    private Time time;
    private int senderId;
    private int chatId;
    private Integer fileId;
    private String senderName;

    public Message(final String text, final int senderId, final String senderName, final int chatId, final Time time, final Integer fileId) {
        this.text = text;
        this.senderId = senderId;
        this.senderName = senderName;
        this.chatId = chatId;
        this.time = time;
        this.fileId = fileId;
        this.file = null;
    }

    public void addFile(final MessageFile file) {
        this.file = file;
    }

    public String getText() {
        if (file != null) return file.getFile().getName();
        else return text;
    }

    public int getChatId() {
        return chatId;
    }

    public File getFile() {
        if (file == null) return null;
        else return file.getFile();
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(time);
    }

    public int getSenderId() {
        return senderId;
    }

    public int getFileId() { return fileId; }

    public String getSenderName() {
        return senderName;
    }
}
