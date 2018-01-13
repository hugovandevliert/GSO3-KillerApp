package main.ui.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.ApplicationManager;
import main.data.model.Chat;
import main.data.model.Message;
import main.data.model.User;
import main.rmi.AuthClient;
import main.rmi.MessageClient;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class BaseController {
    final static ApplicationManager applicationManager = new ApplicationManager();

    @FXML private Pane paneMenu;
    @FXML private Pane paneContent;
    @FXML private Pane paneLogin;
    @FXML private Pane paneRegister;
    @FXML private Label lblProfile;
    @FXML private Label lblPrivateChats;
    @FXML private Label lblGroupChats;
    @FXML private Label lblMemos;
    @FXML private JFXTextField txtUsername;
    @FXML private JFXPasswordField txtPassword;
    @FXML private JFXTextField txtPasswordVisible;
    @FXML private JFXCheckBox cboxPasword;
    @FXML private JFXTextField txtUsernameRegister;
    @FXML private JFXPasswordField txtPasswordRegister;
    @FXML private JFXTextField txtPasswordVisibleRegister;
    @FXML private JFXCheckBox cboxPaswordRegister;
    @FXML private JFXTextField txtNameRegister;
    @FXML private JFXComboBox<String> comboboxFunctionRegister;
    @FXML private Label lblOfflineMode;

    private FontAwesomeIconView selectedIcon;
    private Timeline timelineMenuIn;
    private Timeline timelineMenuOut;
    private Timeline timelineAlertDown;
    private Timeline timelineAlertUp;

    public void initialSetup() {
        selectedIcon = new FontAwesomeIconView();
        applicationManager.setBaseController(this);
        paneContent.getChildren().removeAll(lblProfile, lblPrivateChats, lblGroupChats, lblMemos, paneRegister);
    }

    public void login() {
        try {
            if (applicationManager.login(txtUsername.getText(), txtPassword.getText())) {
                txtPassword.setText("");
                txtPasswordVisible.setText("");
                paneContent.getChildren().remove(paneLogin);
                timelineMenuIn.play();
                paneContent.getChildren().addAll(lblProfile, lblPrivateChats, lblGroupChats, lblMemos);

                Platform.runLater(() -> {
                    try {
                        final AuthClient authClient = new AuthClient(applicationManager.getClientManager().getRegistryAuth(), applicationManager.getClientManager());
                        applicationManager.getClientManager().setAuthClient(authClient);

                        final String newMessageProperty = authClient.registerClient(applicationManager.getCurrentUser().getId());

                        final MessageClient messageClient = new MessageClient(applicationManager.getClientManager().getRegistryMessage(),
                                applicationManager.getClientManager(), this, newMessageProperty);
                        applicationManager.getClientManager().setMessageClient(messageClient);
                    } catch (RemoteException | NotBoundException e) {
                        showAlert("Unable to connect to our server.\nYou are now in offline mode.", paneContent);
                        lblOfflineMode.setVisible(true);
                        e.printStackTrace();
                    }
                });
            } else {
                showAlert("Username or password incorrect. Please try again.", paneContent);
            }
        } catch (SQLException | ConnectException e) {
            showAlert("Unable to log in.\nError: " + e.getMessage(), paneContent);
            e.printStackTrace();
        }
    }

    public void logout() {
        selectedIcon.setStyle("-fx-fill: black");
        selectedIcon = new FontAwesomeIconView();
        paneContent.getChildren().clear();
        timelineMenuOut.play();
        showAlert("You have been logged out.\nPlease log in to confirm your identity.", paneContent);
        paneContent.getChildren().add(paneLogin);
    }

    public void openRegisterForm() {
        paneContent.getChildren().clear();
        paneContent.getChildren().add(paneRegister);

        try {
            comboboxFunctionRegister.getItems().addAll(applicationManager.getUserRepository().getFunctionNames()) ;
        } catch (SQLException | ConnectException e) {
            showAlert("Unable to connect to database.\nError: " + e.getMessage(), paneContent);
            e.printStackTrace();
        }
    }
    public void cancelRegistration() {
        paneContent.getChildren().clear();
        paneContent.getChildren().add(paneLogin);
    }

    public void register() {
        try {
            applicationManager.register(txtUsernameRegister.getText(), txtPasswordRegister.getText(), txtNameRegister.getText(), comboboxFunctionRegister.getValue());
            paneContent.getChildren().clear();
            paneContent.getChildren().add(paneLogin);
            showAlert("Account created!\nPlease log in with your account.", paneContent);
        } catch (IllegalArgumentException ex) {
            showAlert(ex.getMessage(), paneContent);
        } catch (SQLException | ConnectException e) {
            showAlert("Unable to register.\nError: " + e.getMessage(), paneContent);
            e.printStackTrace();
        }
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
                memoPageController.loadMemos();

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

    public void setMenuAnimation() {
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

        // Animation for scroll to the left
        timelineMenuOut.setCycleCount(1);
        timelineMenuOut.setAutoReverse(true);
        final KeyValue kvl1 = new KeyValue(clipRect.widthProperty(), 0);
        final KeyValue kvl2 = new KeyValue(clipRect.translateXProperty(), boxBounds.getWidth());
        final KeyValue kvl3 = new KeyValue(paneMenu.translateXProperty(), -boxBounds.getWidth());
        final KeyFrame kfl = new KeyFrame(Duration.millis(200), kvl1, kvl2, kvl3);
        timelineMenuOut.getKeyFrames().add(kfl);
    }

    public void setAlertAnimation(Pane paneAlert) {
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
        EventHandler<ActionEvent> onFinishedUp = t -> {
                try {
                    ((AnchorPane) paneAlert.getParent()).getChildren().remove(paneAlert);
                } catch (Exception e) {
                    e.printStackTrace();
                }};

        // Animation for scroll up
        timelineAlertUp.setCycleCount(1);
        timelineAlertUp.setAutoReverse(true);
        final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
        final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), boxBounds.getHeight());
        final KeyValue kvUp3 = new KeyValue(paneAlert.translateYProperty(), -boxBounds.getHeight());
        final KeyFrame kfUp = new KeyFrame(Duration.millis(200), onFinishedUp, kvUp1, kvUp2, kvUp3);
        timelineAlertUp.getKeyFrames().add(kfUp);
    }

    public void closeApplication() {
        System.exit(0);
    }

    public void minimizeApplication(MouseEvent mouseEvent) {
        ((Stage) ((FontAwesomeIconView) mouseEvent.getSource()).getScene().getWindow()).setIconified(true);
    }

    public void synchronizePasswordfields(KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(txtPassword)) {
            txtPasswordVisible.setText(txtPassword.getText());
        } else {
            txtPassword.setText(txtPasswordVisible.getText());
        }
    }

    public void synchronizePasswordfieldsRegister(KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(txtPasswordRegister)) {
            txtPasswordVisibleRegister.setText(txtPasswordRegister.getText());
        } else {
            txtPasswordRegister.setText(txtPasswordVisibleRegister.getText());
        }
    }

    public void cboxPasswordChanged() {
        if (cboxPasword.isSelected()) {
            txtPassword.setVisible(false);
            txtPasswordVisible.setVisible(true);
            txtPasswordVisible.requestFocus();
            txtPasswordVisible.deselect();
            txtPasswordVisible.positionCaret(txtPasswordVisible.getLength());
        } else {
            txtPassword.setVisible(true);
            txtPasswordVisible.setVisible(false);
            txtPassword.requestFocus();
            txtPassword.deselect();
            txtPassword.positionCaret(txtPassword.getLength());
        }
    }

    public void cboxPasswordChangedRegister() {
        if (cboxPaswordRegister.isSelected()) {
            txtPasswordRegister.setVisible(false);
            txtPasswordVisibleRegister.setVisible(true);
            txtPasswordVisibleRegister.requestFocus();
            txtPasswordVisibleRegister.deselect();
            txtPasswordVisibleRegister.positionCaret(txtPasswordVisibleRegister.getLength());
        } else {
            txtPasswordRegister.setVisible(true);
            txtPasswordVisibleRegister.setVisible(false);
            txtPasswordRegister.requestFocus();
            txtPasswordRegister.deselect();
            txtPasswordRegister.positionCaret(txtPasswordRegister.getLength());
        }
    }

    public void displayMessage(final Message message) throws IOException {
        //Don't display message if the chat is already opened.
        if (applicationManager.getOpenedChat() != null && applicationManager.getOpenedChat().getChatId() == message.getChatId()) {
            applicationManager.getOpenedChat().loadMessage(message);
        } else {
            try {
                final Chat chat = applicationManager.getChatRepository().getChatWithId(message.getChatId());
                chat.setUsers((ArrayList<User>) applicationManager.getUserRepository().getUsersByChatId(chat.getId()));
                showAlert(chat, message, paneContent);
            } catch (SQLException e) {
                showAlert("Unable to connect to database.\nError: " + e.getMessage(), paneContent);
                e.printStackTrace();
            }
        }
    }

    void showAlert(final String message, final Pane parentPane) {
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

    private void showAlert(final Chat chat, final Message message, final Pane parentPane) throws IOException {
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
}
