package com.brodzik.adrian.simplefilesynchronizer.ui.entry;

import com.brodzik.adrian.simplefilesynchronizer.App;
import com.brodzik.adrian.simplefilesynchronizer.data.EntryHandler;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

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
        textFieldName.textProperty().bindBidirectional(viewModel.entryProperty().getValue().nameProperty());
        textFieldSource.textProperty().bindBidirectional(viewModel.entryProperty().getValue().sourceProperty());
        textFieldDestination.textProperty().bindBidirectional(viewModel.entryProperty().getValue().destinationProperty());
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
