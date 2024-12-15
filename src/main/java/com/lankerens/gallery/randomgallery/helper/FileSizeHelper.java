package com.lankerens.gallery.randomgallery.helper;

import java.io.File;

/*
 *@title FileSizeHelper
 *@description
 *@author zhus
 *@version 1.0
 *@create 2024-12-16 2:27
 */
public class FileSizeHelper {

    // 计算文件夹大小
    public static long calculateFolderSize(File folder) {
        long size = 0;
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    size += calculateFolderSize(file); // 如果是文件夹，递归计算大小
                } else {
                    size += file.length(); // 如果是文件，累加文件大小
                }
            }
        }

        return size;
    }

    // 格式化大小为更可读的格式（例如 KB、MB）
    public static String formatSize(long sizeInBytes) {
        if (sizeInBytes < 1024) {
            return sizeInBytes + " bytes";
        } else if (sizeInBytes < 1048576) {
            return sizeInBytes / 1024 + " KB";
        } else if (sizeInBytes < 1073741824) {
            return sizeInBytes / 1048576 + " MB";
        } else {
            return sizeInBytes / 1073741824 + " GB";
        }
    }

}
