package com.brodzik.adrian.simplefilesynchronizer.ui.entry;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.data.SyncDirection;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class EntryViewModel implements ViewModel {
    private final ObjectProperty<Entry> entry = new SimpleObjectProperty<>();
    private Mode mode = Mode.ADD;

    public EntryViewModel() {
        entry.set(new Entry(0, "", "", "", SyncDirection.UNIDIRECTIONAL_1, "", true, new Date(0)));
    }

    public Entry getEntry() {
        return entry.get();
    }

    public ObjectProperty<Entry> entryProperty() {
        return entry;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public enum Mode {
        ADD,
        EDIT
    }
}
