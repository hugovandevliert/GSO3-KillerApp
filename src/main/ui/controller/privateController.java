package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.data.model.Chat;

import java.io.IOException;

public class PrivateController {
    @FXML protected Pane paneContent;
    @FXML private VBox vboxListedChats;

    private BaseController baseController;

    void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

    void loadChats() throws IOException {
        //Temp Chats with messages for testing purposes
        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedchat.fxml"));
            final Pane listedChatPane = fxmlLoader.load();
            final ListedchatController listedchatController = fxmlLoader.getController();

            Chat chat = new Chat(i,"test " + i, Chat.ChatType.PRIVATE);
            chat.addMessage("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message");

            listedchatController.setListedchat(chat);
            listedchatController.setBaseController(baseController);

            vboxListedChats.getChildren().add(listedChatPane);
        }
    }
}
