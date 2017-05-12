package com.yaotuofu.android.framework.cache.bigcache.other.bitmap;

import android.annotation.TargetApi;
import android.graphics.BitmapFactory;
import android.os.Build;

/**
 * Created by FelixFang on 10/26/16.
 */

public class ImageDecodingInfo {

    private final ImageSize targetSize;

    private final ImageScaleType imageScaleType;
    private final ViewScaleType viewScaleType;

    private final boolean considerExifParams;
    private final BitmapFactory.Options decodingOptions;

    public ImageDecodingInfo(ImageSize targetSize,ImageScaleType imageScaleType, ViewScaleType viewScaleType, boolean considerExifParams,BitmapFactory.Options options) {
        this.targetSize = targetSize;

        this.imageScaleType = imageScaleType;
        this.viewScaleType = viewScaleType;
        this.considerExifParams = considerExifParams;
        decodingOptions = new BitmapFactory.Options();
        copyOptions(decodingOptions, options);
    }

    private void copyOptions(BitmapFactory.Options srcOptions, BitmapFactory.Options destOptions) {
        destOptions.inDensity = srcOptions.inDensity;
        destOptions.inDither = srcOptions.inDither;
        destOptions.inInputShareable = srcOptions.inInputShareable;
        destOptions.inJustDecodeBounds = srcOptions.inJustDecodeBounds;
        destOptions.inPreferredConfig = srcOptions.inPreferredConfig;
        destOptions.inPurgeable = srcOptions.inPurgeable;
        destOptions.inSampleSize = srcOptions.inSampleSize;
        destOptions.inScaled = srcOptions.inScaled;
        destOptions.inScreenDensity = srcOptions.inScreenDensity;
        destOptions.inTargetDensity = srcOptions.inTargetDensity;
        destOptions.inTempStorage = srcOptions.inTempStorage;
        if (Build.VERSION.SDK_INT >= 10) copyOptions10(srcOptions, destOptions);
        if (Build.VERSION.SDK_INT >= 11) copyOptions11(srcOptions, destOptions);
    }

    @TargetApi(10)
    private void copyOptions10(BitmapFactory.Options srcOptions, BitmapFactory.Options destOptions) {
        destOptions.inPreferQualityOverSpeed = srcOptions.inPreferQualityOverSpeed;
    }

    @TargetApi(11)
    private void copyOptions11(BitmapFactory.Options srcOptions, BitmapFactory.Options destOptions) {
        destOptions.inBitmap = srcOptions.inBitmap;
        destOptions.inMutable = srcOptions.inMutable;
    }

    /**
     * @return Target size for image. Decoded bitmap should close to this size according to {@linkplain ImageScaleType
     * image scale type} and {@linkplain ViewScaleType view scale type}.
     */
    public ImageSize getTargetSize() {
        return targetSize;
    }

    /**
     * @return {@linkplain ImageScaleType Scale type for image sampling and scaling}. This parameter affects result size
     * of decoded bitmap.
     */
    public ImageScaleType getImageScaleType() {
        return imageScaleType;
    }

    /** @return {@linkplain ViewScaleType View scale type}. This parameter affects result size of decoded bitmap. */
    public ViewScaleType getViewScaleType() {
        return viewScaleType;
    }

    /** @return <b>true</b> - if EXIF params of image should be considered; <b>false</b> - otherwise */
    public boolean shouldConsiderExifParams() {
        return considerExifParams;
    }

    /** @return Decoding options */
    public BitmapFactory.Options getDecodingOptions() {
        return decodingOptions;
    }
}
