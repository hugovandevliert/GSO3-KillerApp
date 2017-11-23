package main.ui.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.ApplicationManager;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BaseController implements Initializable {
    final static ApplicationManager applicationManager = new ApplicationManager();

    @FXML protected Pane paneContent;

    @FXML private Pane paneMenu;
    @FXML private Pane paneLogin;

    private FontAwesomeIconView selectedIcon;
    private Timeline timelineMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.selectedIcon = new FontAwesomeIconView();

        setAnimation();
    }

    public void login() {
        applicationManager.login("test", "test");
        paneContent.getChildren().remove(paneLogin);
        timelineMenu.play();
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

    private void setAnimation(){
		// Initial position setting for Pane
        Rectangle2D boxBounds = new Rectangle2D(0, 0, 150, 750);
        Rectangle clipRect = new Rectangle();
        clipRect.setWidth(0);
        clipRect.setHeight(boxBounds.getHeight());
        clipRect.translateXProperty().set(boxBounds.getWidth());
        paneMenu.setClip(clipRect);
        paneMenu.translateXProperty().set(-boxBounds.getWidth());

		// Animation for bouncing effect
        final Timeline timelineMenuBounce = new Timeline();
        timelineMenuBounce.setCycleCount(2);
        timelineMenuBounce.setAutoReverse(true);
        final KeyValue kvb1 = new KeyValue(clipRect.widthProperty(), (boxBounds.getWidth()-15));
        final KeyValue kvb2 = new KeyValue(clipRect.translateXProperty(), 15);
        final KeyValue kvb3 = new KeyValue(paneMenu.translateXProperty(), -15);
        final KeyFrame kfb = new KeyFrame(Duration.millis(100), kvb1, kvb2, kvb3);
        timelineMenuBounce.getKeyFrames().add(kfb);

		// Event handler to call bouncing effect after scrolling is finished
        EventHandler<ActionEvent> onFinished = t -> timelineMenuBounce.play();

        timelineMenu = new Timeline();

        // Animation for scroll to the right
        timelineMenu.setCycleCount(1);
        timelineMenu.setAutoReverse(true);
        final KeyValue kvr1 = new KeyValue(clipRect.widthProperty(), boxBounds.getWidth());
        final KeyValue kvr2 = new KeyValue(clipRect.translateXProperty(), 0);
        final KeyValue kvr3 = new KeyValue(paneMenu.translateXProperty(), 0);
        final KeyFrame kfr = new KeyFrame(Duration.millis(200), onFinished, kvr1, kvr2, kvr3);
        timelineMenu.getKeyFrames().add(kfr);
    }

    public void closeApplication() {
        System.exit(0);
    }

    public void minimizeApplication(MouseEvent mouseEvent) {
        ((Stage)((FontAwesomeIconView)mouseEvent.getSource()).getScene().getWindow()).setIconified(true);
    }
}
