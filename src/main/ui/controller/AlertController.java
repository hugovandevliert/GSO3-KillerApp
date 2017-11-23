package main.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.data.model.Chat;
import main.data.model.Message;

public class AlertController {

    @FXML private Label lblName;
    @FXML private Label lblMessage;

    void setMessage(Chat chat, Message message) {
        lblName.setText("Private chat with: " + chat.getName());

        Text txtName = new Text(message.getSenderName() + ": ");
        txtName.setFont(Font.font("Segoe UI SemiBold",18));
        Text txtMessage = new Text(message.getText());
        txtMessage.setFont(Font.font("Segoe UI SemiLight", 18));

        TextFlow txtflowLastMessage = new TextFlow(txtName, txtMessage);
        txtflowLastMessage.setMaxWidth(750);
        lblMessage.setGraphic(txtflowLastMessage);
    }
}
