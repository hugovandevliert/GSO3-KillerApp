package main.ui.controller;

import com.jfoenix.controls.JFXTextField;
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

public class CreateGroupChatController extends BaseController {
    @FXML private VBox vboxListedUsers;
    @FXML private JFXTextField txtChatName;

    private Pane parentPane;

    private List<ListedUserController> listedUserControllers;

    void setParentPane(final Pane parentPane) {
        this.parentPane = parentPane;
    }

    void loadUsers(final ArrayList<User> users) throws IOException {
        applicationManager.setOpenedChat(null);
        applicationManager.setPageController(null);

        listedUserControllers = new ArrayList<>();

        for (User user : users) {
            if (user.getId() != applicationManager.getCurrentUser().getId()) {
                final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedUser.fxml"));
                final Pane listedUserPane = fxmlLoader.load();
                final ListedUserController listedUserController = fxmlLoader.getController();
                listedUserController.setParentPane(parentPane);
                listedUserController.setListedUser(user, ListedUserController.onAction.SELECTUSER);

                listedUserControllers.add(listedUserController);
                vboxListedUsers.getChildren().add(listedUserPane);
            }
        }
    }

    public void createGroupChat() {
        if (txtChatName.getText().equals("")) {
            showAlert("Please enter a valid group chat name.", parentPane);
            return;
        }

        ArrayList<User> users = new ArrayList<>();
        users.add(applicationManager.getCurrentUser());

        for (ListedUserController c : listedUserControllers) {
            if (c.isSelected()) {
                users.add(c.getUser());
            }
        }

        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/chat.fxml"));
        Pane newContentPane = null;
        try {
            newContentPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ChatController chatController = fxmlLoader.getController();

        try {
            chatController.loadChat(applicationManager.getChatRepository().createChat(txtChatName.getText(), Chat.ChatType.GROUP, users));
        } catch (SQLException | ConnectException e) {
            showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
            e.printStackTrace();
        }

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newContentPane);
    }
}
