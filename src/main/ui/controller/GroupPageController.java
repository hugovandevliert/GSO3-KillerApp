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

public class GroupPageController extends BaseController {
    private Pane parentPane;

    @FXML private VBox vboxListedChats;

    public void setParentPane(final Pane parentPane) {
        this.parentPane = parentPane;
    }

    //temp users for testing purposes
    ArrayList<User> users;
    void loadChats() throws IOException {
        //Temp Chats with messages for testing purposes
        for (int i = 0; i < 10; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedChat.fxml"));
            final Pane listedChatPane = fxmlLoader.load();
            final ListedChatController listedChatController = fxmlLoader.getController();

            users = new ArrayList<User>();
            users.add(applicationManager.getCurrentUser());
            users.add(new User(0, "testUser2", "Simone",
                    "SuperCEO", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
            users.add(new User(0, "testUser3", "Timo",
                    "Bierproever", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
            users.add(new User(0, "testUser4", "JuulVergeetTas",
                    "Paupert", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

            Chat chat = new Chat(i,"group " + i, Chat.ChatType.GROUP, new ArrayList<>(), users);

            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message", i, new User(0, "testUser3", "Timo",
                    "Bierproever", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()).getId(), new Time(20, 45,10)));

            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message", i,
                    applicationManager.getCurrentUser().getId(), new Time(20, 45,50)));

            Message tempMessage = new Message("test.txt", i, new User(0, "testUser2", "Ronald",
                    "SuperCEO", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()).getId(), new Time(20, 45,10));
            tempMessage.addFile(new MessageFile(new File("main/util/test/test.txt")));
            chat.addMessage(tempMessage);

            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message", i,
                    new User(0, "testUser2", "Simone", "SuperCEO", null, new ArrayList<>(),
                            new ArrayList<>(), new ArrayList<>()).getId(), new Time(20, 45,10)));
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message", i,
                    applicationManager.getCurrentUser().getId(), new Time(20, 45,20)));
            chat.addMessage(new Message("This is a very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very very " +
                    "very very very very very very very very very very very very very very very very very very very long message", i,
                    applicationManager.getCurrentUser().getId(), new Time(1, 1,1)));

            chat.addMessage(new Message("And another one test message", i, new User(0, "testUser2", "JuulVergeetTas",
                    "SuperCEO", null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()).getId(), new Time(20, 45,10)));

            listedChatController.setParentPane(parentPane);
            listedChatController.setListedChat(chat);

            vboxListedChats.getChildren().add(listedChatPane);
        }
    }

    public void createChat() throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/createGroupChat.fxml"));
        final Pane newPane = fxmlLoader.load();
        final CreateGroupChatController createGroupChatController = fxmlLoader.getController();
        createGroupChatController.setParentPane(parentPane);
        createGroupChatController.loadUsers(users);

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newPane);
    }
}
