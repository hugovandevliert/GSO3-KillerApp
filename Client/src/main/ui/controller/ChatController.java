package main.ui.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import main.data.model.MessageFile;
import main.data.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ChatController extends BaseController {
    private Chat chat;
    private File selectedFile;
    private Pane parentPane;

    @FXML private Label lblChatName;
    @FXML private ScrollPane scrollpaneListedMessages;
    @FXML private TextArea txtMessageText;
    @FXML private JFXButton btnSendMessage;
    @FXML private VBox vboxListedMessages;

    void setParentPane(final Pane parentPane) {
        this.parentPane = parentPane;
    }

    int getChatId() {
        return chat.getId();
    }

    void loadChat(final Chat chat) {
        applicationManager.setOpenedChat(this);
        applicationManager.setPageController(null);

        this.chat = chat;
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
        scrollpaneListedMessages.vvalueProperty().bind(vboxListedMessages.heightProperty());

        if (chat.getChatType().equals(Chat.ChatType.PRIVATE)) {
            for (User u : chat.getUsers()) {
                if (u.getId() != applicationManager.getCurrentUser().getId()) {
                    lblChatName.setText(u.getName());
                }
            }
        } else {
            lblChatName.setText(this.chat.getName());
        }

        try {
            applicationManager.getChatRepository().resetUnreadCount(chat.getId(), applicationManager.getCurrentUser().getId());
            chat.setMessages((ArrayList<Message>) applicationManager.getMessageRepository().getMessagesByChatId(chat.getId()));
            for (Message message : this.chat.getMessages()) {
                loadMessage(message);
            }
        } catch (SQLException | ConnectException e) {
            showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
        }

        Platform.runLater(() -> txtMessageText.requestFocus());
    }

    public void addFile() {
        selectedFile = null;
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add a file");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("All files", "*.*");
        fileChooser.getExtensionFilters().add(filter);
        selectedFile = fileChooser.showOpenDialog(lblChatName.getScene().getWindow());

        if (selectedFile != null) {
            btnSendMessage.setDisable(false);
            txtMessageText.setText(selectedFile.getName());
            txtMessageText.setEditable(false);
        } else {
            btnSendMessage.setDisable(true);
            txtMessageText.setText("");
            txtMessageText.setEditable(true);
        }
    }

    public void openChatInfo() throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/chatInfo.fxml"));
        final Pane newContentPane = fxmlLoader.load();
        final ChatInfoController chatInfoController = fxmlLoader.getController();
        chatInfoController.setParentPane(parentPane);
        chatInfoController.loadChatInfo(chat);

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newContentPane);
    }

    public void sendMessage() {
        MessageFile messageFile = null;
        Integer fileId = null;

        if (selectedFile != null) {
            messageFile = new MessageFile(selectedFile, selectedFile.getName(), getFileExtension(selectedFile));
            try {
                fileId = applicationManager.getMessageRepository().addFile(messageFile);
            } catch (SQLException | ConnectException e) {
                showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
                e.printStackTrace();
            }
        }
        Message message = new Message(txtMessageText.getText(), applicationManager.getCurrentUser().getId(),
                applicationManager.getCurrentUser().getName(), chat.getId(), new Time(new Date().getTime()), fileId);
        applicationManager.getClientManager().getMessageClient().sendMessage(message);

        if (messageFile != null) {
            message.addFile(messageFile);
        }

        txtMessageText.setText("");
        txtMessageText.setEditable(true);

        loadMessage(message);
    }

    void loadMessage(final Message message) {
        final HBox hBoxMessageBody = new HBox();
        hBoxMessageBody.setMinWidth(1050);

        final Label lblMessage = new Label();
        lblMessage.setText(message.getText());
        lblMessage.setFont(Font.font("Segoe UI SemiLight", 18));
        lblMessage.setBackground(new Background(new BackgroundFill(Paint.valueOf("E0E0E0"), new CornerRadii(2.5), new Insets(-5))));
        lblMessage.setMaxWidth(800);
        lblMessage.setWrapText(true);

        if (message.getFileId() != null) {
            final FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_ALT);
            icon.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 20px;");

            lblMessage.setId(String.valueOf(message.getFileId()));
            lblMessage.setContentDisplay(ContentDisplay.LEFT);
            lblMessage.setCursor(Cursor.HAND);
            lblMessage.setGraphic(icon);

            lblMessage.setOnMouseEntered((MouseEvent event) -> ((Label) event.getSource()).setUnderline(true));
            lblMessage.setOnMouseExited((MouseEvent event) -> ((Label) event.getSource()).setUnderline(false));
            lblMessage.setOnMouseClicked(event -> saveFile(event));
        }

        final Label lblSendTime = new Label();
        lblSendTime.setText(message.getTime());
        lblSendTime.setFont(Font.font("Segoe UI SemiLight", 12));
        lblSendTime.setAlignment(Pos.CENTER);
        lblSendTime.setMinWidth(55);
        lblSendTime.setPadding(new Insets(10));

        if (message.getSenderId() == applicationManager.getCurrentUser().getId()) {
            hBoxMessageBody.setAlignment(Pos.CENTER_RIGHT);

            hBoxMessageBody.getChildren().add(lblMessage);
            hBoxMessageBody.getChildren().add(lblSendTime);
        } else if (chat.getChatType() != Chat.ChatType.PRIVATE) {
            hBoxMessageBody.setAlignment(Pos.CENTER_LEFT);

            final Label lblSenderName = new Label();
            lblSenderName.setText(message.getSenderName() + ": ");
            lblSenderName.setFont(Font.font("Segoe UI SemiBold", 18));
            lblSenderName.setAlignment(Pos.TOP_LEFT);
            lblSenderName.setBackground(new Background(new BackgroundFill(Paint.valueOf("E0E0E0"), new CornerRadii(2.5), new Insets(-5))));

            lblMessage.heightProperty().addListener((obs, oldVal, newVal) ->
                    lblSenderName.resizeRelocate(lblSenderName.getLayoutX(), lblMessage.getLayoutY(), lblSenderName.getWidth(), newVal.doubleValue()));
            lblMessage.layoutYProperty().addListener((obs, oldVal, newVal) ->
                    lblSenderName.relocate(lblSenderName.getLayoutX(), newVal.doubleValue()));

            hBoxMessageBody.getChildren().add(lblSendTime);
            hBoxMessageBody.getChildren().add(lblSenderName);
            hBoxMessageBody.getChildren().add(lblMessage);
        } else {
            hBoxMessageBody.setAlignment(Pos.CENTER_LEFT);

            hBoxMessageBody.getChildren().add(lblSendTime);
            hBoxMessageBody.getChildren().add(lblMessage);
        }

        vboxListedMessages.getChildren().add(hBoxMessageBody);
    }

    private void saveFile(MouseEvent event) {
        Label lblMessage = (Label) event.getSource();
        final int fileId = Integer.valueOf(lblMessage.getId());
        String fileName = null;
        String fileExtension = null;

        try {
            fileName = applicationManager.getMessageRepository().getFileName(fileId);
            fileExtension = applicationManager.getMessageRepository().getFileExtension(fileId);
        } catch (SQLException | ConnectException e) {
            showAlert("Error retrieving file from database.\nError: " + e.getMessage(), parentPane);
            e.printStackTrace();
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.setInitialFileName(fileName);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(fileExtension + " files", "*." + fileExtension);
        FileChooser.ExtensionFilter extFilterDefault = new FileChooser.ExtensionFilter("all files", "*.*" );
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extFilterDefault);
        final File savedFile = fileChooser.showSaveDialog(lblChatName.getScene().getWindow());

        if (savedFile != null) {
            try (FileOutputStream stream = new FileOutputStream(savedFile)) {
                stream.write(applicationManager.getMessageRepository().getFile(fileId));
                stream.close();
            } catch (IOException | SQLException e) {
                showAlert("Could not save file.\n" + e.getMessage(), parentPane);
            }
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf('.') != -1 && fileName.lastIndexOf('.') != 0) {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        } else {
            return "";
        }
    }
}
