package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.App;
import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.handler.SyncHandler;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DashboardView implements FxmlView<DashboardViewModel> {
    private static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @FXML
    private TableView<Entry> entryTable;

    @FXML
    private TableColumn<Entry, String> tableColumnDirection;

    @FXML
    private TableColumn<Entry, String> tableColumnEnabled;

    @FXML
    private TableColumn<Entry, String> tableColumnLastSync;

    @FXML
    private Button buttonEditEntry;

    @FXML
    private Button buttonRemoveEntry;

    @FXML
    private Button buttonSync;

    @FXML
    private Button buttonSyncAll;

    @InjectViewModel
    private DashboardViewModel viewModel;

    @FXML
    public void initialize() {
        entryTable.setItems(viewModel.getEntries());
        entryTable.setRowFactory(entryTableView -> {
            TableRow<Entry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    editEntry();
                }
            });
            return row;
        });

        tableColumnDirection.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDirection().getSymbol()));
        tableColumnEnabled.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isEnabled() ? "Yes" : "No"));
        tableColumnLastSync.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastSync().getTime() == 0 ? "Never" : DashboardView.DATETIME_FORMAT.format(data.getValue().getLastSync())));

        buttonEditEntry.disableProperty().bind(Bindings.isNull(viewModel.selectedEntryProperty()));
        buttonRemoveEntry.disableProperty().bind(Bindings.isNull(viewModel.selectedEntryProperty()));
        buttonSync.disableProperty().bind(Bindings.isNull(viewModel.selectedEntryProperty()));
        buttonSyncAll.disableProperty().bind(Bindings.isEmpty(viewModel.getEntries()));

        viewModel.selectedEntryProperty().bind(entryTable.getSelectionModel().selectedItemProperty());
        viewModel.getEntries().addListener((InvalidationListener) change -> entryTable.refresh());
    }

    @FXML
    private void newEntry() {
        viewModel.addNewEntry();
    }

    @FXML
    private void editEntry() {
        viewModel.editSelectedEntry();
    }

    @FXML
    private void removeEntry() {
        viewModel.removeSelectedEntry();
    }

    @FXML
    private void sync() {
        viewModel.syncSelectedEntry();
    }

    @FXML
    private void syncAll() {
        SyncHandler.INSTANCE.syncAll();
    }

    @FXML
    private void editSettings() {
    }

    @FXML
    private void minimizeToTray() {
        App.primaryStage.hide();
    }

    @FXML
    private void exit() {
        Platform.exit();
    }
}
