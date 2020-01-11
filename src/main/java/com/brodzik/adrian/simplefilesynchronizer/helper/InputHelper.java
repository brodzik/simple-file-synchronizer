package com.brodzik.adrian.simplefilesynchronizer.helper;

import java.io.File;

public final class InputHelper {
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty() || s.isBlank() || s.trim().isEmpty();
    }

    public static boolean isDirectory(String s) {
        return new File(s).isDirectory();
    }
}
