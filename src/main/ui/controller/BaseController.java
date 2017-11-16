package main.ui.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.ApplicationManager;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BaseController implements Initializable {
    final static ApplicationManager applicationManager = new ApplicationManager();

    @FXML protected Pane paneContent;

    private FontAwesomeIconView selectedIcon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/privatePage.fxml"));
                newPane = fxmlLoader.load();
                final PrivatePageController privatePageController = fxmlLoader.getController();
                privatePageController.setParentPane(paneContent);
                privatePageController.loadChats();

                paneContent.getChildren().clear();
                paneContent.getChildren().add(newPane);
                break;
            case "iconGroup":
                fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/groupPage.fxml"));
                newPane = fxmlLoader.load();
                final GroupPageController groupPageController = fxmlLoader.getController();
                groupPageController.setParentPane(paneContent);
                groupPageController.loadChats();

                paneContent.getChildren().clear();
                paneContent.getChildren().add(newPane);
                break;
            case "iconMemo":
                fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/memoPage.fxml"));
                newPane = fxmlLoader.load();
                final MemoPageController memoPageController = fxmlLoader.getController();
                memoPageController.setParentPane(paneContent);

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
