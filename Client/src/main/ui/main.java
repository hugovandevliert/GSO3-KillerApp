package main.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.ui.controller.BaseController;

public class main extends Application {
    private double xOffset, yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fx/base.fxml"));
        final Parent root = fxmlLoader.load();
        final BaseController baseController = fxmlLoader.getController();
        baseController.setMenuAnimation();
        baseController.initialSetup();
        final Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        enableGUIMoving(root, primaryStage);

        //primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    private void enableGUIMoving(final Parent parent, final Stage stage) {
        // Change the offset for both X and Y
        // whenever the user clicks on our GUI we need to register that coordinate and save it
        parent.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // Change the actual offset of the scene with the changed variables
        parent.setOnMouseDragged(event -> {
            if (yOffset < 30) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }
}
