package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.App;
import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.ui.about.AboutController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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

    @FXML
    private void showAbout() throws IOException {
        Parent root = FXMLLoader.load(AboutController.class.getResource("AboutView.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.primaryStage);
        stage.setResizable(false);
        stage.show();
    }

    public DashboardModel getModel() {
        return model;
    }
}
