package main.ui.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    void loadChat(final Chat chat) {
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

        lblChatName.setText(this.chat.getName());

        if (!this.chat.getMessages().isEmpty()) {
            for (Message message : this.chat.getMessages()) {
                loadMessage(message);
            }
        }

        scrollpaneListedMessages.setVvalue(1.0);
    }

    private void loadMessage(final Message message) {
        final HBox hBoxMessageBody = new HBox();
        hBoxMessageBody.setMinWidth(1050);

        final Label lblMessage = new Label();
        lblMessage.setText(message.getText());
        lblMessage.setFont(Font.font("Segoe UI SemiLight", 18));
        lblMessage.setBackground(new Background(new BackgroundFill(Paint.valueOf("E0E0E0"), new CornerRadii(2.5), new Insets(-5))));
        lblMessage.setMaxWidth(800);
        lblMessage.setWrapText(true);

        //TODO: Add file to message
        //Maybe like this?
        //lblMessage.setId(message.getId());

        if (message.getFile() != null) {
            final FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_ALT);
            icon.setStyle("-fx-font-family: FontAwesome; -fx-font-size: 20px;");
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
        } else if (chat.getChatType() != Chat.ChatType.PRIVATE){
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

        txtMessageText.setText("");
        txtMessageText.setEditable(true);

        scrollpaneListedMessages.setVvalue(1.0);
    }

    private void saveFile(MouseEvent event) {
        //TODO: Somehow figure out file.
        //Maybe like this?
        //Label lblMessage = (Message) event.getSource();
        //final int messageId = lblMessage.getId();

        //temp file
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
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }
}
