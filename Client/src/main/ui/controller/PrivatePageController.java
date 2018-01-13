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

public class PrivatePageController extends BaseController implements IChatPageController {
    @FXML private VBox vboxListedChats;

    private Pane parentPane;

    void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    void loadChats() throws IOException {
        applicationManager.setOpenedChat(null);
        applicationManager.setPageController(this);

        try {
            applicationManager.loadPrivateChats();
        } catch (SQLException | ConnectException e) {
            showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader;

        for (Chat chat : applicationManager.getCurrentUser().getPrivateChats()) {
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
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/createPrivateChat.fxml"));
        final Pane newPane = fxmlLoader.load();
        final CreatePrivateChatController createPrivateChatController = fxmlLoader.getController();
        createPrivateChatController.setParentPane(parentPane);
        createPrivateChatController.loadUsers((ArrayList<User>) applicationManager.getAllUsers());

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
