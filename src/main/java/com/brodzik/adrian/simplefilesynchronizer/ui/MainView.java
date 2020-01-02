package com.brodzik.adrian.simplefilesynchronizer.ui;

import de.saxsys.mvvmfx.FxmlView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainView implements FxmlView<MainViewModel> {
    @FXML
    public Button buttonTest;

    public void initialize() {
        buttonTest.setOnAction(actionEvent -> System.out.println("Click!"));
    }
}
