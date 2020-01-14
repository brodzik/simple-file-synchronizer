package com.brodzik.adrian.simplefilesynchronizer.helper;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class InputHelperTest {
    @Test
    void testIsEmpty() {
        Assertions.assertTrue(InputHelper.isEmpty(null));
        Assertions.assertTrue(InputHelper.isEmpty(""));
        Assertions.assertTrue(InputHelper.isEmpty("     "));
        Assertions.assertFalse(InputHelper.isEmpty("null"));
        Assertions.assertFalse(InputHelper.isEmpty("     t     "));
    }

    @Test
    void testIsDirectory() throws IOException {
        Path path = Files.createTempDirectory("a");

        Assertions.assertTrue(InputHelper.isDirectory(path.toString()));

        FileUtils.deleteDirectory(path.toFile());
    }
}
