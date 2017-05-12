package com.yaotuofu.android.framework.baseutils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.yaotuofu.android.framework.application.BaseApplication;


/**
 * Created by Felix on 2016/3/28.
 */
public class ToastUtils {
    public static boolean isShow = true;

    private static Toast sToast = null;

    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 居中短时间显示Toast
     *
     * @param context Context
     * @param message CharSequence
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            setToast(Toast.makeText(context, message, Toast.LENGTH_SHORT)).show();
    }

    public static void showShort(CharSequence message) {
        if (isShow)
            // setToast(Toast.makeText(context, message, Toast.LENGTH_SHORT)).show();
            showMessage(message.toString(), Toast.LENGTH_SHORT);

    }

    public static void showShort(int message) {
        if (isShow)
            // setToast(Toast.makeText(context, message, Toast.LENGTH_SHORT)).show();
            showMessage(BaseApplication.getInstance().getResources().getString(message), Toast.LENGTH_LONG);

    }

    private static Toast setToast(Toast toast) {
        toast.setGravity(Gravity.CENTER, 0, 0);// 设置居中
        toast.show();
        return toast;
    }

    /**
     * 居中短时间显示Toast
     *
     * @param context Context
     * @param message int
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            setToast(Toast.makeText(context, message, Toast.LENGTH_SHORT)).show();
    }

    /**
     * 居中长时间显示Toast
     *
     * @param context Context
     * @param message CharSequence
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            setToast(Toast.makeText(context, message, Toast.LENGTH_LONG)).show();
    }

    public static void showLong(CharSequence message) {
        if (isShow)
            //setToast(Toast.makeText(context, message, Toast.LENGTH_LONG)).show();
            showMessage(message.toString(), Toast.LENGTH_LONG);

    }

    public static void showLong(int message) {
        if (isShow)
            // setToast(Toast.makeText(context, message, Toast.LENGTH_LONG)).show();
            showMessage(BaseApplication.getInstance().getResources().getString(message), Toast.LENGTH_LONG);

    }

    /**
     * 居中长时间显示Toast
     *
     * @param context Context
     * @param message CharSequence
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            setToast(Toast.makeText(context, message, Toast.LENGTH_LONG)).show();
    }

    /**
     * 自定义居中显示Toast时间
     *
     * @param context Context
     * @param message CharSequence
     * @param duration int
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            setToast(Toast.makeText(context, message, duration)).show();
    }

    public static void show(CharSequence message, int duration) {
        if (isShow)
            // setToast(Toast.makeText(context, message, duration)).show();
            showMessage(message.toString(), duration);

    }

    /**
     * 自定义居中显示Toast时间
     *
     * @param context Context
     * @param message int
     * @param duration int
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            setToast(Toast.makeText(context, message, duration)).show();
    }

    public static void show(int message, int duration) {
        if (isShow)
            //setToast(Toast.makeText(context, message, duration)).show();
            showMessage(BaseApplication.getInstance().getResources().getString(message), duration);
    }

    public static void showMessage(final String msg, final int len) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (null == sToast) {
            sToast = Toast.makeText(BaseApplication.getInstance(), msg, len);
        } else {
            sToast.setDuration(len);
            sToast.setText(msg);
        }
        sToast.show();

//        ((Activity)act).runOnUiThread(new Runnable() {
//
//            public void run() {
//                Toast.makeText(act, msg, len).show();
//            }
//        });
    }
}
