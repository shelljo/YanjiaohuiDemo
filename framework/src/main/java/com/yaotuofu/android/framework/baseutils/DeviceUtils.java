package com.yaotuofu.android.framework.baseutils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.yaotuofu.android.framework.encryption.AES;

import java.util.UUID;

/**
 * Created by Felix on 2016/4/25.
 */
public class DeviceUtils {
    private static final String FILE_NAME = "devNo";
    private static final String SP_KEY_DEVNO = "KEYDEVNO";

    private static void put(Context context, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            value = AES.Encrypt(value);
            SpUtils.put(context, key, value, FILE_NAME);
        }
    }

    private static String get(Context context, String key, String defaultValue) {
        String value = SpUtils.get(context, key, defaultValue, FILE_NAME);
        if (!TextUtils.isEmpty(value)) {
            value = AES.Decrypt(value);
        }
        return value;
    }

    /**
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        String devNo = DeviceUtils.get(context, SP_KEY_DEVNO, "");
        if(!TextUtils.isEmpty(devNo)) {
            return devNo;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        if (!TextUtils.isEmpty(imei)) {
            DeviceUtils.put(context, SP_KEY_DEVNO, imei);
            return imei;
        }

        if (TextUtils.isEmpty(imei)) {
            WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiMan != null) {
                WifiInfo wifiInf = wifiMan.getConnectionInfo();
                if (wifiInf != null && wifiInf.getMacAddress() != null) {// 48位，如FA:34:7C:6D:E4:D7
                    imei = wifiInf.getMacAddress().replaceAll(":", "");
                    DeviceUtils.put(context, SP_KEY_DEVNO, imei);
                    return imei;
                }
            }
        }
        if (TextUtils.isEmpty(imei)) {
            imei = android.provider.Settings.Secure.getString(context.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
        }
        if (TextUtils.isEmpty(imei)) {
            imei = UUID.randomUUID().toString().replaceAll("-", "");// UUID通用唯一识别码(Universally
        }
        DeviceUtils.put(context, SP_KEY_DEVNO, imei);
        return imei;
    }

    /**
     *
     * @return
     */
    public static String getOsVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }



}
