package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.data.model.Chat;
import main.data.model.Message;
import java.io.IOException;

public class AlertController {

    @FXML private Label lblName;
    @FXML private Label lblMessage;

    private Chat chat;
    private Pane parentPane;

    void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    void setMessage(Chat chat, Message message) {
        this.chat = chat;
        lblName.setText("Private chat with: " + chat.getName());

        Text txtName = new Text(message.getSenderName() + ": ");
        txtName.setFont(Font.font("Segoe UI SemiBold",18));
        Text txtMessage = new Text(message.getText());
        txtMessage.setFont(Font.font("Segoe UI SemiLight", 18));

        TextFlow txtflowLastMessage = new TextFlow(txtName, txtMessage);
        txtflowLastMessage.setMaxWidth(750);
        lblMessage.setGraphic(txtflowLastMessage);
    }

    public void openChat() throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/chat.fxml"));
        final Pane newContentPane = fxmlLoader.load();
        final ChatController chatController = fxmlLoader.getController();
        chatController.setParentPane(parentPane);
        chatController.loadChat(chat);

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newContentPane);
    }
}
