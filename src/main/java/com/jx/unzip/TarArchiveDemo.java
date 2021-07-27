package com.jx.unzip;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import java.io.*;

public class TarArchiveDemo {
    public static void main(String[] args) {
        File file = new File("D:\\unzip_test\\wsl-data.tar");
        try (FileInputStream in = new FileInputStream(file);
             TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(in)) {
            byte[] buffer = new byte[4096];
            TarArchiveEntry entry;
            while ((entry = tarArchiveInputStream.getNextTarEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }
                File outFile = new File(file.getParent(), entry.getName());
                if (!outFile.getParentFile().exists()) {
                    outFile.getParentFile().mkdirs();
                }
                try (FileOutputStream fos = new FileOutputStream(outFile)) {
                    while (tarArchiveInputStream.read(buffer) > 0) {
                        fos.write(buffer);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
