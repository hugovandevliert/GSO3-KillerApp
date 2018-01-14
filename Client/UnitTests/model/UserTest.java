package model;

import main.data.model.Chat;
import main.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;
    private Chat testChat1;
    private Chat testChat2;

    @BeforeEach
    void setUp() {
        user = new User(1, "testUser1", "testUser", "tester", null);

        testChat1 = new Chat(1, "testChat1", Chat.ChatType.PRIVATE);
        testChat2 = new Chat(2, "testChat2", Chat.ChatType.GROUP);
    }

    @Test
    void getId() {
        assertEquals(1, user.getId(), "The userId should have been 1");
    }

    @Test
    void getUsername() {
        assertEquals("testUser1", user.getUsername(), "The username should have been testUser1");
    }

    @Test
    void getName() {
        assertEquals("testUser", user.getName(), "The username should have been testUser");
    }

    @Test
    void getFunction() {
        assertEquals("tester", user.getFunction(), "The function should have been tester");
    }

    @Test
    void getPhoto() {
        assertEquals(null, user.getPhoto(), "The photo should have been null");
    }

    @Test
    void getPrivateChatsAndSetPrivateChats() {
        assertEquals(null, user.getPrivateChats(),"This should have been null");

        ArrayList<Chat> testUserChats = new ArrayList<>();
        testUserChats.add(testChat1);
        user.setPrivateChats(testUserChats);

        assertEquals(Collections.unmodifiableList(testUserChats).get(0).getId(), user.getPrivateChats().get(0).getId());
        assertEquals(Collections.unmodifiableList(testUserChats).get(0).getChatType(), user.getPrivateChats().get(0).getChatType());
        assertEquals(Collections.unmodifiableList(testUserChats).get(0).getName(), user.getPrivateChats().get(0).getName());
    }

    @Test
    void getGroupChatsAndSetGroupChats() {
        assertEquals(null, user.getGroupChats(),"This should have been null");

        ArrayList<Chat> testUserChats = new ArrayList<>();
        testUserChats.add(testChat1);
        testUserChats.add(testChat2);
        user.setGroupChats(testUserChats);

        assertEquals(Collections.unmodifiableList(testUserChats).get(1).getId(), user.getGroupChats().get(1).getId());
        assertEquals(Collections.unmodifiableList(testUserChats).get(1).getChatType(), user.getGroupChats().get(1).getChatType());
        assertEquals(Collections.unmodifiableList(testUserChats).get(1).getName(), user.getGroupChats().get(1).getName());
    }


    @Test
    void getMemosAndSetMemos() {
        assertEquals(null, user.getMemos(),"This should have been null");

        ArrayList<Chat> testUserChats = new ArrayList<>();
        testUserChats.add(testChat1);
        testUserChats.add(testChat2);
        user.setMemos(testUserChats);

        assertEquals(Collections.unmodifiableList(testUserChats).get(0).getId(), user.getMemos().get(0).getId());
        assertEquals(Collections.unmodifiableList(testUserChats).get(0).getChatType(), user.getMemos().get(0).getChatType());
        assertEquals(Collections.unmodifiableList(testUserChats).get(0).getName(), user.getMemos().get(0).getName());
    }
}