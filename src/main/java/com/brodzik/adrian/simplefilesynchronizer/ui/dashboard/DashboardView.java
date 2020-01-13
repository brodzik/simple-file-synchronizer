package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.App;
import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.handler.ConfigurationHandler;
import com.brodzik.adrian.simplefilesynchronizer.handler.SyncHandler;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DashboardView implements FxmlView<DashboardViewModel> {
    private static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @FXML
    private SplitPane splitPane;

    @FXML
    private TableView<Entry> entryTable;

    @FXML
    private TableColumn<Entry, String> tableColumnName;

    @FXML
    private TableColumn<Entry, String> tableColumnFolderA;

    @FXML
    private TableColumn<Entry, String> tableColumnFolderB;

    @FXML
    private TableColumn<Entry, String> tableColumnDirection;

    @FXML
    private TableColumn<Entry, String> tableColumnFrequency;

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

    @FXML
    private Button buttonMinimizeToTray;

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

        splitPane.getDividers().stream().findFirst().ifPresent(divider -> divider.positionProperty().bindBidirectional(ConfigurationHandler.INSTANCE.layout.dividerPositionProperty()));

        tableColumnName.setPrefWidth(ConfigurationHandler.INSTANCE.layout.getColumnWidthName());
        tableColumnFolderA.setPrefWidth(ConfigurationHandler.INSTANCE.layout.getColumnWidthFolderA());
        tableColumnFolderB.setPrefWidth(ConfigurationHandler.INSTANCE.layout.getColumnWidthFolderB());
        tableColumnDirection.setPrefWidth(ConfigurationHandler.INSTANCE.layout.getColumnWidthDirection());
        tableColumnFrequency.setPrefWidth(ConfigurationHandler.INSTANCE.layout.getColumnWidthFrequency());
        tableColumnEnabled.setPrefWidth(ConfigurationHandler.INSTANCE.layout.getColumnWidthEnabled());
        tableColumnLastSync.setPrefWidth(ConfigurationHandler.INSTANCE.layout.getColumnWidthLastSync());

        tableColumnName.widthProperty().addListener(observable -> ConfigurationHandler.INSTANCE.layout.setColumnWidthName(tableColumnName.getWidth()));
        tableColumnFolderA.widthProperty().addListener(observable -> ConfigurationHandler.INSTANCE.layout.setColumnWidthFolderA(tableColumnFolderA.getWidth()));
        tableColumnFolderB.widthProperty().addListener(observable -> ConfigurationHandler.INSTANCE.layout.setColumnWidthFolderB(tableColumnFolderB.getWidth()));
        tableColumnDirection.widthProperty().addListener(observable -> ConfigurationHandler.INSTANCE.layout.setColumnWidthDirection(tableColumnDirection.getWidth()));
        tableColumnFrequency.widthProperty().addListener(observable -> ConfigurationHandler.INSTANCE.layout.setColumnWidthFrequency(tableColumnFrequency.getWidth()));
        tableColumnEnabled.widthProperty().addListener(observable -> ConfigurationHandler.INSTANCE.layout.setColumnWidthEnabled(tableColumnEnabled.getWidth()));
        tableColumnLastSync.widthProperty().addListener(observable -> ConfigurationHandler.INSTANCE.layout.setColumnWidthLastSync(tableColumnLastSync.getWidth()));

        tableColumnDirection.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDirection().getSymbol()));
        tableColumnEnabled.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isEnabled() ? "Yes" : "No"));
        tableColumnLastSync.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLastSync().getTime() == 0 ? "Never" : DashboardView.DATETIME_FORMAT.format(data.getValue().getLastSync())));
        tableColumnFrequency.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFrequency() > 0 ? String.format("every %s seconds", data.getValue().getFrequency()) : "Manual"));

        buttonEditEntry.disableProperty().bind(Bindings.isNull(viewModel.selectedEntryProperty()));
        buttonRemoveEntry.disableProperty().bind(Bindings.isNull(viewModel.selectedEntryProperty()));
        buttonSync.disableProperty().bind(Bindings.isNull(viewModel.selectedEntryProperty()));
        buttonSyncAll.disableProperty().bind(Bindings.isEmpty(viewModel.getEntries()));

        buttonMinimizeToTray.setDisable(!App.hasTray);

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
    private void minimizeToTray() {
        App.primaryStage.hide();
    }

    @FXML
    private void exit() {
        Platform.exit();
    }
}
