package com.brodzik.adrian.simplefilesynchronizer.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashHelper {
    public static String getFileChecksum(File file, MessageDigest digest) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        fis.close();

        byte[] bytes = digest.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public static String getSHA256(File file) throws NoSuchAlgorithmException, IOException {
        return HashHelper.getFileChecksum(file, MessageDigest.getInstance("SHA-256"));
    }
}
