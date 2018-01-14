package model;

import main.data.model.Chat;
import main.data.model.Message;
import main.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ChatTest {
    private Chat chat;
    private ArrayList<User> users;
    private User testUser1;
    private User testUser2;
    private User testUser3;
    private ArrayList<Message> messages;
    private Message lastSentMessage;

    @BeforeEach
    void setUp() {
        chat = new Chat(1, "testChat", Chat.ChatType.GROUP);

        testUser1 = new User(1, "testUser1", "testUserOne", "tester", null);
        testUser2 = new User(2, "testUser2", "testUserTwo", "tester", null);
        testUser3 = new User(3, "testUser3", "testUserThree", "tester", null);

        users = new ArrayList<>();
        users.add(testUser1);
        users.add(testUser2);
        users.add(testUser3);

        Message testMessage1 = new Message("testMessage1", testUser1.getId(), testUser1.getName(), chat.getId(), new Time(12, 34, 56), null);
        Message testMessage2 = new Message("testMessage1", testUser2.getId(), testUser2.getName(), chat.getId(), new Time(23, 45, 56), null);
        lastSentMessage = new Message("lastSentMessage", testUser3.getId(), testUser3.getName(), chat.getId(), new Time(23, 55, 56), null);

        messages = new ArrayList<>();
        messages.add(testMessage1);
        messages.add(testMessage2);
        messages.add(lastSentMessage);
    }

    @Test
    void getId() {
        assertEquals(1, chat.getId(), "This should have been 1");
    }

    @Test
    void getName() {
        assertEquals("testChat", chat.getName(), "This should have been testChat");
    }

    @Test
    void getChatType() {
        assertEquals(Chat.ChatType.GROUP, chat.getChatType(), "This should have been GROUP");
    }

    @Test
    void getLastSentMessageAndSetLastSentMessage() {
        assertEquals(null, chat.getLastSentMessage(), "This should have been null");

        chat.setLastSentMessage(lastSentMessage);

        assertEquals(lastSentMessage, chat.getLastSentMessage());
    }

    @Test
    void getMessagesAndSetMessages() {
        assertEquals(null, chat.getMessages(), "This should have been null");

        chat.setMessages(messages);

        assertEquals(Collections.unmodifiableList(messages), chat.getMessages());
    }

    @Test
    void getUsersAndSetUsers() {
        assertEquals(null, chat.getUsers(), "This should have been null");

        chat.setUsers(users);

        assertEquals(Collections.unmodifiableList(users), chat.getUsers());
    }

    @Test
    void getUsersAndSetUsersPrivateChat() {
        Chat privateChat = new Chat(2, "privateChat", Chat.ChatType.PRIVATE);

        assertEquals(null, privateChat.getUsers(), "This should have been null");

        ArrayList<User> twoUsers = new ArrayList<>();
        twoUsers.add(testUser1);
        twoUsers.add(testUser2);

        privateChat.setUsers(twoUsers);

        assertEquals(Collections.unmodifiableList(twoUsers), privateChat.getUsers());

        ArrayList<User> threeUsers = new ArrayList<>();
        threeUsers.add(testUser1);
        threeUsers.add(testUser2);
        threeUsers.add(testUser3);

        Throwable caught = null;
        try {
            privateChat.setUsers(threeUsers);
        } catch (Throwable t) {
            caught = t;
        }
        assertNotNull(caught);

        if (caught == null) return; //Needed for SonarQube......
        assertEquals(IllegalArgumentException.class, caught.getClass(), "This should be an IllegalArgumentException");
    }

    @Test
    void getUnreadCountAndSetUnreadCount() {
        assertEquals("0", chat.getUnreadCount(), "This should have been 0");

        chat.setUnreadCount(20);

        assertEquals("20", chat.getUnreadCount(), "This should have been 20");
    }

    @Test
    void getUnreadCountAndSetUnreadCount999Plus() {
        assertEquals("0", chat.getUnreadCount(), "This should have been 0");

        chat.setUnreadCount(999);

        assertEquals("999", chat.getUnreadCount(), "This should have been 999");

        chat.setUnreadCount(1000);

        assertEquals("999+", chat.getUnreadCount(), "This should have been 999+");
    }
}