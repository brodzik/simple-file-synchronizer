package com.brodzik.adrian.simplefilesynchronizer;

import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import com.brodzik.adrian.simplefilesynchronizer.ui.dashboard.DashboardView;
import com.brodzik.adrian.simplefilesynchronizer.ui.dashboard.DashboardViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        App.primaryStage = stage;

        ViewTuple<DashboardView, DashboardViewModel> about = FluentViewLoader.fxmlView(DashboardView.class).load();

        stage.setScene(new Scene(about.getView()));
        stage.setTitle(Constants.DASHBOARD_TITLE);
        stage.show();
    }
}
