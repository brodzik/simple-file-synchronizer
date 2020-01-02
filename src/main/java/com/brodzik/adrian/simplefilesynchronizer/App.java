package com.brodzik.adrian.simplefilesynchronizer;

import com.brodzik.adrian.simplefilesynchronizer.ui.MainView;
import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        System.out.println("Starting...");
        Application.launch(args);
        System.out.println("Exiting...");
    }

    @Override
    public void start(Stage stage) {
        Parent parent = FluentViewLoader.fxmlView(MainView.class).load().getView();
        stage.setScene(new Scene(parent));
        stage.setTitle("Simple File Synchronizer");
        stage.show();
    }
}
