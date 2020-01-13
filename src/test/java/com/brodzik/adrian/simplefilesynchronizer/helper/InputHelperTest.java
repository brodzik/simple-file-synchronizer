package com.brodzik.adrian.simplefilesynchronizer.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

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
    void testIsDirectory() {
        File file = new File("a/b/c");
        file.mkdirs();

        Assertions.assertTrue(InputHelper.isDirectory("a"));
        Assertions.assertTrue(InputHelper.isDirectory("a/b"));
        Assertions.assertTrue(InputHelper.isDirectory("a/b/c"));
        Assertions.assertFalse(InputHelper.isDirectory("a/b/c/d/"));
    }
}
