package main.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MemoController {
    @FXML protected Pane paneContent;

    private BaseController baseController;

    void setBaseController(BaseController baseController) {
        this.baseController = baseController;
    }

}
