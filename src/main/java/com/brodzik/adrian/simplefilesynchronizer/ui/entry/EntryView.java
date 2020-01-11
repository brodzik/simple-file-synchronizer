package com.brodzik.adrian.simplefilesynchronizer.ui.entry;

import com.brodzik.adrian.simplefilesynchronizer.App;
import com.brodzik.adrian.simplefilesynchronizer.data.EntryHandler;
import com.brodzik.adrian.simplefilesynchronizer.util.InputValidation;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class EntryView implements FxmlView<EntryViewModel> {
    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldSource;

    @FXML
    private TextField textFieldDestination;

    @FXML
    private Button buttonCancel;

    @InjectViewModel
    private EntryViewModel viewModel;

    public void bindTextFields() {
        textFieldName.textProperty().bindBidirectional(viewModel.getEntry().nameProperty());
        textFieldSource.textProperty().bindBidirectional(viewModel.getEntry().sourceProperty());
        textFieldDestination.textProperty().bindBidirectional(viewModel.getEntry().destinationProperty());
    }

    @FXML
    private void selectSource() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(App.primaryStage);

        if (file != null) {
            viewModel.entryProperty().getValue().setSource(file.getAbsolutePath());
        }
    }

    @FXML
    private void selectDestination() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(App.primaryStage);

        if (file != null) {
            viewModel.entryProperty().getValue().setDestination(file.getAbsolutePath());
        }
    }

    @FXML
    private void apply() {
        if (InputValidation.isEmpty(viewModel.getEntry().getName())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Name cannot be empty.");
            alert.showAndWait();
            return;
        }

        if (!InputValidation.isDirectory(viewModel.getEntry().getSource())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid source directory.");
            alert.showAndWait();
            return;
        }

        if (!InputValidation.isDirectory(viewModel.getEntry().getDestination())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid destination directory.");
            alert.showAndWait();
            return;
        }

        if (Objects.equals(viewModel.getEntry().getSource(), viewModel.getEntry().getDestination())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Source and destination must be different.");
            alert.showAndWait();
            return;
        }

        switch (viewModel.getMode()) {
            case ADD:
                EntryHandler.INSTANCE.add(viewModel.getEntry());
                break;
            case EDIT:
                EntryHandler.INSTANCE.edit(viewModel.getEntry());
                break;
        }


        close();
    }

    @FXML
    private void close() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }
}
