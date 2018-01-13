package main.data.model;

import java.io.File;

public class MessageFile {
    private final File file;
    private final String name;
    private final String extension;

    /**
     * initialize class
     *
     * @param file      the file in this messageFile
     * @param name      the name of the file in this messageFile
     * @param extension the extension of the file in this messageFile
     */
    public MessageFile(final File file, final String name, final String extension) {
        this.file = file;
        this.name = name;
        this.extension = extension;
    }

    /**
     * @return file in this messageFile
     */
    public File getFile() {
        return this.file;
    }

    /**
     * @return name of the file in this messageFile
     */
    public String getName() {
        return name;
    }

    /**
     * @return extension of the file in this messageFile
     */
    public String getExtension() {
        return extension;
    }
}
