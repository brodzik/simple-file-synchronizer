package com.brodzik.adrian.simplefilesynchronizer.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Layout {
    private final DoubleProperty width;
    private final DoubleProperty height;
    private final DoubleProperty dividerPosition;
    private final DoubleProperty columnWidthName;
    private final DoubleProperty columnWidthFolderA;
    private final DoubleProperty columnWidthFolderB;
    private final DoubleProperty columnWidthDirection;
    private final DoubleProperty columnWidthFrequency;
    private final DoubleProperty columnWidthEnabled;
    private final DoubleProperty columnWidthLastSync;

    public Layout(double width, double height, double dividerPosition, double columnWidthName, double columnWidthFolderA, double columnWidthFolderB, double columnWidthDirection, double columnWidthFrequency, double columnWidthEnabled, double columnWidthLastSync) {
        this.width = new SimpleDoubleProperty(width);
        this.height = new SimpleDoubleProperty(height);
        this.dividerPosition = new SimpleDoubleProperty(dividerPosition);
        this.columnWidthName = new SimpleDoubleProperty(columnWidthName);
        this.columnWidthFolderA = new SimpleDoubleProperty(columnWidthFolderA);
        this.columnWidthFolderB = new SimpleDoubleProperty(columnWidthFolderB);
        this.columnWidthDirection = new SimpleDoubleProperty(columnWidthDirection);
        this.columnWidthFrequency = new SimpleDoubleProperty(columnWidthFrequency);
        this.columnWidthEnabled = new SimpleDoubleProperty(columnWidthEnabled);
        this.columnWidthLastSync = new SimpleDoubleProperty(columnWidthLastSync);
    }

    public double getWidth() {
        return width.get();
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public double getHeight() {
        return height.get();
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public double getDividerPosition() {
        return dividerPosition.get();
    }

    public void setDividerPosition(double dividerPosition) {
        this.dividerPosition.set(dividerPosition);
    }

    public DoubleProperty dividerPositionProperty() {
        return dividerPosition;
    }

    public double getColumnWidthName() {
        return columnWidthName.get();
    }

    public void setColumnWidthName(double columnWidthName) {
        this.columnWidthName.set(columnWidthName);
    }

    public DoubleProperty columnWidthNameProperty() {
        return columnWidthName;
    }

    public double getColumnWidthFolderA() {
        return columnWidthFolderA.get();
    }

    public void setColumnWidthFolderA(double columnWidthFolderA) {
        this.columnWidthFolderA.set(columnWidthFolderA);
    }

    public DoubleProperty columnWidthFolderAProperty() {
        return columnWidthFolderA;
    }

    public double getColumnWidthFolderB() {
        return columnWidthFolderB.get();
    }

    public void setColumnWidthFolderB(double columnWidthFolderB) {
        this.columnWidthFolderB.set(columnWidthFolderB);
    }

    public DoubleProperty columnWidthFolderBProperty() {
        return columnWidthFolderB;
    }

    public double getColumnWidthDirection() {
        return columnWidthDirection.get();
    }

    public void setColumnWidthDirection(double columnWidthDirection) {
        this.columnWidthDirection.set(columnWidthDirection);
    }

    public DoubleProperty columnWidthDirectionProperty() {
        return columnWidthDirection;
    }

    public double getColumnWidthFrequency() {
        return columnWidthFrequency.get();
    }

    public void setColumnWidthFrequency(double columnWidthFrequency) {
        this.columnWidthFrequency.set(columnWidthFrequency);
    }

    public DoubleProperty columnWidthFrequencyProperty() {
        return columnWidthFrequency;
    }

    public double getColumnWidthEnabled() {
        return columnWidthEnabled.get();
    }

    public void setColumnWidthEnabled(double columnWidthEnabled) {
        this.columnWidthEnabled.set(columnWidthEnabled);
    }

    public DoubleProperty columnWidthEnabledProperty() {
        return columnWidthEnabled;
    }

    public double getColumnWidthLastSync() {
        return columnWidthLastSync.get();
    }

    public void setColumnWidthLastSync(double columnWidthLastSync) {
        this.columnWidthLastSync.set(columnWidthLastSync);
    }

    public DoubleProperty columnWidthLastSyncProperty() {
        return columnWidthLastSync;
    }


}
