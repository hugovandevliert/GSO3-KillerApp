package main.data.model;

import javafx.scene.image.Image;

import java.util.Collections;
import java.util.List;

public class User {
    private final int id;
    private String username;
    private String name;
    private String function;
    private Image photo;

    private List<Chat> privateChats;
    private List<Chat> groupChats;
    private List<Chat> memos;

    /**
     * initialize class
     * @param id the id of this user
     * @param username the username of this user
     * @param name the name of this user
     * @param function the function of this user
     * @param photo the photo of this user
     */
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

    /**
     * set the private chats this user participates in
     * @param privateChats private chats this user participates in
     */
    public void setPrivateChats(List<Chat> privateChats) {
        this.privateChats = privateChats;
    }

    /**
     * set the group chats this user participates in
     * @param groupChats group chats this user participates in
     */
    public void setGroupChats(List<Chat> groupChats) {
        this.groupChats = groupChats;
    }

    /**
     * set the memos this user has received or sent
     * @param memos the memos of this user
     */
    public void setMemos(List<Chat> memos) {
        this.memos = memos;
    }

    /**
     * @return id of this user
     */
    public int getId() {
        return id;
    }

    /**
     * @return username of this user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return name of this user
     */
    public String getName() {
        return name;
    }

    /**
     * @return function of this user
     */
    public String getFunction() {
        return function;
    }

    /**
     * @return photo of this user (can be null)
     */
    public Image getPhoto() {
        return photo;
    }

    /**
     * @return private chats this user is participating in
     */
    public List<Chat> getPrivateChats() {
        return Collections.unmodifiableList(privateChats);
    }

    /**
     * @return group chats this user is participating in
     */
    public List<Chat> getGroupChats() {
        return Collections.unmodifiableList(groupChats);
    }

    /**
     * @return memos this user has sent or received
     */
    public List<Chat> getMemos() {
        return Collections.unmodifiableList(memos);
    }
}
