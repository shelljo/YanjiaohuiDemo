package com.yaotuofu.android.framework.cache.bigcache.other.bitmap;

import android.widget.ImageView;

/**
 * Created by FelixFang on 10/26/16.
 */

public enum  ViewScaleType {
    /**
     * Scale the image uniformly (maintain the image's aspect ratio) so that at least one dimension (width or height) of
     * the image will be equal to or less the corresponding dimension of the view.
     */
    FIT_INSIDE,
    /**
     * Scale the image uniformly (maintain the image's aspect ratio) so that both dimensions (width and height) of the
     * image will be equal to or larger than the corresponding dimension of the view.
     */
    CROP;

    /**
     * Defines scale type of ImageView.
     *
     * @param imageView {@link ImageView}
     * @return {@link #FIT_INSIDE} for
     *         <ul>
     *         <li>{@link ImageView.ScaleType#FIT_CENTER}</li>
     *         <li>{@link ImageView.ScaleType#FIT_XY}</li>
     *         <li>{@link ImageView.ScaleType#FIT_START}</li>
     *         <li>{@link ImageView.ScaleType#FIT_END}</li>
     *         <li>{@link ImageView.ScaleType#CENTER_INSIDE}</li>
     *         </ul>
     *         {@link #CROP} for
     *         <ul>
     *         <li>{@link ImageView.ScaleType#CENTER}</li>
     *         <li>{@link ImageView.ScaleType#CENTER_CROP}</li>
     *         <li>{@link ImageView.ScaleType#MATRIX}</li>
     *         </ul>
     */
    public static ViewScaleType fromImageView(ImageView imageView) {
        switch (imageView.getScaleType()) {
            case FIT_CENTER:
            case FIT_XY:
            case FIT_START:
            case FIT_END:
            case CENTER_INSIDE:
                return FIT_INSIDE;
            case MATRIX:
            case CENTER:
            case CENTER_CROP:
            default:
                return CROP;
        }
    }
}
