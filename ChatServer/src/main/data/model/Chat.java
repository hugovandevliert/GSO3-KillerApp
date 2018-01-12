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
    private Message lastSentMessage;
    private int unreadCount;

    public Chat(final int id, final String name, final ChatType chatType) {
        this.id = id;
        this.name = name;
        this.chatType = chatType;
        this.messages = null;
        this.users = null;
        unreadCount = 0;
    }

    public void setMessages(final ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public void setLastSentMessage(final Message lastSentMessage) {
        this.lastSentMessage = lastSentMessage;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
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

    public Message getLastSentMessage() {
        return lastSentMessage;
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public String getUnreadCount() {
        if (unreadCount > 999) {
            return "999+";
        } else {
            return String.valueOf(unreadCount);
        }
    }
}
