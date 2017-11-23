package main.ui.controller;

import javafx.event.ActionEvent;
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

public class PrivatePageController extends BaseController {
    @FXML private VBox vboxListedChats;

    private Pane parentPane;

    void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    void loadChats() throws IOException {
        //Temp Chats with messages for testing purposes
        ArrayList<User> users = new ArrayList<User>();
        users.add(applicationManager.getCurrentUser());
        users.add(new User(0, "testUser2", "Simone",
                "SuperCEO", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedChat.fxml"));
            final Pane listedChatPane = fxmlLoader.load();
            final ListedChatController listedChatController = fxmlLoader.getController();


            Chat chat = new Chat(i, "Simone", Chat.ChatType.PRIVATE, new ArrayList<>(), users);

            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message",
                    applicationManager.getCurrentUser(), new Time(1, 1, 1)));
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message",
                    applicationManager.getCurrentUser(), new Time(1, 1, 20)));
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message",
                    new User(0, "testUser2", "Simone", "SuperCEO", null, new ArrayList<>(),
                            new ArrayList<>(), new ArrayList<>()), new Time(20, 45, 10)));
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message",
                    applicationManager.getCurrentUser(), new Time(20, 45, 20)));
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message",
                    applicationManager.getCurrentUser(), new Time(1, 1, 1)));

            chat.addMessage(new Message(new File("main/util/test/test.txt"), new User(0, "testUser2", "Simone",
                    "SuperCEO", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), new Time(1, 1, 10)));

            chat.addMessage(new Message("And another one test message", applicationManager.getCurrentUser(), new Time(20, 45, 10)));

            chat.addMessage(new Message("And a laaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarge test message",
                    new User(0, "testUser2", "Simone", "SuperCEO", null, new ArrayList<>(),
                            new ArrayList<>(), new ArrayList<>()), new Time(20, 45, 10)));

            listedChatController.setParentPane(parentPane);
            listedChatController.setListedChat(chat);

            vboxListedChats.getChildren().add(listedChatPane);
        }


        //testing alerts
        showAlert(new Chat(999, "Simone", Chat.ChatType.PRIVATE, new ArrayList<>(), users), new Message("test message", new User(0, "testUser2", "Simone", "SuperCEO", null, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>()), new Time(20, 45,10)), parentPane.getParent(), parentPane);
    }

    public void createChat() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/createPrivateChat.fxml"));
        Pane newPane = fxmlLoader.load();
        final CreatePrivateChatController createPrivateChatController = fxmlLoader.getController();
        createPrivateChatController.setParentPane(parentPane);

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newPane);
    }
}
