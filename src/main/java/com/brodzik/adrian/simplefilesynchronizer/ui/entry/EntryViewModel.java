package com.brodzik.adrian.simplefilesynchronizer.ui.entry;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

public class EntryViewModel implements ViewModel {
    private final ObjectProperty<Entry> entry = new SimpleObjectProperty<>();

    public EntryViewModel() {
        entry.set(new Entry(0, "", "", "", "", true));
    }

    public Entry getEntry() {
        return entry.get();
    }

    public ObservableValue<Entry> entryProperty() {
        return entry;
    }
}
