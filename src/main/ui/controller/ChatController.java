package main.ui.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import main.data.model.Chat;
import main.data.model.Message;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ChatController extends BaseController {
    @FXML private Label lblChatName;
    @FXML private ScrollPane scrollpaneListedMessages;
    @FXML private TextArea txtMessageText;
    @FXML private JFXButton btnSendMessage;
    @FXML private VBox vboxListedMessages;

    private File selectedFile;

    void loadChat(final Chat chat) {
        txtMessageText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtMessageText.getText().isEmpty() && selectedFile == null) {
                btnSendMessage.setDisable(true);
            } else {
                btnSendMessage.setDisable(false);
            }
        });

        vboxListedMessages = new VBox();
        vboxListedMessages.setSpacing(20);
        scrollpaneListedMessages.setFitToHeight(false);
        scrollpaneListedMessages.setContent(vboxListedMessages);

        lblChatName.setText(chat.getName());

        if (!chat.getMessages().isEmpty()) {
            for (Message message : chat.getMessages()) {
                loadMessage(message);
            }
        }
    }

    private void loadMessage(final Message message) {
        //TODO: Add time to message
        //Maybe like this?
//        lblMessage.setId(message.getId());

        final HBox hBox = new HBox();
        hBox.setMinWidth(1050);

        final Label lblMessage = new Label();
        lblMessage.setText(message.getText());
        lblMessage.setFont(Font.font("Segoe UI Light", 18));
        lblMessage.setBackground(new Background(new BackgroundFill(Paint.valueOf("E0E0E0"), new CornerRadii(2.5), new Insets(-5))));
        lblMessage.setMaxWidth(900);
        lblMessage.setWrapText(true);

        if (message.getFile() != null) {
            final FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_ALT);
            icon.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 50px;");
            lblMessage.setContentDisplay(ContentDisplay.LEFT);
            lblMessage.setCursor(Cursor.HAND);
            lblMessage.setGraphic(icon);

            lblMessage.setOnMouseEntered((MouseEvent event) -> ((Label) event.getSource()).setUnderline(true));
            lblMessage.setOnMouseExited((MouseEvent event) -> ((Label) event.getSource()).setUnderline(false));
            lblMessage.setOnMouseClicked(event -> saveFile());
        }

        final Label lblSendTime = new Label();
        lblSendTime.setText(message.getTime());
        lblSendTime.setFont(Font.font("Segoe UI Light", 12));
        lblSendTime.setPadding(new Insets(10));

        if (message.getSender().getId() == applicationManager.getCurrentUser().getId()) {
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.getChildren().add(lblMessage);
            hBox.getChildren().add(lblSendTime);
        } else {
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.getChildren().add(lblSendTime);
            hBox.getChildren().add(lblMessage);
        }

        vboxListedMessages.getChildren().add(hBox);
    }

    public void addFile() {
        selectedFile = null;
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add an file");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("All files", "*.*");
        fileChooser.getExtensionFilters().add(filter);
        selectedFile = fileChooser.showOpenDialog(lblChatName.getScene().getWindow());

        if (selectedFile != null) {
            btnSendMessage.setDisable(false);
        } else {
            btnSendMessage.setDisable(true);
        }
    }

    public void sendMessage() {

    }

    private void saveFile() {
        //TODO: Somehow figure out file.
        final File fileContent = new File("main/util/test/test.txt");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.setInitialFileName(fileContent.getName());
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(getFileExtension(fileContent), getFileExtension(fileContent)));
        final File file = fileChooser.showSaveDialog(lblChatName.getScene().getWindow());

        if (file != null) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(String.valueOf(fileContent));
                fileWriter.close();
            } catch (IOException ex) {
                //TODO: proper error feedback for user
                System.out.println("Could not save file.\n" + ex.getMessage());
            }
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }
}
