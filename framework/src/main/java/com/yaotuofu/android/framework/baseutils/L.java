package com.yaotuofu.android.framework.baseutils;

import android.util.Log;

/**
 * Log统一管理
 *
 * Created by Felix on 2016/3/24.
 */
public class L {
    private static final String TAG = "wing";
    public static boolean isDebug = false; // 是否需要打印bug，可以在application的onCreate函数里面初始化

    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String getDefaultTag() {
        return TAG;
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void w(String msg) {
        if (isDebug)
            Log.w(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(TAG + tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(TAG + tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(TAG + tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(TAG + tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(TAG + tag, msg);
    }

    public static void e(String msg, Throwable tr) {
        if (isDebug) {
            Log.e(TAG, msg, tr);
        }
    }

    public static void e(Throwable tr) {
        if (isDebug) {
            Log.e(TAG, "", tr);
        }
    }

    public static void w(String tag, String msg, Throwable e) {
        if (isDebug)
            Log.w(TAG + tag, msg, e);
    }
}
