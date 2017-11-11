package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.data.model.Chat;

import java.io.IOException;

public class ListedchatController {
    @FXML private Label lblName;
    @FXML private Label lblLastMessage;
    @FXML private Label lblMessageCount;

    private Chat chat;
    private BaseController baseController;

    void setBaseController(final BaseController baseController) {
        this.baseController = baseController;
    }

    void setListedchat(final Chat chat) {
        this.chat = chat;
        lblName.setText(chat.getName());
        lblLastMessage.setText(chat.getLastMessage());
        lblMessageCount.setText(String.valueOf(chat.getUnreadMessagesCount()));
    }

    public void openChat() throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/chat.fxml"));
        final Pane newContentPane = fxmlLoader.load();
        final ChatController chatController = fxmlLoader.getController();
        chatController.setBaseController(baseController);
        chatController.loadChat(chat);

        baseController.paneContent.getChildren().clear();
        baseController.paneContent.getChildren().add(newContentPane);
    }
}
