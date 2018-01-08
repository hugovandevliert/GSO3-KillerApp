package main.data.model;

import java.io.File;
import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Message implements Serializable {
    private String text;
    private MessageFile file;
    private Time time;
    private int chatId;
    private int fileId;
    private int senderId;
    private String senderName;

    public Message(String text, int chatId, int senderId, Time time) {
        this.text = text;
        this.chatId = chatId;
        this.senderId = senderId;
        this.time = time;
        this.file = null;
    }

    public void addFile(MessageFile file) {
        this.file = file;
    }

    public String getText() {
        if (file != null) return file.getFile().getName();
        else return text;
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

    public int getChatId() { return chatId; }

    public int getFileId() { return fileId; }

    public String getSenderName() {
        return senderName;
    }
}
