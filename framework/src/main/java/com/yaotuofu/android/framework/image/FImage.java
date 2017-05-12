package com.yaotuofu.android.framework.image;

import android.content.Context;

import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLog;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Felix on 2016/4/14.
 */
public class FImage {

    private static int MAX_MEM = 256 * ByteConstants.MB;

    private static ImagePipelineConfig getConfigureCaches(Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEM,// 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中图片的最大数量。
                MAX_MEM,// 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE);// 内存缓存中单个图片的最大大小。

        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };

        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());

        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(context);
        builder.setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams);
        builder.setRequestListeners(requestListeners);
        return builder.build();
    }

    public static void initImageFramework(Context context) {

        /*Fresco自身已保证单次初始化*/
        Fresco.initialize(context, getConfigureCaches(context));
        /* 如果需要查看fresco日志，取消注释下面这行 */
//        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
    }

    public static void initImageFramework(Context context, boolean isLog) {

        /*Fresco自身已保证单次初始化*/
        Fresco.initialize(context, getConfigureCaches(context));
        /* 如果需要查看fresco日志，取消注释下面这行 */
        if (isLog) {
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
        }
    }
}
