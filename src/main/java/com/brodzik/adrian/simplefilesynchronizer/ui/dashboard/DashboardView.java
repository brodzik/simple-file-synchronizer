package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class DashboardView implements FxmlView<DashboardViewModel> {
    @FXML
    private TableView<Entry> entryTable;

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
        viewModel.selectedEntryProperty().bind(entryTable.getSelectionModel().selectedItemProperty());
        viewModel.getEntries().addListener((InvalidationListener) change -> entryTable.refresh());
    }

    @FXML
    private void newEntry() {
    }

    @FXML
    private void editEntry() {
    }

    @FXML
    private void removeEntry() {
    }

    @FXML
    private void editSettings() {
    }

    @FXML
    private void syncNow() {
    }
}
