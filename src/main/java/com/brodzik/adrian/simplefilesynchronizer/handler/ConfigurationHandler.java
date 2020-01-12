package com.brodzik.adrian.simplefilesynchronizer.handler;

import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;

public final class ConfigurationHandler implements Loadable {
    public static final ConfigurationHandler INSTANCE = new ConfigurationHandler();

    public double width;
    public double height;
    public boolean runOnStartup;

    private ConfigurationHandler() {
        if (Constants.APP_DIR.toFile().mkdirs()) {
            System.out.println("Created app directory: " + Constants.APP_DIR.toString());
        }
    }

    public void reset() {
        width = 1280;
        height = 720;
        runOnStartup = false;
    }

    @Override
    public void load() {
        if (Files.exists(Constants.CONFIG_FILE)) {
            try {
                FileReader reader = new FileReader(Constants.CONFIG_FILE.toFile());

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(reader);

                width = (double) json.get("width");
                height = (double) json.get("height");
                runOnStartup = (boolean) json.get("runOnStartup");

                reader.close();
            } catch (Exception e) {
                System.out.println("Failed to load configuration. Configuration has been reset.");
                e.printStackTrace();
                reset();
                save();
            }
        }
    }

    @Override
    public void save() {
        JSONObject json = new JSONObject();
        json.put("width", width);
        json.put("height", height);
        json.put("runOnStartup", runOnStartup);

        try {
            FileWriter writer = new FileWriter(Constants.CONFIG_FILE.toFile());
            writer.write(json.toJSONString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Config saved.");
    }
}
