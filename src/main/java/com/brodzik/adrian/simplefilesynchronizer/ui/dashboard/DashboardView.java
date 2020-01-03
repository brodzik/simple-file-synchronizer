package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.model.Entry;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DashboardView implements FxmlView<DashboardViewModel> {
    @FXML
    private TableView<Entry> tableView;

    @FXML
    private TableColumn<Entry, Integer> idColumn;

    @FXML
    private TableColumn<Entry, String> nameColumn;

    @FXML
    private TableColumn<Entry, String> sourceColumn;

    @FXML
    private TableColumn<Entry, String> destinationColumn;

    @FXML
    private TableColumn<Entry, String> frequencyColumn;

    @FXML
    private TableColumn<Entry, Boolean> enabledColumn;

    @InjectViewModel
    private DashboardViewModel viewModel;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sourceColumn.setCellValueFactory(cellData -> cellData.getValue().sourceProperty());
        destinationColumn.setCellValueFactory(cellData -> cellData.getValue().destinationProperty());
        frequencyColumn.setCellValueFactory(cellData -> cellData.getValue().frequencyProperty());
        enabledColumn.setCellValueFactory(cellData -> cellData.getValue().enabledProperty());

        tableView.setItems(viewModel.getEntries());
    }

    @FXML
    private void createNew(ActionEvent event) {
        System.out.println("NaM");
    }

    @FXML
    private void showSettings(ActionEvent event) {
        System.out.println("Settings");
    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }
}
