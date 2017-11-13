package main.ui.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.data.model.Chat;
import main.data.model.Message;

public class ChatController extends BaseController {
    @FXML private Label lblChatName;
    @FXML private VBox vboxListedMessages;

    void loadChat(final Chat chat) {
        lblChatName.setText(chat.getName());

        if (!chat.getMessages().isEmpty()) {
            for (Message message : chat.getMessages()) {
                loadMessage(message);
            }
        }
    }

    private void loadMessage(final Message message) {
        final HBox hBox = new HBox();
        hBox.setMinWidth(1050);

        final Label lblMessage = new Label();
        lblMessage.setText(message.getText());
        lblMessage.setFont(Font.font("Segoe UI Light"));
        lblMessage.setStyle("-fx-font-size: 18; -fx-background-color: #E0E0E0; -fx-padding: 10;");
        lblMessage.setMaxWidth(900);
        lblMessage.setWrapText(true);

        if (message.getFile() != null) {
            final FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_ALT);
            icon.setStyle("-fx-font-family:FontAwesome ;-fx-font-size: 50px;");
            lblMessage.setContentDisplay(ContentDisplay.LEFT);
            lblMessage.setCursor(Cursor.HAND);
            lblMessage.setGraphic(icon);

            lblMessage.setOnMouseEntered((MouseEvent event) -> ((Label) event.getSource()).setUnderline(true));
            lblMessage.setOnMouseExited((MouseEvent event) -> ((Label) event.getSource()).setUnderline(false));
            lblMessage.setOnMouseClicked(event -> openFile());
        }

        if (message.getSender().getId() == applicationManager.getCurrentUser().getId()) {
            hBox.setAlignment(Pos.CENTER_RIGHT);
        } else {
            hBox.setAlignment(Pos.CENTER_LEFT);
        }

        hBox.getChildren().add(lblMessage);
        vboxListedMessages.getChildren().add(hBox);
    }

    private void openFile() {

    }
}
