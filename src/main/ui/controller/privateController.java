package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.data.model.Chat;
import main.data.model.Message;
import main.data.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PrivateController extends BaseController {
    @FXML protected Pane paneContent;
    @FXML private VBox vboxListedChats;

    private Pane parentPane;

    void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    void loadChats() throws IOException {
        //Temp Chats with messages for testing purposes
        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedChat.fxml"));
            final Pane listedChatPane = fxmlLoader.load();
            final ListedChatController listedChatController = fxmlLoader.getController();

            Chat chat = new Chat(i,"Timo", Chat.ChatType.PRIVATE);

            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message",
                    applicationManager.getCurrentUser()));

            chat.addMessage(new Message("I have sent you a file!", new File("test.txt"), new User(0, "testUser2", "Simone",
                    "SuperCEO", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>())));

            chat.addMessage(new Message("And another one test message", applicationManager.getCurrentUser()));

            chat.addMessage(new Message("And a laaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarge test message",
                    new User(0, "testUser2", "Simone", "SuperCEO", null, new ArrayList<>(),
                            new ArrayList<>(), new ArrayList<>())));

            listedChatController.setParentPane(parentPane);
            listedChatController.setListedChat(chat);

            vboxListedChats.getChildren().add(listedChatPane);
        }
    }
}
