package model;

import main.data.model.MessageFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageFileTest {
    private MessageFile messageFile;
    private File file;

    @BeforeEach
    void setUp() {
        file = new File("\\UnitTests\\util\\testDocument.txt");
        messageFile = new MessageFile(file, file.getName(), getFileExtension(file));
    }

    @Test
    void getFile() {
        assertEquals(file, messageFile.getFile());
    }

    @Test
    void getName() {
        assertEquals(file.getName(), messageFile.getName());
    }

    @Test
    void getExtension() {
        assertEquals(getFileExtension(file), messageFile.getExtension());
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf('.') != -1 && fileName.lastIndexOf('.') != 0) {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        } else {
            return "";
        }
    }
}