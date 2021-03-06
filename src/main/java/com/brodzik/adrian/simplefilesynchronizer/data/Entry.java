package com.brodzik.adrian.simplefilesynchronizer.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Entry {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty folderA;
    private final StringProperty folderB;
    private final ObjectProperty<SyncDirection> direction;
    private final IntegerProperty frequency;
    private final BooleanProperty enabled;
    private final ObjectProperty<Date> lastSync;

    public Entry(int id, String name, String folderA, String folderB, SyncDirection direction, int frequency, boolean enabled, Date lastSync) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.folderA = new SimpleStringProperty(folderA);
        this.folderB = new SimpleStringProperty(folderB);
        this.direction = new SimpleObjectProperty<>(direction);
        this.frequency = new SimpleIntegerProperty(frequency);
        this.enabled = new SimpleBooleanProperty(enabled);
        this.lastSync = new SimpleObjectProperty<>(lastSync);
    }

    public Entry(Entry entry) {
        this(entry.getId(), entry.getName(), entry.getFolderA(), entry.getFolderB(), entry.getDirection(), entry.getFrequency(), entry.isEnabled(), entry.getLastSync());
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getFolderA() {
        return folderA.get();
    }

    public void setFolderA(String folderA) {
        this.folderA.set(folderA);
    }

    public StringProperty folderAProperty() {
        return folderA;
    }

    public String getFolderB() {
        return folderB.get();
    }

    public void setFolderB(String folderB) {
        this.folderB.set(folderB);
    }

    public StringProperty folderBProperty() {
        return folderB;
    }

    public SyncDirection getDirection() {
        return direction.get();
    }

    public void setDirection(SyncDirection direction) {
        this.direction.set(direction);
    }

    public ObjectProperty<SyncDirection> directionProperty() {
        return direction;
    }

    public int getFrequency() {
        return frequency.get();
    }

    public void setFrequency(int frequency) {
        this.frequency.set(frequency);
    }

    public IntegerProperty frequencyProperty() {
        return frequency;
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    public BooleanProperty enabledProperty() {
        return enabled;
    }

    public Date getLastSync() {
        return lastSync.get();
    }

    public void setLastSync(Date lastSync) {
        this.lastSync.set(lastSync);
    }

    public ObjectProperty<Date> lastSyncProperty() {
        return lastSync;
    }
}
