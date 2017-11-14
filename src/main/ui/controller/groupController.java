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
import java.sql.Time;
import java.util.ArrayList;

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
                    "very very very very very very very very very very very very very very very very very very very long message", new User(0, "testUser2", "Timo",
                    "SuperCEO", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), new Time(20, 45,10)));
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message",
                    applicationManager.getCurrentUser(), new Time(20, 45,50)));

            chat.addMessage(new Message(new File("main/util/test/test.txt"), new User(0, "testUser2", "Ronald",
                    "SuperCEO", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), new Time(20, 45,10)));

            chat.addMessage(new Message("And another one test message", new User(0, "testUser2", "JuulVergeetTas",
                    "SuperCEO", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), new Time(20, 45,10)));

            listedChatController.setParentPane(parentPane);
            listedChatController.setListedChat(chat);

            vboxListedChats.getChildren().add(listedChatPane);
        }
    }
}
