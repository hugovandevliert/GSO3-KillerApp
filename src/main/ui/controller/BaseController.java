package main.ui.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.ApplicationManager;

import java.io.IOException;
import java.util.Objects;

public class BaseController {
    final protected ApplicationManager applicationManager = new ApplicationManager();

    @FXML protected Pane paneContent;
    private FontAwesomeIconView selectedIcon;

    public BaseController() {
        this.selectedIcon = new FontAwesomeIconView();

        applicationManager.login("test", "test");
    }

    public void selectMenuIcon(MouseEvent mouseEvent) throws IOException {
        Pane newPane;
        FXMLLoader fxmlLoader;

        selectedIcon.setStyle("-fx-fill: black");

        selectedIcon = (FontAwesomeIconView) mouseEvent.getSource();

        highlightMenuIcon(mouseEvent);

        switch (selectedIcon.getId()) {
            case "iconAccount":
                fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/account.fxml"));
                newPane = fxmlLoader.load();
                final AccountController accountController = fxmlLoader.getController();
                accountController.loadAccount(applicationManager.getCurrentUser());

                paneContent.getChildren().clear();
                paneContent.getChildren().add(newPane);
            break;
            case "iconPrivate":
                fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/private.fxml"));
                newPane = fxmlLoader.load();
                final PrivateController privateController = fxmlLoader.getController();
                privateController.setBaseController(this);
                privateController.loadChats();

                paneContent.getChildren().clear();
                paneContent.getChildren().add(newPane);
                break;
            case "iconGroup":
                fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/group.fxml"));
                newPane = fxmlLoader.load();
                final GroupController groupController = fxmlLoader.getController();
                groupController.setBaseController(this);
                groupController.loadChats();

                paneContent.getChildren().clear();
                paneContent.getChildren().add(newPane);
                break;
            case "iconMemo":
                fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/memo.fxml"));
                newPane = fxmlLoader.load();
                final MemoController memoController = fxmlLoader.getController();
                memoController.setBaseController(this);

                paneContent.getChildren().clear();
                paneContent.getChildren().add(newPane);
                break;
        }
    }

    public void highlightMenuIcon(MouseEvent mouseEvent) {
        final FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();
        icon.setStyle("-fx-fill: #1DB01D");
    }

    public void revertHighlightMenuIcon(MouseEvent mouseEvent) {
        final FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();

        if (Objects.equals(selectedIcon, icon)) return;

        icon.setStyle("-fx-fill: black");
    }

    public void closeApplication() {
        System.exit(0);
    }

    public void minimizeApplication(MouseEvent mouseEvent) {
        ((Stage)((FontAwesomeIconView)mouseEvent.getSource()).getScene().getWindow()).setIconified(true);
    }
}
