package com.yaotuofu.android.framework.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.yaotuofu.android.framework.controller.BaseActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.yaotuofu.android.framework.cache.acache.ACache;

/**
 * 全局配置
 * 1、统计api
 * 2、安全退出，防止内存泄漏
 * 3、
 *
 * Created by Felix on 2016/3/24.
 */
public class BaseApplication extends MultiDexApplication {
    protected static BaseApplication mApplication;
    private static List<BaseActivity> activityList = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
//        mApplication = this;
//        FImage.initImageFramework(this);

        ACache.get(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static BaseApplication getInstance() {
        return mApplication;
    }

    public static List<BaseActivity> getActivityList() {
        return activityList;
    }

    public static void clearAllActivity() {
        for (BaseActivity activity : activityList) {
            if (!activity.isFinishing())
                activity.finishNoAnimation();

        }

        activityList.clear();
    }

    public static <T> void closeActivity(Class<T> clazz) {
        List<BaseActivity> activities = new ArrayList<BaseActivity>();
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i).getClass() == clazz) {
                activities.add(activityList.get(i));
            }

        }
        if (!activities.isEmpty()) {
            for (BaseActivity activity : activities) {
                activity.finishNoAnimation();
            }
            activityList.remove(activities);
        }
    }

    public static void exit() {
        clearAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
