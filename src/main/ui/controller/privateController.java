package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.data.model.Chat;
import main.data.model.Message;
import main.data.model.User;

import java.io.IOException;
import java.util.ArrayList;

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

            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message", baseController.applicationManager.getCurrentUser()));

            chat.addMessage(new Message("A smaller test message", new User(0, "testUser2", "Simone", "SuperCEO", null, new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>())));

            chat.addMessage(new Message("And another one test message", baseController.applicationManager.getCurrentUser()));

            chat.addMessage(new Message("And a laaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarge test message", new User(0, "testUser2", "Simone", "SuperCEO", null, new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>())));

            listedchatController.setListedchat(chat);
            listedchatController.setBaseController(baseController);

            vboxListedChats.getChildren().add(listedChatPane);
        }
    }
}
