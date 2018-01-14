package main.ui.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import main.data.model.Chat;
import main.data.model.Message;
import main.data.model.User;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class CreateMemoController extends BaseController {
    @FXML private JFXComboBox<String> comboboxFunction;
    @FXML private TextArea txtMemoText;

    private Pane parentPane;

    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    public void loadFunctions() {
        applicationManager.setOpenedChat(null);
        applicationManager.setPageController(null);

        try {
            comboboxFunction.getItems().addAll(applicationManager.getUserRepository().getFunctionNames()) ;
        } catch (SQLException | ConnectException e) {
            showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
            e.printStackTrace();
        }

        Platform.runLater(() -> txtMemoText.requestFocus());
    }

    public void sendMemo() {
        if (comboboxFunction.getValue() == null) {
            showAlert("Please choose a function.", parentPane);
        } else if (txtMemoText.getText().equals("")) {
            showAlert("Please write a memo.", parentPane);
        } else {
            ArrayList<User> users = new ArrayList<>();
            users.add(applicationManager.getCurrentUser());

            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/chat.fxml"));
            Pane newContentPane = null;
            ChatController chatController = null;
            Integer chatId = null;

            try {
                for (User user : applicationManager.getAllUsers()) {
                    if (user.getFunction().equals(comboboxFunction.getValue())) {
                        users.add(user);
                    }
                }

                newContentPane = fxmlLoader.load();

                chatController = fxmlLoader.getController();
                chatId = chatController.getChatId();
                chatController.loadChat(applicationManager.getChatRepository().createChat("Memo to: " + comboboxFunction.getValue(), Chat.ChatType.MEMO, users));
            } catch (SQLException | ConnectException e) {
                showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (chatId == null) {
                showAlert("An unknown error has occurred", parentPane);
                return;
            }

            Message message = new Message(txtMemoText.getText(), applicationManager.getCurrentUser().getId(),
                    applicationManager.getCurrentUser().getName(), chatId, new Time(new Date().getTime()), null);
            applicationManager.getClientManager().getMessageClient().sendMessage(message);
            chatController.loadMessage(message);

            parentPane.getChildren().clear();
            parentPane.getChildren().add(newContentPane);
        }
    }
}
