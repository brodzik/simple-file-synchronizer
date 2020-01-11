package com.brodzik.adrian.simplefilesynchronizer.ui.entry;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class EntryViewModel implements ViewModel {
    private final ObjectProperty<Entry> entry = new SimpleObjectProperty<>();
    private EntryViewMode mode = EntryViewMode.ADD;

    public EntryViewModel() {
        entry.set(new Entry(0, "", "", "", "", true));
    }

    public Entry getEntry() {
        return entry.get();
    }

    public ObjectProperty<Entry> entryProperty() {
        return entry;
    }

    public EntryViewMode getMode() {
        return mode;
    }

    public void setMode(EntryViewMode mode) {
        this.mode = mode;
    }
}
