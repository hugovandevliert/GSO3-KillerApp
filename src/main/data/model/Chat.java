package main.data.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chat {

    public enum ChatType {
        PRIVATE,
        GROUP,
        MEMO
    }

    private int id;
    private String name;
    private ChatType chatType;
    private ArrayList<Message> messages;
    private ArrayList<User> users;

    //temp constructor for testing purposes
    public Chat(final int id, final String name, final ChatType chatType) {
        this.id = id;
        this.name = name;
        this.chatType = chatType;
        messages = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public String getLastMessage() {
        return messages.get(0).getText();
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
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
