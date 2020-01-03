package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.model.Entry;
import de.saxsys.mvvmfx.ViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardViewModel implements ViewModel {
    private ObservableList<Entry> entries = FXCollections.observableArrayList();

    public DashboardViewModel() {
        for (int i = 0; i < 100; ++i) {
            entries.add(new Entry(i, "a", "b", "c", "d", true));
        }
    }

    public ObservableList<Entry> getEntries() {
        return entries;
    }
}
