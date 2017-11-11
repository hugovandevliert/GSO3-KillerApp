package main.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.data.model.Chat;
import main.data.model.Message;

public class ChatController {
    @FXML private Label lblChatName;
    @FXML private VBox vboxListedMessages;

    public void loadChat(final Chat chat) {
        lblChatName.setText(chat.getName());

        if (!chat.getMessages().isEmpty()) {
            for (Message message : chat.getMessages()) {
                //Create message
            }
        }
    }
}
