package com.jx.unzip;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class CompressorsDemo {
    public static void uncompress7z(File file) throws IOException {
        System.out.println(file.getAbsoluteFile());
        String path = file.getParent();
        SevenZFile sevenZFile = new SevenZFile(file);
        final Iterable<SevenZArchiveEntry> entries = sevenZFile.getEntries();
        byte[] buffer = new byte[4096];
        SevenZArchiveEntry entry;
        InputStream inputStream;
        while ((entry = sevenZFile.getNextEntry()) != null) {
            if (entry.isDirectory()) {
                continue;
            }
            String name = entry.getName();
            if (name.endsWith("zip")) {
                unzip(new File(path, name));
            } else if (name.endsWith("7z")) {
                uncompress7z(new File(path, name));
            }
            File outputFile = new File(path, entry.getName());
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            inputStream = sevenZFile.getInputStream(entry);
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                while (inputStream.read(buffer) > 0) {
                    fos.write(buffer);
                }
            }
        }
    }

    public static void unzip(File file) throws IOException {
        String path = file.getParent();
        ZipFile zipFile = new ZipFile(file);
        byte[] buffer = new byte[4096];
        ZipArchiveEntry entry;
        Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
        InputStream inputStream;
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            if (entry.isDirectory()) {
                continue;
            }
            String name = entry.getName();
            if (name.endsWith("zip")) {
                unzip(new File(path, name));
            } else if (name.endsWith("7z")) {
                uncompress7z(new File(path, name));
            }
            File outputFile = new File(path, entry.getName());
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            inputStream = zipFile.getInputStream(entry);
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                while (inputStream.read(buffer) > 0) {
                    fos.write(buffer);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\unzip_test\\demo.zip");
        unzip(file);
    }
}
