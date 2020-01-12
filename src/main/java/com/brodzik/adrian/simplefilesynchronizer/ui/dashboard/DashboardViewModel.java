package com.brodzik.adrian.simplefilesynchronizer.ui.dashboard;

import com.brodzik.adrian.simplefilesynchronizer.App;
import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.handler.EntryHandler;
import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import com.brodzik.adrian.simplefilesynchronizer.ui.entry.EntryView;
import com.brodzik.adrian.simplefilesynchronizer.ui.entry.EntryViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class DashboardViewModel implements ViewModel {
    private final ObservableList<Entry> entries = FXCollections.observableArrayList(entry -> new Observable[]{
            entry.nameProperty(),
            entry.sourceProperty(),
            entry.destinationProperty(),
            entry.frequencyProperty(),
            entry.enabledProperty()
    });
    private final ObjectProperty<Entry> selectedEntry = new SimpleObjectProperty<>();

    public DashboardViewModel() {
        updateEntryList();
        EntryHandler.INSTANCE.addListener(this::updateEntryList);
    }

    private void updateEntryList() {
        List<Entry> allEntries = EntryHandler.INSTANCE.getEntries();
        entries.clear();
        allEntries.forEach(entry -> entries.add(new Entry(entry)));
    }

    public void addNewEntry() {
        ViewTuple<EntryView, EntryViewModel> entry = FluentViewLoader.fxmlView(EntryView.class).load();
        entry.getViewModel().setMode(EntryViewModel.Mode.ADD);
        entry.getCodeBehind().bindTextFields();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.primaryStage);
        stage.setScene(new Scene(entry.getView()));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.setTitle(Constants.ENTRY_NEW_TITLE);
        stage.show();
    }

    public void editSelectedEntry() {
        ViewTuple<EntryView, EntryViewModel> entry = FluentViewLoader.fxmlView(EntryView.class).load();
        entry.getViewModel().setMode(EntryViewModel.Mode.EDIT);
        entry.getViewModel().entryProperty().set(new Entry(getSelectedEntry()));
        entry.getCodeBehind().bindTextFields();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.primaryStage);
        stage.setScene(new Scene(entry.getView()));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.setTitle(Constants.ENTRY_EDIT_TITLE);
        stage.show();
    }

    public void removeSelectedEntry() {
        if (selectedEntry.get() != null) {
            EntryHandler.INSTANCE.remove(getSelectedEntry());
            updateEntryList();
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
