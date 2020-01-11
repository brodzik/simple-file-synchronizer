package com.brodzik.adrian.simplefilesynchronizer.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntryHandler {
    public static final EntryHandler INSTANCE = new EntryHandler();

    private final ArrayList<Entry> entries = new ArrayList<>();

    private EntryHandler() {
    }

    public void add(Entry entry) {
        entries.add(entry);
    }

    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
