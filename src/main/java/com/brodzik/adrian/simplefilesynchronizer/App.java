package com.brodzik.adrian.simplefilesynchronizer;

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
        System.out.println("Starting...");
        Application.launch(args);
        System.out.println("Exiting...");
    }

    @Override
    public void start(Stage stage) throws IOException {
        App.primaryStage = stage;
        Parent root = FXMLLoader.load(DashboardController.class.getResource("DashboardView.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Simple File Synchronizer");
        stage.show();
    }
}
