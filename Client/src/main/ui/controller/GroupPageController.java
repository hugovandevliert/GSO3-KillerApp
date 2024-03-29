package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.data.model.Chat;
import main.data.model.User;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupPageController extends BaseController implements IChatPageController {
    @FXML private VBox vboxListedChats;

    private Pane parentPane;

    public void setParentPane(final Pane parentPane) {
        this.parentPane = parentPane;
    }

    void loadChats() throws IOException {
        applicationManager.setOpenedChat(null);
        applicationManager.setPageController(this);

        try {
            applicationManager.loadGroupChats();
        } catch (SQLException e) {
            showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
            e.printStackTrace();
        }
        final List<Chat> chats = applicationManager.getCurrentUser().getGroupChats();
        FXMLLoader fxmlLoader;

        for (Chat chat : chats) {
            try {
                chat.setLastSentMessage(applicationManager.getMessageRepository().getLastMessageByChatId(chat.getId()));
            } catch (SQLException | ConnectException e) {
                showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
            }
            fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedChat.fxml"));
            final Pane listedChatPane = fxmlLoader.load();
            final ListedChatController listedChatController = fxmlLoader.getController();

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
        try {
            createGroupChatController.loadUsers((ArrayList<User>) applicationManager.getAllUsers());
        } catch (SQLException e) {
            showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
            e.printStackTrace();
        }

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newPane);
    }

    @Override
    public void refreshChats() {
        vboxListedChats.getChildren().clear();
        try {
            loadChats();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
