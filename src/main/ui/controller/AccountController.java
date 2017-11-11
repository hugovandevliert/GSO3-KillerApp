package main.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.data.model.User;


public class AccountController {
    @FXML private ImageView imgviewProfilePicture;
    @FXML private Label lblUsername;
    @FXML private Label lblName;
    @FXML private Label lblFunction;

    void loadAccount(User currentUser) {
        if (currentUser.getPhoto() != null) {
            imgviewProfilePicture.setImage(currentUser.getPhoto());
        }
        lblUsername.setText(currentUser.getUsername());
        lblName.setText(currentUser.getName());
        lblFunction.setText(currentUser.getFunction());
    }
}
