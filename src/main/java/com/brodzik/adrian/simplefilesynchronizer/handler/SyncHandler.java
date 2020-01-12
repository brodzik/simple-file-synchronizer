package com.brodzik.adrian.simplefilesynchronizer.handler;

import com.brodzik.adrian.simplefilesynchronizer.data.Entry;
import com.brodzik.adrian.simplefilesynchronizer.helper.HashHelper;
import com.brodzik.adrian.simplefilesynchronizer.helper.InputHelper;
import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SyncHandler implements Loadable {
    public static final SyncHandler INSTANCE = new SyncHandler();

    public final Map<String, List<String>> fileList = new HashMap<>();

    private SyncHandler() {
    }

    public void sync(Entry entry) {
        if (entry.isEnabled()) {
            System.out.println("Syncing: " + entry.getName());

            Date now = new Date();

            if (!InputHelper.isDirectory(entry.getFolderA())) {
                System.out.println("Skipping sync. Directory not found: " + entry.getFolderA());
                return;
            }

            if (!InputHelper.isDirectory(entry.getFolderB())) {
                System.out.println("Skipping sync. Directory not found: " + entry.getFolderB());
                return;
            }

            Path pathA = Paths.get(entry.getFolderA());
            Path pathB = Paths.get(entry.getFolderB());

            switch (entry.getDirection()) {
                case UNIDIRECTIONAL_1:
                    syncUnidirectional(pathA, pathB);
                    break;
                case UNIDIRECTIONAL_2:
                    syncUnidirectional(pathB, pathA);
                    break;
                case BIDIRECTIONAL:
                    syncBidirectional(pathA, pathB, entry.getLastSync().getTime());
                    break;
                default:
                    System.out.println("Unknown sync direction.");
                    break;
            }

            try {
                fileList.put(pathA.toString(), getRelativeFilePaths(pathA));
                fileList.put(pathB.toString(), getRelativeFilePaths(pathB));
            } catch (Exception e) {
                e.printStackTrace();
            }

            entry.setLastSync(now);
            EntryHandler.INSTANCE.update(entry);
        } else {
            System.out.println("Skipping: " + entry.getName());
        }
    }

    public void syncAll() {
        EntryHandler.INSTANCE.getEntries().forEach(this::sync);
    }

    private void syncUnidirectional(Path src, Path dest) {
        try {
            createDirs(src, dest);

            HashMap<String, String> srcFiles = getRelativeFileHashes(src);
            HashMap<String, String> destFiles = getRelativeFileHashes(dest);

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

    private void syncBidirectional(Path pathA, Path pathB, long lastSync) {
        try {
            createDirs(pathA, pathB);
            createDirs(pathB, pathA);

            Map<String, String> filesA = getRelativeFileHashes(pathA);
            Map<String, String> filesB = getRelativeFileHashes(pathB);

            System.out.println("Checking A to B...");
            syncBidirectionalHelper(pathA, pathB, lastSync, filesA, filesB);

            System.out.println("Checking B to A...");
            syncBidirectionalHelper(pathB, pathA, lastSync, filesB, filesA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncBidirectionalHelper(Path pathA, Path pathB, long lastSync, Map<String, String> filesA, Map<String, String> filesB) {
        filesA.forEach((relativePath, hash) -> {
            try {
                if (filesB.containsKey(relativePath)) {
                    if (hash.equals(filesB.get(relativePath))) {
                        System.out.println("File is up-to-date: " + relativePath);
                    } else {
                        long dateA = Paths.get(pathA.toString(), relativePath).toFile().lastModified();
                        long dateB = Paths.get(pathB.toString(), relativePath).toFile().lastModified();

                        if (dateA > lastSync && dateB > lastSync && lastSync > 0) {
                            // TODO: resolve conflict
                            System.out.println("Sync conflict: " + relativePath);
                        } else if (dateA > dateB) {
                            System.out.println("Updating file: " + relativePath);
                            Files.copy(Paths.get(pathA.toString(), relativePath), Paths.get(pathB.toString(), relativePath), StandardCopyOption.REPLACE_EXISTING);
                        } else {
                            System.out.println("Updating file: " + relativePath);
                            Files.copy(Paths.get(pathB.toString(), relativePath), Paths.get(pathA.toString(), relativePath), StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                } else {
                    List<String> prevFilesB = fileList.get(pathB.toString());

                    if (prevFilesB != null && prevFilesB.contains(relativePath)) {
                        System.out.println("Removing file: " + relativePath);
                        Files.delete(Paths.get(pathA.toString(), relativePath));
                    } else {
                        System.out.println("Updating file: " + relativePath);
                        Files.copy(Paths.get(pathA.toString(), relativePath), Paths.get(pathB.toString(), relativePath), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private HashMap<String, String> getRelativeFileHashes(Path p) throws IOException {
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

    private List<String> getRelativeFilePaths(Path p) throws IOException {
        return Files.walk(p).filter(Files::isRegularFile).map(path -> path.toString().replace(p.toString(), "")).collect(Collectors.toList());
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

    @Override
    public void load() {
        if (Files.exists(Constants.FILE_LIST_FILE)) {
            try {
                FileReader reader = new FileReader(Constants.FILE_LIST_FILE.toFile());

                JSONParser parser = new JSONParser();
                JSONArray array = (JSONArray) parser.parse(reader);

                for (Object object : array) {
                    JSONObject o = (JSONObject) object;
                    fileList.put(o.get("rootPath").toString(), (List<String>) o.get("relativeFilePaths"));
                }

                reader.close();
            } catch (Exception e) {
                System.out.println("Failed to load file list.");
                e.printStackTrace();
                save();
            }
        }
    }

    @Override
    public void save() {
        JSONArray array = new JSONArray();

        fileList.forEach((rootPath, relativeFilePaths) -> {
            JSONObject object = new JSONObject();
            object.put("rootPath", rootPath);
            object.put("relativeFilePaths", relativeFilePaths);
            array.add(object);
        });

        try {
            FileWriter writer = new FileWriter(Constants.FILE_LIST_FILE.toFile());
            writer.write(array.toJSONString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("File list saved.");
    }
}
