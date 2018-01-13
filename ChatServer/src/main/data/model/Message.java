package main.data.model;

import java.io.File;
import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Message implements Serializable {
    private transient MessageFile file;
    private String text;
    private Time time;
    private int senderId;
    private int chatId;
    private Integer fileId;
    private String senderName;

    /**
     * initialize class
     * @param text the text of this message
     * @param senderId the id of the sender of this message
     * @param senderName the name of the sender of this message
     * @param chatId the id of the chat this message is sent in
     * @param time the time this message is sent at
     * @param fileId the id of the file sent within this message (can be null)
     */
    public Message(final String text, final int senderId, final String senderName, final int chatId, final Time time, final Integer fileId) {
        this.text = text;
        this.senderId = senderId;
        this.senderName = senderName;
        this.chatId = chatId;
        this.time = time;
        if (fileId == null || fileId == 0) {
            this.fileId = null;
        } else {
            this.fileId = fileId;
        }
        this.file = null;
    }

    /**
     * add file to this message
     * @param file messageFile to add to this message
     */
    public void addFile(final MessageFile file) {
        this.file = file;
    }

    /**
     * @return text in this message
     */
    public String getText() {
        if (file != null) return file.getFile().getName();
        else return text;
    }

    /**
     * @return id of chat this message is sent in
     */
    public int getChatId() {
        return chatId;
    }

    /**
     * @return file added to this message
     */
    public File getFile() {
        if (file == null) {
            return null;
        }
        else {
            return file.getFile();
        }
    }

    /**
     * @return time this message was sent at
     */
    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(time);
    }

    /**
     * @return id of the sender of this message
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * @return id of the file added to this message (can be null)
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * @return name of the sender of this message
     */
    public String getSenderName() {
        return senderName;
    }
}
