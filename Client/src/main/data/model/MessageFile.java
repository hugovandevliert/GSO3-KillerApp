package main.data.model;

import java.io.File;

public class MessageFile {
    private final File file;
    private final String name;
    private final String extension;

    public MessageFile(final File file, final String name, final String extension) {
        this.file = file;
        this.name = name;
        this.extension = extension;
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }
}
