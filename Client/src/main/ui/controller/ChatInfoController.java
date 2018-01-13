package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.data.model.Chat;
import main.data.model.User;

import java.io.IOException;

public class ChatInfoController extends BaseController {
    private Pane parentPane;

    @FXML private Label lblChatName ;
    @FXML private VBox vboxListedUsers;

    void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    void loadChatInfo(Chat chat) throws IOException {
        applicationManager.setOpenedChat(null);

        if (chat.getChatType() == Chat.ChatType.PRIVATE) {
            if (chat.getUsers().get(0).getId() == applicationManager.getCurrentUser().getId()) {
                lblChatName.setText("Members of private chat with: " + chat.getUsers().get(1).getName());
            } else {
                lblChatName.setText("Members of private chat with: " + chat.getUsers().get(0).getName());
            }
        } else {
            lblChatName.setText("Members of: " + chat.getName());
        }

        for (User user : chat.getUsers()) {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedUser.fxml"));
            final Pane listedUserPane = fxmlLoader.load();
            final ListedUserController listedUserController = fxmlLoader.getController();
            listedUserController.setParentPane(parentPane);
            listedUserController.setListedUser(user, ListedUserController.onAction.OPENPROFILE);

            vboxListedUsers.getChildren().add(listedUserPane);
        }
    }

    public void leaveChat() {
        //TODO: Add leave functionality.
    }
}
