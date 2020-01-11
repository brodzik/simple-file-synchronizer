package com.brodzik.adrian.simplefilesynchronizer.reference;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Constants {
    public static final String APP_NAME = "Simple File Synchronizer";
    public static final String DASHBOARD_TITLE = String.format("%s - Dashboard", Constants.APP_NAME);

    public static final String HOME_DIR = System.getProperty("user.home");
    public static final Path APP_DIR = Paths.get(Constants.HOME_DIR, ".simplefilesynchronizer");
    public static final Path CONFIG_FILE = Paths.get(Constants.APP_DIR.toString(), "config.json");
}
