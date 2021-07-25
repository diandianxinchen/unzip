package com.jx.unzip;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {

    public static String getFileNameWithoutType(String fileName) {
        int indexOf = fileName.lastIndexOf(".");
        return indexOf != -1 ? fileName.substring(0, indexOf) : fileName;
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    public static void main(String[] args) throws IOException {
        String fileZipPath = "D:\\unzip_test\\demo.zip";
        File fileZip = new File(fileZipPath);
        if (!fileZip.exists()) {
            throw new RuntimeException("找不到文件," + fileZipPath);
        }

        // 默认解压到当前路径
        String path = fileZip.getParent();
        String fileZipName = fileZip.getName();
        String destFileName = getFileNameWithoutType(fileZipName);
        File destDir = new File(path, destFileName);
        System.out.println(fileZip);
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip), Charset.forName("GBK"));
        ZipEntry zipEntry = zis.getNextEntry();
        byte[] buffer = new byte[1024];
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }
}
