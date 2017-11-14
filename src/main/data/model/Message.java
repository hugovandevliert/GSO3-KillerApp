package main.data.model;

import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Message {
    private String text;
    private File file;
    private Time time;
    private User sender;

    public Message(String text, User sender, Time time) {
        this.text = text;
        this.file = null;
        this.sender = sender;
        this.time = time;
    }

    public Message(File file, User sender, Time time) {
        this.text = null;
        this.file = file;
        this.sender = sender;
        this.time = time;
    }

    public String getText() {
        if (file != null) {
            return file.getName();
        } else {
            return text;
        }
    }

    public File getFile() {
        return file;
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
