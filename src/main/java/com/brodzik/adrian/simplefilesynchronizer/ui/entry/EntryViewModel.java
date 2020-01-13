package com.brodzik.adrian.simplefilesynchronizer.ui.entry;

import com.brodzik.adrian.simplefilesynchronizer.App;
import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.data.SyncDirection;
import com.brodzik.adrian.simplefilesynchronizer.handler.EntryHandler;
import com.brodzik.adrian.simplefilesynchronizer.helper.InputHelper;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.Date;
import java.util.Objects;

public class EntryViewModel implements ViewModel {
    private final ObjectProperty<Entry> entry = new SimpleObjectProperty<>();
    private Mode mode = Mode.ADD;

    public EntryViewModel() {
        entry.set(new Entry(0, "", "", "", SyncDirection.UNIDIRECTIONAL_1, 0, true, new Date(0)));
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

    public void selectFolder(Folder folder) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(App.primaryStage);

        if (file != null) {
            switch (folder) {
                case A:
                    getEntry().setFolderA(file.getAbsolutePath());
                    break;
                case B:
                    getEntry().setFolderB(file.getAbsolutePath());
                    break;
                default:
                    break;
            }
        }
    }

    public boolean applyChanges() {
        if (InputHelper.isEmpty(getEntry().getName())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Name cannot be empty.");
            alert.showAndWait();
            return false;
        }

        if (!InputHelper.isDirectory(getEntry().getFolderA())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid source directory.");
            alert.showAndWait();
            return false;
        }

        if (!InputHelper.isDirectory(getEntry().getFolderB())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid destination directory.");
            alert.showAndWait();
            return false;
        }

        if (Objects.equals(getEntry().getFolderA(), getEntry().getFolderB())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Source and destination must be different.");
            alert.showAndWait();
            return false;
        }

        switch (getMode()) {
            case ADD:
                EntryHandler.INSTANCE.add(getEntry());
                break;
            case EDIT:
                EntryHandler.INSTANCE.update(getEntry());
                break;
        }

        return true;
    }

    public enum Mode {
        ADD,
        EDIT
    }

    public enum Folder {
        A,
        B
    }
}
