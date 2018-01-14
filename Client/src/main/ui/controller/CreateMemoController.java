package main.ui.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.net.ConnectException;
import java.sql.SQLException;

public class CreateMemoController extends BaseController {
    @FXML private JFXComboBox<String> comboboxFunction;
    @FXML private TextArea txtMemoText;

    private Pane parentPane;

    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }

    public void loadFunctions() {
        applicationManager.setOpenedChat(null);
        applicationManager.setPageController(null);

        try {
            comboboxFunction.getItems().addAll(applicationManager.getUserRepository().getFunctionNames()) ;
        } catch (SQLException | ConnectException e) {
            showAlert("Unable to connect to database.\nError: " + e.getMessage(), parentPane);
            e.printStackTrace();
        }
    }

    public void sendMemo() {
        if (comboboxFunction.getValue() == null) {
            showAlert("Please choose a function.", parentPane);
        } else if (txtMemoText.getText().equals("")) {
            showAlert("Please write a memo.", parentPane);
        }
    }
}
