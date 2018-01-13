package main.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.data.model.User;


public class AccountController extends BaseController {
    @FXML private ImageView imgviewProfilePicture;
    @FXML private Label lblUsername;
    @FXML private Label lblName;
    @FXML private Label lblFunction;

    @Override
    public void logout() {
        applicationManager.logout();
    }

    void loadAccount(User user) {
        if (user.getPhoto() != null) {
            imgviewProfilePicture.setImage(user.getPhoto());
        }
        lblUsername.setText(user.getUsername());
        lblName.setText(user.getName());
        lblFunction.setText(user.getFunction());
    }
}
