package model;

import main.data.model.Chat;
import main.data.model.Message;
import main.data.model.MessageFile;
import main.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {
    private User user;
    private Chat testChat;
    private Message message;

    @BeforeEach
    void setUp() {
        user = new User(0, "testUser1", "testUser", "tester", null);
        testChat = new Chat(1, "testChat1", Chat.ChatType.PRIVATE);
        message = new Message("testMessage", user.getId(), user.getName(), testChat.getId(), new Time(12, 34, 56), 2);
    }

    @Test
    void addFileAndGetFile() {
        File testFile = new File("\\UnitTests\\util\\testDocument.txt");
        MessageFile messageFile = new MessageFile(testFile,"testDocument", "txt");
        message.addFile(messageFile);

        assertEquals(message.getFile(), testFile);
    }

    @Test
    void getText() {
        assertEquals("testMessage", message.getText(), "This should have been testMessage");
    }

    @Test
    void getChatId() {
        assertEquals(1, message.getChatId(), "This should have been 1");
    }

    @Test
    void getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        assertEquals(sdf.format(new Time(12, 34, 56)), message.getTime(), "This should have been 12:34");
    }

    @Test
    void getSenderId() {
        assertEquals(0, message.getSenderId(), "This should have been 0");
    }

    @Test
    void getFileId() {
        assertEquals(java.util.Optional.ofNullable(2), java.util.Optional.ofNullable(message.getFileId()), "This should have been 2");
    }

    @Test
    void getSenderName() {
        assertEquals("testUser", message.getSenderName(), "This should have been testUser");
    }
}