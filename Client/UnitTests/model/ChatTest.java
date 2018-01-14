package model;

import main.data.model.Chat;
import main.data.model.Message;
import main.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ChatTest {
    private Chat chat;
    private ArrayList<Message> messages;
    private Message message;
    private ArrayList<User> users;

    @BeforeEach
    void setUp() {
        chat = new Chat(1, "testChat", Chat.ChatType.GROUP);
    }

    @Test
    void getId() {
    }

    @Test
    void getName() {
    }

    @Test
    void getChatType() {
    }

    @Test
    void getLastSentMessage() {
    }

    @Test
    void setLastSentMessage() {
    }

    @Test
    void getMessages() {
    }

    @Test
    void setMessages() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void setUsers() {
    }

    @Test
    void getUnreadCount() {
    }

    @Test
    void setUnreadCount() {
    }
}