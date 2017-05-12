package com.yaotuofu.android.framework.cache.acache;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by FelixFang on 10/25/16.
 */

public final class PathUtil {

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
