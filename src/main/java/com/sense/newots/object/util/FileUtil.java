package com.sense.newots.object.util;

import java.io.File;
import java.text.DecimalFormat;

public class FileUtil {
    public static String[] getConfFiles() {
        String foldpath = "conf/fs_configs";
        File file = new File(foldpath);
        String[] filelist = file.list();
        return filelist;
    }

    public static String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024L)
            fileSizeString = df.format(fileS) + "B";
        else if (fileS < 1048576L)
            fileSizeString = df.format(fileS / 1024.0D) + "K";
        else if (fileS < 1073741824L)
            fileSizeString = df.format(fileS / 1048576.0D) + "M";
        else {
            fileSizeString = df.format(fileS / 1073741824.0D) + "G";
        }
        return fileSizeString;
    }
}