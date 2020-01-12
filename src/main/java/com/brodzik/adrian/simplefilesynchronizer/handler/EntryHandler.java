package com.brodzik.adrian.simplefilesynchronizer.handler;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EntryHandler implements Loadable {
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

    @Override
    public void load() {
        if (Files.exists(Constants.ENTRIES_FILE)) {
            try {
                FileReader reader = new FileReader(Constants.ENTRIES_FILE.toFile());

                JSONParser parser = new JSONParser();
                JSONArray array = (JSONArray) parser.parse(reader);

                for (Object object : array) {
                    JSONObject o = (JSONObject) object;
                    Entry entry = new Entry(Integer.parseInt(o.get("id").toString()),
                            (String) o.get("name"),
                            (String) o.get("source"),
                            (String) o.get("destination"),
                            (String) o.get("frequency"),
                            (boolean) o.get("enabled"));
                    entries.add(entry);
                }

                reader.close();
            } catch (Exception e) {
                System.out.println("Failed to load configuration. Configuration has been reset.");
                e.printStackTrace();
                save();
            }
        }
    }

    @Override
    public void save() {
        JSONArray array = new JSONArray();

        for (Entry entry : entries) {
            JSONObject object = new JSONObject();
            object.put("id", entry.getId());
            object.put("name", entry.getName());
            object.put("source", entry.getSource());
            object.put("destination", entry.getDestination());
            object.put("frequency", entry.getFrequency());
            object.put("enabled", entry.isEnabled());

            array.add(object);
        }

        try {
            FileWriter writer = new FileWriter(Constants.ENTRIES_FILE.toFile());
            writer.write(array.toJSONString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Entries saved.");
    }

    public interface Listener {
        void onChanged();
    }
}
