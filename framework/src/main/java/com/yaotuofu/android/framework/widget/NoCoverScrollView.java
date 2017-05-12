package com.yaotuofu.android.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by FelixFang on 6/6/16.
 */
public class NoCoverScrollView extends ScrollView {
    private GestureDetector mGestureDetector;
    OnTouchListener mGestureListener;

    public NoCoverScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mGestureDetector = new GestureDetector(new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY)
        {
            if (Math.abs(distanceY) > Math.abs(distanceX))
            {
                return true;
            }
            return false;
        }
    }
}
