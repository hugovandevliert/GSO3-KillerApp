package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.data.model.Chat;
import main.data.model.Message;

import java.io.IOException;

public class GroupController extends BaseController {
    @FXML protected Pane paneContent;
    @FXML private VBox vboxListedChats;

    private Pane parentPane;

    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    void loadChats() throws IOException {
        //Temp Chats with messages for testing purposes
        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedChat.fxml"));
            final Pane listedChatPane = fxmlLoader.load();
            final ListedChatController listedChatController = fxmlLoader.getController();

            Chat chat = new Chat(i,"group " + i, Chat.ChatType.GROUP);
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very  very very very very very very very very very very very very very long message", applicationManager.getCurrentUser()));

            listedChatController.setParentPane(parentPane);
            listedChatController.setListedChat(chat);

            vboxListedChats.getChildren().add(listedChatPane);
        }
    }
}
