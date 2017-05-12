package com.yaotuofu.android.framework.baseutils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Sdcard工具类
 * 1、判断Sdcard是否可用
 * 2、获取Sdcard路径
 * 3、获取Sd卡的剩余容量 单位byte
 * 4、获取指定路径所在空间的剩余可用容量字节数，单位byte
 * 5、获取系统根root存储路径
 *
 * Created by Felix on 2016/3/26.
 */
public class SdcardUtils {
    private SdcardUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断Sdcard是否可用
     */
    public static boolean isSdcardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取Sd卡路径
     */
    public static String getSdcardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 获取Sd卡的剩余容量 单位byte
     */
    public static long getSdcardAllSize() {
        if(isSdcardEnable()) {
            StatFs stat = new StatFs(getSdcardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if(filePath.startsWith(getSdcardPath())) {
            filePath = getSdcardPath();
        }else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统根root存储路径
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }
}
