package com.yaotuofu.android.framework.ninephotos;

import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/4/1.
 */

public interface GestureDetector {

    boolean onTouchEvent(MotionEvent ev);

    boolean isScaling();

    boolean isDragging();

    void setOnGestureListener(OnGestureListener listener);
}
