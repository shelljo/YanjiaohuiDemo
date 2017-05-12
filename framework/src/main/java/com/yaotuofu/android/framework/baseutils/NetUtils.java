package com.yaotuofu.android.framework.baseutils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yaotuofu.android.framework.application.BaseApplication;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络相关工具类
 * 1、判断网络是否连接
 * 2、判断是否是wifi连接
 * 3、打开网络设置界面
 *
 * Created by Felix on 2016/3/25.
 */
public class NetUtils {
    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected() {

        Context context = BaseApplication.getInstance();

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 获取Url中参数
     * @param url
     * @return
     */
    public static Map<String, String> getUrlPramNameAndValue(String url) {
        String regEx = "(\\?|&+)(.+?)=([^&]*)";//匹配参数名和参数值的正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        // LinkedHashMap是有序的Map集合，遍历时会按照加入的顺序遍历输出
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        while(m.find()) {
            String paramName = m.group(2);//获取参数名
            String paramVal = m.group(3);//获取参数值
            paramMap.put(paramName, paramVal);
        }
        return paramMap;
    }

    /**
     * 硬解码,临时措施
     * @param url
     * @return
     */
    public static String getUrlPath(String url) {
//        String regEx = "(?<=((([^:/#]+):)?(//([^/?#]*))?)/home(?=((\\?([^#]*))?(#(.*))?)))";
        String regEx = "\\?";
        Pattern p = Pattern.compile(regEx);
        String[] result = p.split(url);
        String regEx2 = "\\/";
        Pattern p2 = Pattern.compile(regEx2);
        String[] result2 = p2.split(result[0]);
        int length = result2.length;
        return result2[length - 1];
    }


    public static void main(String[] args) {
        System.out.println(NetUtils.getUrlPath("http://detail/home?portfolioId=4"));
    }
}
