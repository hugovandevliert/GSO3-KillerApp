package main.ui.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class BaseController {
    @FXML protected Pane paneContent;
    private FontAwesomeIconView selectedIcon;

    public BaseController() {
        this.selectedIcon = new FontAwesomeIconView();
    }

    public void selectMenuIcon(MouseEvent mouseEvent) throws IOException {
        selectedIcon.setStyle("-fx-fill: black");

        selectedIcon = (FontAwesomeIconView) mouseEvent.getSource();

        highlightMenuIcon(mouseEvent);

        switch (selectedIcon.getId()) {
            case "iconAccount":
                paneContent.getChildren().clear();
                paneContent.getChildren().add(new FXMLLoader(getClass().getResource("/main/ui/fx/account.fxml")).load());
                break;
            case "iconPrivate":
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/private.fxml"));
                final Pane newPane = fxmlLoader.load();
                final PrivateController privateController = fxmlLoader.getController();
                privateController.addChats();

                paneContent.getChildren().clear();
                paneContent.getChildren().add(newPane);
                break;
            case "iconGroup":
                paneContent.getChildren().clear();
                paneContent.getChildren().add(new FXMLLoader(getClass().getResource("/main/ui/fx/group.fxml")).load());
                break;
            case "iconMemo":
                paneContent.getChildren().clear();
                paneContent.getChildren().add(new FXMLLoader(getClass().getResource("/main/ui/fx/memo.fxml")).load());
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
