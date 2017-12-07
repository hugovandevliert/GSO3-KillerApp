package main.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.ui.controller.BaseController;

public class main extends Application {
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

        //primaryStage.setOnCloseRequest(e -> Platform.exit());
    }
}
