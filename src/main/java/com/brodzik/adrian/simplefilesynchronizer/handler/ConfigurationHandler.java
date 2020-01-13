package com.brodzik.adrian.simplefilesynchronizer.handler;

import com.brodzik.adrian.simplefilesynchronizer.data.Layout;
import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;

public final class ConfigurationHandler implements Loadable {
    public static final ConfigurationHandler INSTANCE = new ConfigurationHandler();
    private static Logger LOGGER = LoggerFactory.getLogger(ConfigurationHandler.class);
    public Layout layout = new Layout(1280, 720, 0.85, 100, 200, 200, 70, 150, 70, 125);

    private ConfigurationHandler() {
    }

    @Override
    public void load() {
        if (Files.exists(Constants.CONFIG_FILE)) {
            try {
                FileReader reader = new FileReader(Constants.CONFIG_FILE.toFile());

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(reader);

                layout = new Layout(
                        Double.parseDouble(json.get("width").toString()),
                        Double.parseDouble(json.get("height").toString()),
                        Double.parseDouble(json.get("dividerPosition").toString()),
                        Double.parseDouble(json.get("columnWidthName").toString()),
                        Double.parseDouble(json.get("columnWidthFolderA").toString()),
                        Double.parseDouble(json.get("columnWidthFolderB").toString()),
                        Double.parseDouble(json.get("columnWidthDirection").toString()),
                        Double.parseDouble(json.get("columnWidthFrequency").toString()),
                        Double.parseDouble(json.get("columnWidthEnabled").toString()),
                        Double.parseDouble(json.get("columnWidthLastSync").toString())
                );

                reader.close();
            } catch (Exception e) {
                LOGGER.error("Failed to load configuration.");
                LOGGER.error(e.getLocalizedMessage());
                save();
            }
        }
    }

    @Override
    public void save() {
        JSONObject json = new JSONObject();
        json.put("width", layout.getWidth());
        json.put("height", layout.getHeight());
        json.put("dividerPosition", layout.getDividerPosition());
        json.put("columnWidthName", layout.getColumnWidthName());
        json.put("columnWidthFolderA", layout.getColumnWidthFolderA());
        json.put("columnWidthFolderB", layout.getColumnWidthFolderB());
        json.put("columnWidthDirection", layout.getColumnWidthDirection());
        json.put("columnWidthFrequency", layout.getColumnWidthFrequency());
        json.put("columnWidthEnabled", layout.getColumnWidthEnabled());
        json.put("columnWidthLastSync", layout.getColumnWidthLastSync());

        try {
            FileWriter writer = new FileWriter(Constants.CONFIG_FILE.toFile());
            writer.write(json.toJSONString());
            writer.close();
            LOGGER.info("Configuration saved.");
        } catch (Exception e) {
            LOGGER.error("Failed to save configuration.");
            LOGGER.error(e.getLocalizedMessage());
        }
    }
}
