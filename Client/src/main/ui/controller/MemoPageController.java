package main.ui.controller;

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

public class MemoPageController extends BaseController implements IChatPageController {
    @FXML private VBox vboxListedChats;

    private Pane parentPane;

    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    void loadMemos() throws IOException {
        applicationManager.setOpenedChat(null);
        applicationManager.setPageController(this);

        try {
            applicationManager.loadMemos();
        } catch (SQLException e) {
            showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
            e.printStackTrace();
        }
        final List<Chat> memos = applicationManager.getCurrentUser().getMemos();
        FXMLLoader fxmlLoader;

        for (Chat memo : memos) {
            try {
                memo.setLastSentMessage(applicationManager.getMessageRepository().getLastMessageByChatId(memo.getId()));
            } catch (SQLException | ConnectException e) {
                showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
            }
            fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/listedChat.fxml"));
            final Pane listedChatPane = fxmlLoader.load();
            final ListedChatController listedChatController = fxmlLoader.getController();

            listedChatController.setParentPane(parentPane);
            listedChatController.setListedChat(memo);

            vboxListedChats.getChildren().add(listedChatPane);
        }
    }

    public void createMemo() throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/createMemo.fxml"));
        final Pane newPane = fxmlLoader.load();
        final CreateMemoController createMemoController = fxmlLoader.getController();
        createMemoController.setParentPane(parentPane);
        createMemoController.loadFunctions();

        parentPane.getChildren().clear();
        parentPane.getChildren().add(newPane);
    }

    @Override
    public void refreshChats() {
        vboxListedChats.getChildren().clear();
        try {
            loadMemos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
