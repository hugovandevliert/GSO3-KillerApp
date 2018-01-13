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
import java.net.ConnectException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class ListedUserController extends BaseController {
    enum onAction {
        OPENPROFILE,
        OPENCHAT,
        SELECTUSER
    }


    @FXML private AnchorPane apaneListedUser;
    @FXML private ImageView imgviewProfilePicture;
    @FXML private Label lblName;

    private Pane parentPane;

    private User user;
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public User getUser() {
        return user;
    }

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
        for (Chat chat : applicationManager.getCurrentUser().getPrivateChats()) {
            for (User u : chat.getUsers()) {
                if (u.getId() == user.getId()) {
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

        createPrivateChat();
    }

    private void selectUser() {
        if (Objects.equals(apaneListedUser.getStyle(), "-fx-background-color: #E0E0E0; -fx-background-radius: 2.5;")) {
            apaneListedUser.setStyle("-fx-background-color: #3FBC3F; -fx-background-radius: 2.5;");
            selected = true;
        } else {
            apaneListedUser.setStyle("-fx-background-color: #E0E0E0; -fx-background-radius: 2.5;");
            selected = false;
        }
    }

    private void createPrivateChat() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/chat.fxml"));
        Pane newContentPane = null;
        try {
            newContentPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ChatController chatController = fxmlLoader.getController();
        try {
            ArrayList<User> users = new ArrayList<>();
            users.add(applicationManager.getCurrentUser());
            users.add(user);

            chatController.loadChat(applicationManager.getChatRepository().createChat("", Chat.ChatType.PRIVATE, users));
        } catch (ConnectException | SQLException e) {
            showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
            e.printStackTrace();
        }

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newContentPane);
    }
}
