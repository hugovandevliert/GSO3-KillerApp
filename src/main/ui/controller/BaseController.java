package main.ui.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class BaseController {
    @FXML protected Pane paneContent;
    @FXML protected Label lblHeaderName;
    @FXML protected JFXButton btnHeaderButton;
}
