package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.data.model.Chat;
import main.data.model.Message;
import main.data.model.MessageFile;
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

    //Temp Users for testing purposes
    ArrayList<User> users;
    void loadChats() throws IOException {
        //Temp Chats with messages for testing purposes
        users = new ArrayList<User>();
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
                    "very very very very very very very very very very very very very very very very very very very long message", i,
                    applicationManager.getCurrentUser().getId(), new Time(1, 1, 1)));
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message", i,
                    applicationManager.getCurrentUser().getId(), new Time(1, 1, 1)));
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message", i,
                    new User(0, "testUser2", "Simone", "SuperCEO", null, new ArrayList<>(),
                            new ArrayList<>(), new ArrayList<>()).getId(), new Time(20, 45, 10)));
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message", i,
                    applicationManager.getCurrentUser().getId(), new Time(20, 45, 20)));
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message", i,
                    applicationManager.getCurrentUser().getId(), new Time(1, 1, 1)));


            Message tempMessage = new Message("test.txt", i, new User(0, "testUser2", "Simone",
                    "SuperCEO", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()).getId(), new Time(1, 1, 10));
            tempMessage.addFile(new MessageFile(new File("main/util/test/test.txt")));
            chat.addMessage(tempMessage);

            chat.addMessage(new Message("And another one test message", i, applicationManager.getCurrentUser().getId(), new Time(20, 45, 10)));

            chat.addMessage(new Message("And a laaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaarge test message", i,
                    new User(0, "testUser2", "Simone", "SuperCEO", null, new ArrayList<>(),
                            new ArrayList<>(), new ArrayList<>()).getId(), new Time(20, 45, 10)));

            listedChatController.setParentPane(parentPane);
            listedChatController.setListedChat(chat);

            vboxListedChats.getChildren().add(listedChatPane);
        }


        //testing alerts
        showAlert(new Chat(999, "Simone", Chat.ChatType.PRIVATE, new ArrayList<>(), users), new Message("test message", 0, new User(0, "testUser2", "Simone", "SuperCEO", null, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>()).getId(), new Time(20, 45,10)), parentPane);
    }

    public void createChat() throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/createPrivateChat.fxml"));
        final Pane newPane = fxmlLoader.load();
        final CreatePrivateChatController createPrivateChatController = fxmlLoader.getController();
        createPrivateChatController.setParentPane(parentPane);
        createPrivateChatController.loadUsers(users);

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newPane);
    }
}
