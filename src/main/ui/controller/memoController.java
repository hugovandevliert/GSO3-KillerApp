package main.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MemoController extends BaseController{
    private Pane parentPane;

    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }
}
