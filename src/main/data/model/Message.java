package main.data.model;

import java.io.File;
import java.sql.Time;

public class Message {
    private String text;
    private File file;
    private Time time;
    private User sender;

    //Temp constructor for developing
    Message(String text) {
        this.text = text;
        this.file = null;
    }

    public Message(String text, User sender) {
        this.text = text;
        this.file = null;
        this.sender = sender;
    }

    public Message(File file, User sender) {
        this.text = null;
        this.file = file;
        this.sender = sender;
    }

    public Message(String text, File file, User sender) {
        this.text = text;
        this.file = file;
        this.sender = sender;
    }

    public String getText() {
        if (file != null) {
            text += "\n" + file.getName();
        }
        return text;
    }

    public File getFile() {
        return file;
    }

    public Time getTime() {
        return time;
    }

    public User getSender() {
        return sender;
    }

    public String getSenderName() {
        return sender.getName();
    }
}
