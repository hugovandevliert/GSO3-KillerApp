package main.data.model;

import java.util.Collections;
import java.util.List;

public class Chat {
    private int id;
    private String name;
    private ChatType chatType;
    private List<Message> messages;
    private List<User> users;
    private Message lastSentMessage;
    private int unreadCount;

    /**
     * initialize class
     *
     * @param id       the id of the chat
     * @param name     the name of the chat
     * @param chatType the type of the chat
     */
    public Chat(final int id, final String name, final ChatType chatType) {
        this.id = id;
        this.name = name;
        this.chatType = chatType;
        this.messages = null;
        this.users = null;
        unreadCount = 0;
    }

    /**
     * @return the id of this chat
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name of this chat
     */
    public String getName() {
        return name;
    }

    /**
     * @return the type of this chat
     */
    public ChatType getChatType() {
        return chatType;
    }

    /**
     * @return the last sent message in this chat
     */
    public Message getLastSentMessage() {
        return lastSentMessage;
    }

    /**
     * set the last sent message
     *
     * @param lastSentMessage the last sent message of this chat
     */
    public void setLastSentMessage(final Message lastSentMessage) {
        this.lastSentMessage = lastSentMessage;
    }

    /**
     * @return all messages in this chat
     */
    public List<Message> getMessages() {
        if (messages == null) {
            return null;
        } else {
            return Collections.unmodifiableList(messages);
        }
    }

    /**
     * set the messages of the chat
     *
     * @param messages list of messages in the chat
     */
    public void setMessages(final List<Message> messages) {
        this.messages = messages;
    }

    /**
     * @return all users in this chat
     */
    public List<User> getUsers() {
        if (users == null) {
            return null;
        } else {
            return Collections.unmodifiableList(users);
        }
    }

    /**
     * set the users of the chat
     *
     * @param users list of participants in the chat
     * @throws IllegalArgumentException if this chat type is private and users is > 2
     */
    public void setUsers(final List<User> users) {
        if (chatType == ChatType.PRIVATE && users.size() > 2) {
            throw new IllegalArgumentException("users can not be > 2 if chat type is private");
        } else {
            this.users = users;
        }
    }

    /**
     * @return the amount of unread messages (will return "999+" if over 999)
     */
    public String getUnreadCount() {
        if (unreadCount > 999) {
            return "999+";
        } else {
            return String.valueOf(unreadCount);
        }
    }

    /**
     * set the unread counter of this chat
     *
     * @param unreadCount the number of unread messages in this chat
     */
    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public enum ChatType {
        PRIVATE,
        GROUP,
        MEMO
    }
}
