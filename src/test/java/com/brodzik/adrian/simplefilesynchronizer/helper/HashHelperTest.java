package com.brodzik.adrian.simplefilesynchronizer.helper;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

class HashHelperTest {
    @Test
    void testGetFileChecksum() throws Exception {
        Path path = Files.createTempDirectory("p");

        FileWriter writer = new FileWriter(Paths.get(path.toString(), "test.txt").toFile());
        writer.write("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        writer.close();

        String hash = HashHelper.getFileChecksum(Paths.get(path.toString(), "test.txt").toFile(), MessageDigest.getInstance("SHA-256"));
        Assertions.assertEquals("a58dd8680234c1f8cc2ef2b325a43733605a7f16f288e072de8eae81fd8d6433", hash);

        FileUtils.deleteDirectory(path.toFile());
    }

    @Test
    void testGetSHA256() throws Exception {
        Path path = Files.createTempDirectory("p");

        FileWriter writer = new FileWriter(Paths.get(path.toString(), "test.txt").toFile());
        writer.write("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        writer.close();

        String hash = HashHelper.getSHA256(Paths.get(path.toString(), "test.txt").toFile());
        Assertions.assertEquals("a58dd8680234c1f8cc2ef2b325a43733605a7f16f288e072de8eae81fd8d6433", hash);

        FileUtils.deleteDirectory(path.toFile());
    }
}
