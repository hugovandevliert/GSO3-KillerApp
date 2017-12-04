package main.data.model;

import java.io.File;

public class MessageFile {
    private final File file;

    public MessageFile(File file) {
        this.file = file;
    }

    File getFile() {
        return this.file;
    }
}
