package com.brodzik.adrian.simplefilesynchronizer.concurrent;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.data.SyncDirection;
import com.brodzik.adrian.simplefilesynchronizer.handler.EntryHandler;
import com.brodzik.adrian.simplefilesynchronizer.handler.SyncHandler;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

class BackgroundSynchronizerTest {
    @Test
    void testEmptyRun() throws InterruptedException {
        EntryHandler.INSTANCE.getEntries().forEach(EntryHandler.INSTANCE::remove);

        BackgroundSynchronizer backgroundSynchronizer = new BackgroundSynchronizer();
        Thread thread = new Thread(backgroundSynchronizer);
        thread.start();

        Thread.sleep(1000);

        backgroundSynchronizer.stop();
        thread.join();
    }

    @Test
    void testRun() throws Exception {
        EntryHandler.INSTANCE.getEntries().forEach(EntryHandler.INSTANCE::remove);

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

        EntryHandler.INSTANCE.add(new Entry(0, "", pathA.toString(), pathB.toString(), SyncDirection.BIDIRECTIONAL, 5, true, new Date(0)));

        BackgroundSynchronizer backgroundSynchronizer = new BackgroundSynchronizer();
        Thread thread = new Thread(backgroundSynchronizer);
        thread.start();

        Thread.sleep(1000);

        backgroundSynchronizer.stop();
        thread.join();

        Assertions.assertTrue(Files.isRegularFile(Paths.get(pathA.toString(), "test1.txt")));
        Assertions.assertTrue(Files.isRegularFile(Paths.get(pathA.toString(), "test2.txt")));
        Assertions.assertTrue(Files.isRegularFile(Paths.get(pathB.toString(), "test1.txt")));
        Assertions.assertTrue(Files.isRegularFile(Paths.get(pathB.toString(), "test2.txt")));

        FileUtils.forceDelete(Paths.get(pathA.toString(), "test1.txt").toFile());
        FileUtils.forceDelete(Paths.get(pathA.toString(), "test2.txt").toFile());

        SyncHandler.INSTANCE.syncAll();

        Assertions.assertFalse(Files.isRegularFile(Paths.get(pathA.toString(), "test1.txt")));
        Assertions.assertFalse(Files.isRegularFile(Paths.get(pathA.toString(), "test2.txt")));
        Assertions.assertFalse(Files.isRegularFile(Paths.get(pathB.toString(), "test1.txt")));
        Assertions.assertFalse(Files.isRegularFile(Paths.get(pathB.toString(), "test2.txt")));

        FileUtils.deleteDirectory(pathA.toFile());
        FileUtils.deleteDirectory(pathB.toFile());
    }
}
