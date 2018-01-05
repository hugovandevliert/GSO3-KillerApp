package main.ui.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.data.model.User;

import java.io.IOException;
import java.util.ArrayList;

public class CreateGroupChatController extends BaseController {
    public VBox vboxListedUsers;
    private Pane parentPane;

    void setParentPane(final Pane parentPane) {
        this.parentPane = parentPane;
    }

    void loadUsers(final ArrayList<User> users) throws IOException {
        for (User user : users) {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedUser.fxml"));
            final Pane listedUserPane = fxmlLoader.load();
            final ListedUserController listedUserController = fxmlLoader.getController();
            listedUserController.setParentPane(parentPane);
            listedUserController.setListedUser(user, ListedUserController.onAction.SELECTUSER);

            vboxListedUsers.getChildren().add(listedUserPane);
        }
    }

    public void createChat() {

    }
}
