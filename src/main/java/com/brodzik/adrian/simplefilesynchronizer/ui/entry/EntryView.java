package com.brodzik.adrian.simplefilesynchronizer.ui.entry;

import com.brodzik.adrian.simplefilesynchronizer.data.SyncDirection;
import com.brodzik.adrian.simplefilesynchronizer.helper.InputHelper;
import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

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
    private ToggleGroup toggleFrequency;

    @FXML
    private RadioButton radioButtonManual;

    @FXML
    private RadioButton radioButtonAutomatic;

    @FXML
    private Spinner<Integer> spinnerFrequency;

    @FXML
    private ToggleGroup toggleEnabled;

    @FXML
    private RadioButton radioButtonEnable;

    @FXML
    private RadioButton radioButtonDisable;

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

        spinnerFrequency.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Constants.SYNC_DELAY_SECONDS, Integer.MAX_VALUE, 0));
        spinnerFrequency.getEditor().setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), Constants.SYNC_DELAY_SECONDS, InputHelper.INTEGER_FILTER));
    }

    public void bindInputs() {
        textFieldName.textProperty().bindBidirectional(viewModel.getEntry().nameProperty());
        textFieldFolderA.textProperty().bindBidirectional(viewModel.getEntry().folderAProperty());
        textFieldFolderB.textProperty().bindBidirectional(viewModel.getEntry().folderBProperty());
        comboBoxDirection.valueProperty().bindBidirectional(viewModel.getEntry().directionProperty());
        spinnerFrequency.getValueFactory().setValue(viewModel.getEntry().getFrequency() > 0 ? viewModel.getEntry().getFrequency() : 3600);
        spinnerFrequency.valueProperty().addListener(observable -> viewModel.getEntry().setFrequency(spinnerFrequency.getValue()));

        toggleFrequency.selectedToggleProperty().addListener(observable -> {
            RadioButton rb = (RadioButton) toggleFrequency.getSelectedToggle();

            if (rb != null) {
                if (rb == radioButtonManual) {
                    viewModel.getEntry().setFrequency(0);
                    spinnerFrequency.setDisable(true);
                } else if (rb == radioButtonAutomatic) {
                    viewModel.getEntry().setFrequency(spinnerFrequency.getValue());
                    spinnerFrequency.setDisable(false);
                }
            }
        });
        toggleFrequency.selectToggle(viewModel.getEntry().getFrequency() > 0 ? radioButtonAutomatic : radioButtonManual);

        toggleEnabled.selectedToggleProperty().addListener(observable -> {
            RadioButton rb = (RadioButton) toggleEnabled.getSelectedToggle();

            if (rb != null) {
                if (rb == radioButtonEnable) {
                    viewModel.getEntry().setEnabled(true);
                } else if (rb == radioButtonDisable) {
                    viewModel.getEntry().setEnabled(false);
                }
            }
        });
        toggleEnabled.selectToggle(viewModel.getEntry().isEnabled() ? radioButtonEnable : radioButtonDisable);
    }

    @FXML
    private void selectFolderA() {
        viewModel.selectFolder(EntryViewModel.Folder.A);
    }

    @FXML
    private void selectFolderB() {
        viewModel.selectFolder(EntryViewModel.Folder.B);
    }

    @FXML
    private void apply() {
        if (viewModel.applyChanges()) {
            close();
        }
    }

    @FXML
    private void close() {
        ((Stage) buttonCancel.getScene().getWindow()).close();
    }
}
