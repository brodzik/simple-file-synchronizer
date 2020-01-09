package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.App;
import com.brodzik.adrian.simplefilesynchronizer.ref.References;
import com.brodzik.adrian.simplefilesynchronizer.ui.about.AboutView;
import com.brodzik.adrian.simplefilesynchronizer.ui.about.AboutViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardView implements FxmlView<DashboardViewModel>, Initializable {
    @InjectViewModel
    private DashboardViewModel viewModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel.subscribe("EXIT", (key, payload) -> Platform.exit());
    }

    @FXML
    private void createNew() {
    }

    @FXML
    private void showSettings() {
    }

    @FXML
    private void exit() {
        viewModel.publish("EXIT");
    }

    @FXML
    private void showAbout() {
        ViewTuple<AboutView, AboutViewModel> about = FluentViewLoader.fxmlView(AboutView.class).load();

        Stage stage = new Stage();
        stage.initOwner(App.primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(about.getView()));
        stage.setTitle(References.ABOUT_TITLE);
        stage.setResizable(false);
        stage.show();
    }
}
