package main.data.model;

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

    public Chat(final int id, final String name, final ChatType chatType, final ArrayList<Message> messages, final ArrayList<User> users) {
        this.id = id;
        this.name = name;
        this.chatType = chatType;
        this.messages = messages;
        this.users = users;
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

    public Message getLastMessage() {
        return messages.get(0);
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public int getUnreadMessagesCount() {
        return 999;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }
}
