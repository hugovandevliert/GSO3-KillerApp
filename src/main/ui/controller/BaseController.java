package main.ui.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class BaseController {
    @FXML protected Pane paneContent;

    public void selectMenuIcon(MouseEvent mouseEvent) throws IOException {
        final FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();

        switch (icon.getId()) {
            case "iconAccount":
                paneContent.getChildren().clear();
                paneContent.getChildren().add(new FXMLLoader(getClass().getResource("/main/ui/fx/account.fxml")).load());
                break;
            case "iconPrivate":
                paneContent.getChildren().clear();
                paneContent.getChildren().add(new FXMLLoader(getClass().getResource("/main/ui/fx/private.fxml")).load());
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
        icon.setStyle("-fx-fill: green");
    }

    public void revertHighlightMenuIcon(MouseEvent mouseEvent) {
        final FontAwesomeIconView icon = (FontAwesomeIconView) mouseEvent.getSource();
        icon.setStyle("-fx-fill: black");
    }
}
