package com.yaotuofu.android.framework.image;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * @author atomd
 */
public class ImageStorageUtils {

  private static final String TAG = ImageStorageUtils.class.getSimpleName();
  private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

  private ImageStorageUtils() {
  }

  public static File getCacheDirectory(Context context) {
    return getCacheDirectory(context, true);
  }

  /**
   * Returns application cache directory. Cache directory will be created on SD card
   * <i>("/Android/data/[app_package_name]/cache")</i> (if card is mounted and app has
   * appropriate
   * permission) or
   * on device's file system depending incoming parameters.
   */
  public static File getCacheDirectory(Context context, boolean preferExternal) {
    File appCacheDir = null;
    String externalStorageState;
    try {
      externalStorageState = Environment.getExternalStorageState();
    } catch (NullPointerException e) { // (sh)it happens (Issue #660)
      externalStorageState = "";
    }
    if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState)
        && hasExternalStoragePermission(context)) {
      appCacheDir = getExternalCacheDir(context);
    }
    if (appCacheDir == null) {
      appCacheDir = context.getCacheDir();
    }
    if (appCacheDir == null) {
      String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
//      Timber.tag(TAG).w("Can't define system cache directory! '%s' will be used.",
//          cacheDirPath);
      appCacheDir = new File(cacheDirPath);
    }
    return appCacheDir;
  }

  /**
   * Returns individual application cache directory (for only image caching from ImageLoader).
   * Cache directory will be
   * created on SD card <i>("/Android/data/[app_package_name]/cache/uil-images")</i> if card is
   * mounted and app has
   * appropriate permission. Else - Android defines cache directory on device's file system.
   */
  public static File getIndividualCacheDirectory(Context context, final String dirName) {
    File cacheDir = getCacheDirectory(context);
    File individualCacheDir = new File(cacheDir, dirName);
    if (!individualCacheDir.exists()) {
      if (!individualCacheDir.mkdir()) {
        individualCacheDir = cacheDir;
      }
    }
    return individualCacheDir;
  }

  private static File getExternalCacheDir(Context context) {
    File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"),
        "data");
    File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
    if (!appCacheDir.exists()) {
      if (!appCacheDir.mkdirs()) {
//        Timber.tag(TAG).w("Unable to create external cache directory");
        return null;
      }
      try {
        new File(appCacheDir, ".nomedia").createNewFile();
      } catch (IOException e) {
//        Timber.tag(TAG)
//            .i("Can't create \".nomedia\" file in application external cache directory");
      }
    }
    return appCacheDir;
  }

  private static boolean hasExternalStoragePermission(Context context) {
    int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
    return perm == PackageManager.PERMISSION_GRANTED;
  }
}
