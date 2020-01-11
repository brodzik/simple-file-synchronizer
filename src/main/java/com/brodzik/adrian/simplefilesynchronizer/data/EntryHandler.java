package com.brodzik.adrian.simplefilesynchronizer.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EntryHandler {
    public static final EntryHandler INSTANCE = new EntryHandler();
    private final ArrayList<Entry> entries = new ArrayList<>();
    private final ArrayList<Listener> listeners = new ArrayList<>();

    private EntryHandler() {
    }

    public void add(Entry entry) {
        int id = entries.size() > 0 ? entries.get(entries.size() - 1).getId() + 1 : 0;
        entry.setId(id);
        entries.add(entry);
        callListeners();
    }

    public void edit(Entry entry) {
        entries.stream().filter(e -> e.getId() == entry.getId()).findFirst().ifPresent(e -> {
            e.setName(entry.getName());
            e.setSource(entry.getSource());
            e.setDestination(entry.getDestination());
            e.setFrequency(entry.getFrequency());
            e.setEnabled(entry.isEnabled());
            callListeners();
        });
    }

    public void remove(Entry entry) {
        entries.stream().filter(e -> e.getId() == entry.getId()).findFirst().ifPresentOrElse(entries::remove, () -> System.out.println("wtf"));
        callListeners();
    }

    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    private void callListeners() {
        for (Listener listener : listeners) {
            listener.onChanged();
        }
    }

    public interface Listener {
        void onChanged();
    }
}
