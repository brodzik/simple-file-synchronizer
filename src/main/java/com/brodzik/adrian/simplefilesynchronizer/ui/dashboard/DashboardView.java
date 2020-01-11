package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class DashboardView implements FxmlView<DashboardViewModel> {
    @FXML
    private TableView<Entry> entryTable;

    @FXML
    private Button buttonEditEntry;

    @FXML
    private Button buttonRemoveEntry;

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
        buttonEditEntry.disableProperty().bind(Bindings.isNull(viewModel.selectedEntryProperty()));
        buttonRemoveEntry.disableProperty().bind(Bindings.isNull(viewModel.selectedEntryProperty()));

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
    private void editSettings() {
    }

    @FXML
    private void syncNow() {
    }
}
