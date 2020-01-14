package com.brodzik.adrian.simplefilesynchronizer.handler;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.data.SyncDirection;
import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

class SyncHandlerTest {
    @Test
    void testSyncUnidirectional() throws IOException {
        SyncHandler.INSTANCE.fileList.clear();

        Path pathA = Files.createTempDirectory("a");
        Path pathB = Files.createTempDirectory("b");

        {
            FileWriter writer = new FileWriter(Paths.get(pathA.toString(), "test1.txt").toFile());
            writer.write("test 1");
            writer.close();
        }

        {
            FileWriter writer = new FileWriter(Paths.get(pathB.toString(), "test2.txt").toFile());
            writer.write("test 2");
            writer.close();
        }

        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.UNIDIRECTIONAL_1, 0, true, new Date(0)));
        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.UNIDIRECTIONAL_1, 0, true, new Date(0)));

        Assertions.assertTrue(Paths.get(pathA.toString(), "test1.txt").toFile().isFile());
        Assertions.assertFalse(Paths.get(pathA.toString(), "test2.txt").toFile().isFile());
        Assertions.assertTrue(Paths.get(pathB.toString(), "test1.txt").toFile().isFile());
        Assertions.assertFalse(Paths.get(pathB.toString(), "test2.txt").toFile().isFile());

        FileUtils.forceDelete(Paths.get(pathA.toString(), "test1.txt").toFile());

        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.UNIDIRECTIONAL_1, 0, true, new Date(0)));
        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.UNIDIRECTIONAL_1, 0, true, new Date(0)));

        Assertions.assertFalse(Paths.get(pathA.toString(), "test1.txt").toFile().isFile());
        Assertions.assertFalse(Paths.get(pathA.toString(), "test2.txt").toFile().isFile());
        Assertions.assertFalse(Paths.get(pathB.toString(), "test1.txt").toFile().isFile());
        Assertions.assertFalse(Paths.get(pathB.toString(), "test2.txt").toFile().isFile());

        {
            FileWriter writer = new FileWriter(Paths.get(pathB.toString(), "test3.txt").toFile());
            writer.write("test 3");
            writer.close();
        }

        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.UNIDIRECTIONAL_2, 0, true, new Date(0)));
        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.UNIDIRECTIONAL_2, 0, true, new Date(0)));

        Assertions.assertTrue(Paths.get(pathA.toString(), "test3.txt").toFile().isFile());
        Assertions.assertTrue(Paths.get(pathB.toString(), "test3.txt").toFile().isFile());

        FileUtils.deleteDirectory(pathA.toFile());
        FileUtils.deleteDirectory(pathB.toFile());
    }

    @Test
    void testSyncBidirectional() throws IOException {
        SyncHandler.INSTANCE.fileList.clear();

        Path pathA = Files.createTempDirectory("a");
        Path pathB = Files.createTempDirectory("b");

        {
            FileWriter writer = new FileWriter(Paths.get(pathA.toString(), "test1.txt").toFile());
            writer.write("test 1");
            writer.close();
        }

        {
            FileWriter writer = new FileWriter(Paths.get(pathB.toString(), "test2.txt").toFile());
            writer.write("test 2");
            writer.close();
        }

        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.BIDIRECTIONAL, 0, true, new Date(0)));
        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.BIDIRECTIONAL, 0, true, new Date(0)));

        Assertions.assertTrue(Paths.get(pathA.toString(), "test2.txt").toFile().isFile());
        Assertions.assertTrue(Paths.get(pathB.toString(), "test1.txt").toFile().isFile());

        FileUtils.forceDelete(Paths.get(pathA.toString(), "test1.txt").toFile());
        FileUtils.forceDelete(Paths.get(pathA.toString(), "test2.txt").toFile());

        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.BIDIRECTIONAL, 0, true, new Date(0)));
        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.BIDIRECTIONAL, 0, true, new Date(0)));

        Assertions.assertFalse(Paths.get(pathA.toString(), "test1.txt").toFile().isFile());
        Assertions.assertFalse(Paths.get(pathA.toString(), "test2.txt").toFile().isFile());
        Assertions.assertFalse(Paths.get(pathB.toString(), "test1.txt").toFile().isFile());
        Assertions.assertFalse(Paths.get(pathB.toString(), "test2.txt").toFile().isFile());

        {
            FileWriter writer = new FileWriter(Paths.get(pathA.toString(), "test1.txt").toFile());
            writer.write("test 1");
            writer.close();
        }

        {
            FileWriter writer = new FileWriter(Paths.get(pathB.toString(), "test2.txt").toFile());
            writer.write("test 2");
            writer.close();
        }

        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.BIDIRECTIONAL, 0, true, new Date(0)));

        {
            FileWriter writer = new FileWriter(Paths.get(pathB.toString(), "test2.txt").toFile());
            writer.write("test 2 modified");
            writer.close();
        }

        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.BIDIRECTIONAL, 0, true, new Date(0)));

        {
            BufferedReader reader = new BufferedReader(new FileReader(Paths.get(pathA.toString(), "test2.txt").toFile()));
            Assertions.assertEquals("test 2 modified", reader.readLine());
            reader.close();
        }

        {
            FileWriter writer = new FileWriter(Paths.get(pathA.toString(), "test2.txt").toFile());
            writer.write("test 2 modified now");
            writer.close();
        }

        {
            FileWriter writer = new FileWriter(Paths.get(pathB.toString(), "test2.txt").toFile());
            writer.write("test 2 modified later");
            writer.close();
        }

        SyncHandler.INSTANCE.sync(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.BIDIRECTIONAL, 0, true, new Date(1)));

        {
            BufferedReader reader = new BufferedReader(new FileReader(Paths.get(pathA.toString(), "test2.txt").toFile()));
            Assertions.assertEquals("test 2 modified later", reader.readLine());
            reader.close();
        }

        FileUtils.deleteDirectory(pathA.toFile());
        FileUtils.deleteDirectory(pathB.toFile());
    }

    @Test
    void testSaveLoad() throws IOException {
        Constants.APP_DIR.toFile().mkdirs();

        SyncHandler.INSTANCE.fileList.clear();

        Assertions.assertEquals(0, SyncHandler.INSTANCE.fileList.size());

        SyncHandler.INSTANCE.fileList.put("a", null);
        SyncHandler.INSTANCE.fileList.put("b", new ArrayList<>());
        SyncHandler.INSTANCE.fileList.put("c", Arrays.asList("d", "e", "f"));
        SyncHandler.INSTANCE.save();
        SyncHandler.INSTANCE.fileList.clear();

        Assertions.assertEquals(0, SyncHandler.INSTANCE.fileList.size());

        SyncHandler.INSTANCE.load();

        Assertions.assertEquals(3, SyncHandler.INSTANCE.fileList.size());
        Assertions.assertTrue(SyncHandler.INSTANCE.fileList.containsKey("a"));
        Assertions.assertTrue(SyncHandler.INSTANCE.fileList.containsKey("b"));
        Assertions.assertTrue(SyncHandler.INSTANCE.fileList.containsKey("c"));
        Assertions.assertNull(SyncHandler.INSTANCE.fileList.get("a"));
        Assertions.assertEquals(0, SyncHandler.INSTANCE.fileList.get("b").size());
        Assertions.assertEquals(Arrays.asList("d", "e", "f"), SyncHandler.INSTANCE.fileList.get("c"));

        FileUtils.deleteDirectory(Constants.APP_DIR.toFile());
    }
}
