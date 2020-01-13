package com.brodzik.adrian.simplefilesynchronizer.helper;

import javafx.scene.control.TextFormatter;

import java.io.File;
import java.util.function.UnaryOperator;

public final class InputHelper {
    public static final UnaryOperator<TextFormatter.Change> INTEGER_FILTER = change -> change.getControlNewText().matches("-?([1-9][0-9]*)?") ? change : null;

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty() || s.isBlank() || s.trim().isEmpty();
    }

    public static boolean isDirectory(String s) {
        return new File(s).isDirectory();
    }
}
