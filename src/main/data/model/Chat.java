package main.data.model;

import java.io.File;
import java.util.ArrayList;

public class Chat {
    private String name;
    private ArrayList<Message> messages;

    public Chat(String name) {
        this.name = name;
        messages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return messages.get(0).getText();
    }

    public int getUnreadMessagesCount() {
        return 999;
    }

    public void addMessage(String text) {
        messages.add(new Message(text));
    }

    public void addMessage(File file) {

    }
}
