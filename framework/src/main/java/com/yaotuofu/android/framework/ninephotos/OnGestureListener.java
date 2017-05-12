package com.yaotuofu.android.framework.ninephotos;

/**
 * Created by Administrator on 2017/4/1.
 */

public interface OnGestureListener {

    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX,
                 float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);
}
