package com.brodzik.adrian.simplefilesynchronizer.handler;

import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;

public final class ConfigurationHandler {
    public static final ConfigurationHandler INSTANCE = new ConfigurationHandler();

    public String setting1 = "xD";
    public String setting2 = "xDD";
    public String setting3 = "xDDD";

    private ConfigurationHandler() {
        if (Constants.APP_DIR.toFile().mkdirs()) {
            System.out.println("Created app directory: " + Constants.APP_DIR.toString());
        }
    }

    public void load() {
        if (Files.exists(Constants.CONFIG_FILE)) {
            try {
                FileReader reader = new FileReader(Constants.CONFIG_FILE.toFile());

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(reader);

                setting1 = (String) json.get("setting1");
                setting2 = (String) json.get("setting2");
                setting3 = (String) json.get("setting3");

                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        JSONObject json = new JSONObject();
        json.put("setting1", setting1);
        json.put("setting2", setting2);
        json.put("setting3", setting3);

        try {
            FileWriter writer = new FileWriter(Constants.CONFIG_FILE.toFile());
            writer.write(json.toJSONString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
