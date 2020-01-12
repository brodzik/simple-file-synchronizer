package com.brodzik.adrian.simplefilesynchronizer.handler;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.data.SyncDirection;
import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public final class EntryHandler implements Loadable, Listenable {
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

    public void update(Entry entry) {
        entries.stream().filter(e -> e.getId() == entry.getId()).findFirst().ifPresent(e -> {
            e.setName(entry.getName());
            e.setFolderA(entry.getFolderA());
            e.setFolderB(entry.getFolderB());
            e.setDirection(entry.getDirection());
            e.setFrequency(entry.getFrequency());
            e.setEnabled(entry.isEnabled());
            e.setLastSync(entry.getLastSync());
            callListeners();
        });
    }

    public void remove(Entry entry) {
        entries.stream().filter(e -> e.getId() == entry.getId()).findFirst().ifPresent(entries::remove);
        callListeners();
    }

    public final List<Entry> getEntries() {
        List<Entry> copy = new ArrayList<>();
        entries.forEach(entry -> copy.add(new Entry(entry)));
        return Collections.unmodifiableList(copy);
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    private void callListeners() {
        listeners.forEach(Listener::onUpdate);
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
                            (String) o.get("folderA"),
                            (String) o.get("folderB"),
                            SyncDirection.valueOf(o.get("direction").toString()),
                            (String) o.get("frequency"),
                            (boolean) o.get("enabled"),
                            new Date(Long.parseLong(o.get("lastSync").toString())));
                    entries.add(entry);
                }

                reader.close();
            } catch (Exception e) {
                System.out.println("Failed to load entries.");
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
            object.put("folderA", entry.getFolderA());
            object.put("folderB", entry.getFolderB());
            object.put("direction", entry.getDirection().toString());
            object.put("frequency", entry.getFrequency());
            object.put("enabled", entry.isEnabled());
            object.put("lastSync", entry.getLastSync().getTime());

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
}
