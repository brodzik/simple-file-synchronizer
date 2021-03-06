package com.brodzik.adrian.simplefilesynchronizer.reference;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Constants {
    public static final String APP_ID = "com.brodzik.adrian.simplefilesynchronizer";
    public static final String MESSAGE_SHOW = "SHOW";

    public static final String APP_NAME = "Simple File Synchronizer";
    public static final String DASHBOARD_TITLE = Constants.APP_NAME;
    public static final String ENTRY_NEW_TITLE = "New entry";
    public static final String ENTRY_EDIT_TITLE = "Edit entry";

    public static final String HOME_DIR = System.getProperty("user.home");
    public static final Path APP_DIR = Paths.get(Constants.HOME_DIR, ".simplefilesynchronizer");
    public static final Path CONFIG_FILE = Paths.get(Constants.APP_DIR.toString(), "config.json");
    public static final Path ENTRIES_FILE = Paths.get(Constants.APP_DIR.toString(), "entries.json");
    public static final Path FILE_LIST_FILE = Paths.get(Constants.APP_DIR.toString(), "fileList.json");

    public static final int SYNC_DELAY_SECONDS = 5;
}
