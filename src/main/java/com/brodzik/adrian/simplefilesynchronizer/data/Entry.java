package com.brodzik.adrian.simplefilesynchronizer.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Entry {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty source;
    private final StringProperty destination;
    private final StringProperty frequency;
    private final BooleanProperty enabled;

    public Entry(int id, String name, String source, String destination, String frequency, boolean enabled) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.source = new SimpleStringProperty(source);
        this.destination = new SimpleStringProperty(destination);
        this.frequency = new SimpleStringProperty(frequency);
        this.enabled = new SimpleBooleanProperty(enabled);
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

    public String getSource() {
        return source.get();
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public StringProperty sourceProperty() {
        return source;
    }

    public String getDestination() {
        return destination.get();
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public String getFrequency() {
        return frequency.get();
    }

    public void setFrequency(String frequency) {
        this.frequency.set(frequency);
    }

    public StringProperty frequencyProperty() {
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
}
