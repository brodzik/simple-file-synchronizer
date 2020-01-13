package com.brodzik.adrian.simplefilesynchronizer.handler;

import com.brodzik.adrian.simplefilesynchronizer.data.Layout;
import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigurationHandlerTest {
    @Test
    void testSaveLoad() {
        Constants.APP_DIR.toFile().mkdirs();

        ConfigurationHandler.INSTANCE.layout = new Layout(1920, 1080, 0.5, 10, 20, 30, 40, 50, 60, 70);
        ConfigurationHandler.INSTANCE.save();
        ConfigurationHandler.INSTANCE.layout = new Layout(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        ConfigurationHandler.INSTANCE.load();

        Assertions.assertEquals(1920, ConfigurationHandler.INSTANCE.layout.getWidth());
        Assertions.assertEquals(1080, ConfigurationHandler.INSTANCE.layout.getHeight());
        Assertions.assertEquals(0.5, ConfigurationHandler.INSTANCE.layout.getDividerPosition());
        Assertions.assertEquals(10, ConfigurationHandler.INSTANCE.layout.getColumnWidthName());
        Assertions.assertEquals(20, ConfigurationHandler.INSTANCE.layout.getColumnWidthFolderA());
        Assertions.assertEquals(30, ConfigurationHandler.INSTANCE.layout.getColumnWidthFolderB());
        Assertions.assertEquals(40, ConfigurationHandler.INSTANCE.layout.getColumnWidthDirection());
        Assertions.assertEquals(50, ConfigurationHandler.INSTANCE.layout.getColumnWidthFrequency());
        Assertions.assertEquals(60, ConfigurationHandler.INSTANCE.layout.getColumnWidthEnabled());
        Assertions.assertEquals(70, ConfigurationHandler.INSTANCE.layout.getColumnWidthLastSync());
    }
}
