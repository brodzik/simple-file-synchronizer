package com.brodzik.adrian.simplefilesynchronizer.handler;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.helper.HashHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public final class SyncHandler {
    public static final SyncHandler INSTANCE = new SyncHandler();

    private SyncHandler() {
    }

    public void sync(Path src, Path dest) {
        try {
            createDirs(src, dest);

            HashMap<String, String> srcFiles = getFiles(src);
            HashMap<String, String> destFiles = getFiles(dest);

            srcFiles.forEach((relativePath, hash) -> {
                if (hash.equals(destFiles.get(relativePath))) {
                    System.out.println("File is up-to-date: " + relativePath);
                } else {
                    try {
                        System.out.println("Updating file: " + relativePath);
                        Files.copy(Paths.get(src.toString(), relativePath), Paths.get(dest.toString(), relativePath), StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            destFiles.forEach((relativePath, hash) -> {
                if (!srcFiles.containsKey(relativePath)) {
                    try {
                        System.out.println("Removing file: " + relativePath);
                        Files.delete(Paths.get(dest.toString(), relativePath));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void syncAll() {
        for (Entry entry : EntryHandler.INSTANCE.getEntries()) {
            System.out.println("Syncing: " + entry.getName());
            sync(Paths.get(entry.getFolderA()), Paths.get(entry.getFolderB()));
        }
    }

    private HashMap<String, String> getFiles(Path p) throws IOException {
        HashMap<String, String> map = new HashMap<>();

        Files.walk(p).filter(Files::isRegularFile).forEach(path -> {
            try {
                String relativePath = path.toString().replace(p.toString(), "");
                map.put(relativePath, HashHelper.getSHA256(path.toFile()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return map;
    }

    private void createDirs(Path src, Path dest) throws IOException {
        Files.walk(src).filter(Files::isDirectory).forEach(path -> {
            String newPath = path.toString().replace(src.toString(), dest.toString());
            File file = new File(newPath);

            if (file.mkdirs()) {
                System.out.println("Directory created: " + newPath);
            } else {
                System.out.println("Directory already exists: " + newPath);
            }
        });
    }
}
