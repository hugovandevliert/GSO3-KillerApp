package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.data.model.User;
import java.io.IOException;

public class ListedUserController {
    @FXML private ImageView imgviewProfilePicture;
    @FXML private Label lblName;

    private Pane parentPane;
    private User user;

    void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    public void setListedUser(User user) {
        this.user = user;
        lblName.setText(user.getName());
        if (user.getPhoto() != null) {
            imgviewProfilePicture.setImage(user.getPhoto());
        }
    }

    public void openUserProfile() throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/account.fxml"));
        final Pane newContentPane = fxmlLoader.load();
        final AccountController accountController = fxmlLoader.getController();
        accountController.loadAccount(user);

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newContentPane);
    }
}
