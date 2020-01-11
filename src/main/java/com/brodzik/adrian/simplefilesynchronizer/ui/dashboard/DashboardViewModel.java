package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardViewModel implements ViewModel {
    private final ObservableList<Entry> entries = FXCollections.observableArrayList(entry -> new Observable[]{
            entry.idProperty(),
            entry.nameProperty(),
            entry.sourceProperty(),
            entry.destinationProperty(),
            entry.frequencyProperty(),
            entry.enabledProperty()
    });
    private final ObjectProperty<Entry> selectedEntry = new SimpleObjectProperty<>();

    public DashboardViewModel() {
        entries.add(new Entry(0, "a", "b", "c", "d", true));
        entries.add(new Entry(1, "a", "b", "c", "d", true));
        entries.add(new Entry(2, "a", "b", "c", "d", true));
        entries.add(new Entry(3, "a", "b", "c", "d", true));
        entries.add(new Entry(4, "a", "b", "c", "d", true));
    }

    public void removeSelectedEntry() {
        if (selectedEntry.get() != null) {
            entries.remove(selectedEntry.get());
        }
    }

    public ObservableList<Entry> getEntries() {
        return entries;
    }

    public Entry getSelectedEntry() {
        return selectedEntry.get();
    }

    public ObjectProperty<Entry> selectedEntryProperty() {
        return selectedEntry;
    }
}
