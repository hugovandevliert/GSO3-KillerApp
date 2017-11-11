package main.data.model;

import java.io.File;
import java.sql.Time;

public class Message {
    private String text;
    private File file;
    private Time time;

    public Message(String text) {
        this.text = text;
    }

    public Message(File file) {
        this.file = file;
    }

    public Message(String text, File file) {
        this.text = text;
        this.file = file;
    }

    public String getText() {
        return text;
    }
}
