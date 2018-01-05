package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.data.model.Chat;
import main.data.model.User;

import java.io.IOException;
import java.util.Objects;

public class ListedUserController extends BaseController {
    enum onAction {
        OPENPROFILE,
        OPENCHAT,
        SELECTUSER
    }

    private Pane parentPane;
    private User user;

    @FXML private AnchorPane apaneListedUser;
    @FXML private ImageView imgviewProfilePicture;
    @FXML private Label lblName;

    void setParentPane(final Pane parentPane) {
        this.parentPane = parentPane;
    }

    void setListedUser(final User user, final onAction onAction) {
        this.user = user;
        lblName.setText(user.getName());
        if (user.getPhoto() != null) {
            imgviewProfilePicture.setImage(user.getPhoto());
        }

        switch (onAction) {
            case OPENPROFILE:
                apaneListedUser.setOnMouseClicked(event -> openUserProfile());
                break;
            case OPENCHAT:
                apaneListedUser.setOnMouseClicked(event -> openChat());
                break;
            case SELECTUSER:
                apaneListedUser.setOnMouseClicked(event -> selectUser());
                break;
        }
    }

    private void openUserProfile() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/account.fxml"));
        Pane newContentPane = null;
        try {
            newContentPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final AccountController accountController = fxmlLoader.getController();
        accountController.loadAccount(user);

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newContentPane);
    }

    private void openChat() {
        for (Chat chat : user.getPrivateChats()) {
            for (User user : chat.getUsers()) {
                if (user.getId() == BaseController.applicationManager.getCurrentUser().getId()) {
                    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/chat.fxml"));
                    Pane newContentPane = null;
                    try {
                        newContentPane = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final ChatController chatController = fxmlLoader.getController();
                    chatController.loadChat(chat);

                    parentPane.getChildren().clear();
                    parentPane.getChildren().add(newContentPane);
                    return;
                }
            }
        }

        createChat();
    }

    private void selectUser() {
        if (Objects.equals(apaneListedUser.getStyle(), "-fx-background-color: #E0E0E0; -fx-background-radius: 2.5;")) {
            apaneListedUser.setStyle("-fx-background-color: #3FBC3F; -fx-background-radius: 2.5;");
        } else {
            apaneListedUser.setStyle("-fx-background-color: #E0E0E0; -fx-background-radius: 2.5;");
        }
    }

    private void createChat() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/chat.fxml"));
        Pane newContentPane = null;
        try {
            newContentPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ChatController chatController = fxmlLoader.getController();
        System.out.println("Creating chats not implemented yet.");
//        TODO: create chat
//        chatController.loadChat();

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newContentPane);
    }
}
