package com.jx.unzip;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

public class GzArchiveDemo {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\unzip_test\\pcre-8.35.tar.gz");
        FileInputStream inputStream = new FileInputStream(file);
        CompressorInputStream in = new GzipCompressorInputStream(inputStream);
        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(in);
        TarArchiveEntry entry;
        while ((entry = tarArchiveInputStream.getNextTarEntry()) != null) {
            if (entry.isDirectory()) {
                continue;
            }
            File outFile = new File(file.getParentFile(), entry.getName());
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            OutputStream outputStream = new FileOutputStream(outFile);
            IOUtils.copy(tarArchiveInputStream, outputStream);
        }
    }
}
