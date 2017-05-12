package com.yaotuofu.android.framework.cache.bigcache.disk.read;

import java.io.File;

/**
 * Created by FelixFang on 10/25/16.
 */

public class FileReadFromDisk implements ReadFromDisk<File> {

    @Override
    public File readOut(File file) {
//        InputStream input = null;
//        try {
//            input = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            L.e(e +  "读取InputStream错误");
//        } catch (Exception e) {
//            L.e(e +  "读取InputStream错误");
//        }
        return file;
    }
}
