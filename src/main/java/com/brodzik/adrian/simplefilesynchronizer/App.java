package com.brodzik.adrian.simplefilesynchronizer;

import com.brodzik.adrian.simplefilesynchronizer.ref.References;
import com.brodzik.adrian.simplefilesynchronizer.ui.dashboard.DashboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        App.primaryStage = stage;

        Parent root = FXMLLoader.load(DashboardController.class.getResource("DashboardView.fxml"));

        stage.setScene(new Scene(root));
        stage.setTitle(References.DASHBOARD_TITLE);
        stage.show();
    }
}
