package main.data.model;

import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Message {
    private String text;
    private MessageFile file;
    private Time time;
    private User sender;

    public Message(String text, User sender, Time time) {
        this.text = text;
        this.file = null;
        this.sender = sender;
        this.time = time;
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

    public User getSender() {
        return sender;
    }

    public String getSenderName() {
        return sender.getName();
    }
}
