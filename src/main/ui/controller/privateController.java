package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.data.model.Chat;

import java.io.IOException;

public class PrivateController {
    @FXML private VBox vboxListedChats;

    void addChats() throws IOException {
        //Temp Chats with messages for testing purposes
        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedchat.fxml"));
            final Pane listedChatPane = fxmlLoader.load();
            final ListedchatController listedchatController = fxmlLoader.getController();

            Chat chat = new Chat("test " + i);
            chat.addMessage("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very  very very very very very very very very very very very very very long message");

            listedchatController.setListedchat(chat);
            listedchatController.setPrivateController(this);

            vboxListedChats.getChildren().add(listedChatPane);
        }
    }
}
