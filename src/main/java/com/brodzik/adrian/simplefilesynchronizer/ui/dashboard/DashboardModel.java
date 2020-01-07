package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardModel {
    private final ObservableList<Entry> entries = FXCollections.observableArrayList();

    public ObservableList<Entry> getEntries() {
        return entries;
    }
}
