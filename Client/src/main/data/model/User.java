package main.data.model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private final int id;
    private String username, name, function;
    private Image photo;

    private List<Chat> privateChats, groupChats, memos;

    public User(final int id, final String username, final String name, final String function, final Image photo) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.function = function;
        this.photo = photo;
        this.privateChats = null;
        this.groupChats = null;
        this.memos = null;
    }

    public void addPrivateChat(final Chat chat) {
        privateChats.add(chat);
    }

    public void addGroupChat(final Chat chat) {
        groupChats.add(chat);
    }

    public void addMemo(final Chat chat) {
        memos.add(chat);
    }

    public void setPrivateChats(List<Chat> privateChats) {
        this.privateChats = privateChats;
    }

    public void setGroupChats(List<Chat> groupChats) {
        this.groupChats = groupChats;
    }

    public void setMemos(List<Chat> memos) {
        this.memos = memos;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getFunction() {
        return function;
    }

    public Image getPhoto() {
        return photo;
    }

    public List<Chat> getPrivateChats() {
        return Collections.unmodifiableList(privateChats);
    }

    public List<Chat> getGroupChats() {
        return Collections.unmodifiableList(groupChats);
    }

    public List<Chat> getMemos() {
        return Collections.unmodifiableList(memos);
    }
}
