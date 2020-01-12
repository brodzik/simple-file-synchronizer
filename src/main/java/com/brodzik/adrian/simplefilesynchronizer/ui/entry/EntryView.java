package com.brodzik.adrian.simplefilesynchronizer.ui.entry;

import com.brodzik.adrian.simplefilesynchronizer.App;
import com.brodzik.adrian.simplefilesynchronizer.data.SyncDirection;
import com.brodzik.adrian.simplefilesynchronizer.handler.EntryHandler;
import com.brodzik.adrian.simplefilesynchronizer.helper.InputHelper;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.util.Objects;

public class EntryView implements FxmlView<EntryViewModel> {
    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldFolderA;

    @FXML
    private TextField textFieldFolderB;

    @FXML
    private ComboBox<SyncDirection> comboBoxDirection;

    @FXML
    private Button buttonCancel;

    @InjectViewModel
    private EntryViewModel viewModel;

    public void initialize() {
        comboBoxDirection.getItems().setAll(SyncDirection.values());
        comboBoxDirection.setConverter(new StringConverter<>() {
            @Override
            public String toString(SyncDirection syncDirection) {
                return syncDirection.getSymbol();
            }

            @Override
            public SyncDirection fromString(String s) {
                for (SyncDirection value : SyncDirection.values()) {
                    if (s.equals(value.getSymbol())) {
                        return value;
                    }
                }

                return null;
            }
        });
    }

    public void bindInputs() {
        textFieldName.textProperty().bindBidirectional(viewModel.getEntry().nameProperty());
        textFieldFolderA.textProperty().bindBidirectional(viewModel.getEntry().folderAProperty());
        textFieldFolderB.textProperty().bindBidirectional(viewModel.getEntry().folderBProperty());
        comboBoxDirection.valueProperty().bindBidirectional(viewModel.getEntry().directionProperty());
    }

    @FXML
    private void selectFolderA() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(App.primaryStage);

        if (file != null) {
            viewModel.entryProperty().getValue().setFolderA(file.getAbsolutePath());
        }
    }

    @FXML
    private void selectFolderB() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(App.primaryStage);

        if (file != null) {
            viewModel.entryProperty().getValue().setFolderB(file.getAbsolutePath());
        }
    }

    @FXML
    private void apply() {
        if (InputHelper.isEmpty(viewModel.getEntry().getName())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Name cannot be empty.");
            alert.showAndWait();
            return;
        }

        if (!InputHelper.isDirectory(viewModel.getEntry().getFolderA())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid source directory.");
            alert.showAndWait();
            return;
        }

        if (!InputHelper.isDirectory(viewModel.getEntry().getFolderB())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid destination directory.");
            alert.showAndWait();
            return;
        }

        if (Objects.equals(viewModel.getEntry().getFolderA(), viewModel.getEntry().getFolderB())) {
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
