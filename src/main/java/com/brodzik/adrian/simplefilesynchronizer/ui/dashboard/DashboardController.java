package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class DashboardController {
    private final DashboardModel model = new DashboardModel();

    public void initialize() {
        model.getEntries().add(new Entry(0, "a", "b", "c", "d", true));
        model.getEntries().add(new Entry(0, "a", "b", "c", "d", true));
        model.getEntries().add(new Entry(0, "a", "b", "c", "d", true));
    }

    @FXML
    private void createNew() {
    }

    @FXML
    private void showSettings() {
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

    public DashboardModel getModel() {
        return model;
    }
}
