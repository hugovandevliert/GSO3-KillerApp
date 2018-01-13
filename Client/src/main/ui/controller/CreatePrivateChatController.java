package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.data.model.User;

import java.io.IOException;
import java.util.ArrayList;

public class CreatePrivateChatController extends BaseController {
    @FXML private VBox vboxListedUsers;
    private Pane parentPane;

    void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    void loadUsers(ArrayList<User> users) throws IOException {
        applicationManager.setOpenedChat(null);
        applicationManager.setPageController(null);

        for (User user : users) {
            if (user.getId() != applicationManager.getCurrentUser().getId()) {
                final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedUser.fxml"));
                final Pane listedUserPane = fxmlLoader.load();
                final ListedUserController listedUserController = fxmlLoader.getController();
                listedUserController.setParentPane(parentPane);
                listedUserController.setListedUser(user, ListedUserController.onAction.OPENCHAT);

                vboxListedUsers.getChildren().add(listedUserPane);
            }
        }
    }
}
