package main.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.data.model.Chat;

public class ListedchatController {
    @FXML private Label lblName;
    @FXML private Label lblLastMessage;
    @FXML private Label lblMessageCount;

    private PrivateController privateController;

    public void setPrivateController(PrivateController PrivateController) {
        this.privateController = PrivateController;
    }

    public void setListedchat(final Chat chat) {
        lblName.setText(chat.getName());
        lblLastMessage.setText(chat.getLastMessage());
        lblMessageCount.setText(String.valueOf(chat.getUnreadMessagesCount()));
    }
}
