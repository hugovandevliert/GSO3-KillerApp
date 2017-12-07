package main.ui.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.ApplicationManager;
import main.data.model.Chat;
import main.data.model.Message;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class BaseController {
    final static ApplicationManager applicationManager = new ApplicationManager();

    @FXML private Pane paneMenu;
    @FXML private Pane paneContent;
    @FXML private Pane paneLogin;
    @FXML private Label lblProfile;
    @FXML private Label lblPrivateChats;
    @FXML private Label lblGroupChats;
    @FXML private Label lblMemos;

    private FontAwesomeIconView selectedIcon;
    private Timeline timelineMenuIn;
    private Timeline timelineMenuOut;
    private Timeline timelineAlertDown;
    private Timeline timelineAlertUp;

    public void initialSetup() {
        this.selectedIcon = new FontAwesomeIconView();
        applicationManager.setBasecontroller(this);
        paneContent.getChildren().removeAll(lblProfile, lblPrivateChats, lblGroupChats, lblMemos);
    }

    public void login() {
        applicationManager.login("test", "test");
        paneContent.getChildren().remove(paneLogin);
        timelineMenuIn.play();
        paneContent.getChildren().addAll(lblProfile, lblPrivateChats, lblGroupChats, lblMemos);
    }

    public void logout() {
        this.selectedIcon = new FontAwesomeIconView();
        paneContent.getChildren().clear();
        timelineMenuOut.play();
//  setMenuAnimation();
        showAlert("You have been logged out.\nPlease log in to confirm your identity.", paneContent);
        paneContent.getChildren().add(paneLogin);
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

    public void setMenuAnimation(){
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
        final KeyValue kvb1 = new KeyValue(clipRect.widthProperty(), boxBounds.getWidth() - 15);
        final KeyValue kvb2 = new KeyValue(clipRect.translateXProperty(), 15);
        final KeyValue kvb3 = new KeyValue(paneMenu.translateXProperty(), -15);
        final KeyFrame kfb = new KeyFrame(Duration.millis(100), kvb1, kvb2, kvb3);
        timelineMenuBounce.getKeyFrames().add(kfb);

        // Event handler to call bouncing effect after scrolling is finished
        EventHandler<ActionEvent> onFinished = t -> timelineMenuBounce.play();

        timelineMenuIn = new Timeline();

        // Animation for scroll to the right
        timelineMenuIn.setCycleCount(1);
        timelineMenuIn.setAutoReverse(true);
        final KeyValue kvr1 = new KeyValue(clipRect.widthProperty(), boxBounds.getWidth());
        final KeyValue kvr2 = new KeyValue(clipRect.translateXProperty(), 0);
        final KeyValue kvr3 = new KeyValue(paneMenu.translateXProperty(), 0);
        final KeyFrame kfr = new KeyFrame(Duration.millis(200), onFinished, kvr1, kvr2, kvr3);
        timelineMenuIn.getKeyFrames().add(kfr);

        timelineMenuOut = new Timeline();

        // Animation for scroll to the right
        timelineMenuOut.setCycleCount(1);
        timelineMenuOut.setAutoReverse(true);
        final KeyValue kvl1 = new KeyValue(clipRect.widthProperty(), 0);
        final KeyValue kvl2 = new KeyValue(clipRect.translateXProperty(), boxBounds.getWidth());
        final KeyValue kvl3 = new KeyValue(paneMenu.translateXProperty(), - boxBounds.getWidth());
        final KeyFrame kfl = new KeyFrame(Duration.millis(200), kvl1, kvl2, kvl3);
        timelineMenuOut.getKeyFrames().add(kfl);
    }

    public void setAlertAnimation(Pane paneAlert){
        // Initial position setting for Pane
        Rectangle2D boxBounds = new Rectangle2D(0, 0, 900, 100);
        Rectangle clipRect = new Rectangle();
        clipRect.setHeight(0);
        clipRect.setWidth(boxBounds.getWidth());
        clipRect.translateYProperty().set(boxBounds.getHeight());
        paneAlert.setClip(clipRect);
        paneAlert.translateYProperty().set(-boxBounds.getHeight());

        // Animation for bouncing effect
        final Timeline timelineMenuBounce = new Timeline();
        timelineMenuBounce.setCycleCount(2);
        timelineMenuBounce.setAutoReverse(true);
        final KeyValue kvb1 = new KeyValue(clipRect.heightProperty(), boxBounds.getHeight() - 15);
        final KeyValue kvb2 = new KeyValue(clipRect.translateYProperty(), 15);
        final KeyValue kvb3 = new KeyValue(paneAlert.translateYProperty(), -15);
        final KeyFrame kfb = new KeyFrame(Duration.millis(100), kvb1, kvb2, kvb3);
        timelineMenuBounce.getKeyFrames().add(kfb);

        // Event handler to call bouncing effect after scrolling is finished
        EventHandler<ActionEvent> onFinished = t -> timelineMenuBounce.play();

        timelineAlertDown = new Timeline();
        timelineAlertUp = new Timeline();

        // Animation for scroll down
        timelineAlertDown.setCycleCount(1);
        timelineAlertDown.setAutoReverse(true);
        final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), boxBounds.getHeight());
        final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);
        final KeyValue kvDwn3 = new KeyValue(paneAlert.translateYProperty(), 0);
        final KeyFrame kfDwn = new KeyFrame(Duration.millis(200), onFinished, kvDwn1, kvDwn2, kvDwn3);
        timelineAlertDown.getKeyFrames().add(kfDwn);

        // Event handler to remove pane from canvas after it's finished going up
        EventHandler<ActionEvent> onFinishedUp = t ->
                ((AnchorPane) paneAlert.getParent()).getChildren().remove(paneAlert);

        // Animation for scroll up
        timelineAlertUp.setCycleCount(1);
        timelineAlertUp.setAutoReverse(true);
        final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
        final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), boxBounds.getHeight());
        final KeyValue kvUp3 = new KeyValue(paneAlert.translateYProperty(), - boxBounds.getHeight());
        final KeyFrame kfUp = new KeyFrame(Duration.millis(200), onFinishedUp, kvUp1, kvUp2, kvUp3);
        timelineAlertUp.getKeyFrames().add(kfUp);
    }

    public void closeApplication() {
        System.exit(0);
    }

    public void minimizeApplication(MouseEvent mouseEvent) {
        ((Stage)((FontAwesomeIconView)mouseEvent.getSource()).getScene().getWindow()).setIconified(true);
    }

    void showAlert(final Chat chat, final Message message, final Pane parentPane) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/alert.fxml"));
        final Pane paneAlert = fxmlLoader.load();
        final AlertController alertController = fxmlLoader.getController();
        alertController.setParentPane(parentPane);
        alertController.setMessage(chat, message);

        setAlertAnimation(paneAlert);
        ((AnchorPane) parentPane.getParent()).getChildren().add(paneAlert);
        timelineAlertDown.play();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> timelineAlertUp.play());
            }
        }, 5000);
    }

    private void showAlert(final String message, final Pane parentPane) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/fx/alert.fxml"));
        Pane paneAlert = null;

        try {
            paneAlert = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final AlertController alertController = fxmlLoader.getController();
        alertController.setText(message);

        setAlertAnimation(paneAlert);
        ((AnchorPane) parentPane.getParent()).getChildren().add(paneAlert);
        timelineAlertDown.play();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> timelineAlertUp.play());
            }
        }, 5000);
    }
}
