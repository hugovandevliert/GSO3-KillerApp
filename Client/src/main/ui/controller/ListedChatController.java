package main.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.data.model.Chat;

import java.io.IOException;

public class ListedChatController extends BaseController{
    @FXML private Label lblName;
    @FXML private Label lblLastMessage;
    @FXML private Label lblMessageCount;

    private Chat chat;
    private Pane parentPane;

    void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    void setListedChat(final Chat chat) {
        this.chat = chat;
        lblName.setText(chat.getName());

        Text txtName = new Text(chat.getLastMessage().getSenderName() + ": ");
        txtName.setFont(Font.font("Segoe UI SemiBold",18));
        Text txtMessage = new Text(chat.getLastMessage().getText());
        txtMessage.setFont(Font.font("Segoe UI SemiLight", 18));

        TextFlow txtflowLastMessage = new TextFlow(txtName, txtMessage);
        txtflowLastMessage.setMaxWidth(825);
        lblLastMessage.setGraphic(txtflowLastMessage);

        lblMessageCount.setText(String.valueOf(chat.getUnreadMessagesCount()));
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
