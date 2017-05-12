package com.yaotuofu.android.framework.widget.refresh;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.SeekBar;

/**
 * Created by Felix on 2016/4/14.
 */
public class FRefreshView extends SwipeRefreshLayout implements IRefresh {

    float DownX;
    float DownY;
    boolean downOnViewPager = false;
    boolean loading = false;

    View mTargetView;

    public FRefreshView(Context context) {
        super(context);
        initView();
    }

    public FRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setColorSchemeColors(Color.parseColor("#FF4388FB"));
    }


    @Override
    public View getRefrshView() {
        return this;
    }

    @Override
    public void setIRefreshListener(final IRefreshListener listener) {
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.onRefresh();
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(getTargetView() instanceof RefreshChildContainer) {
            if(((RefreshChildContainer) getTargetView()).canChildScrollUp()) {
                return false;
            }
        }
        final int action = MotionEventCompat.getActionMasked(ev);
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                DownX = ev.getX();
                DownY = ev.getY();
                downOnViewPager = isDownOnHorizontalSwipeView(this, ev.getRawX(), ev.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                //水平滑动距离大于0.8倍垂直距离 返回不拦截
                if (downOnViewPager && Math.abs(ev.getX() - DownX) > Math.abs(ev.getY() - DownY) * 0.8) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private View getTargetView() {
        if (mTargetView != null) return mTargetView;
        if(getChildCount()>1){
            mTargetView = getChildAt(1);
            return mTargetView;
        }
        return null;
    }

    /**
     * 判断坐标是否在Viewpager上
     *
     * @param viewGroup
     * @param rawX
     * @param rawY
     * @return
     */
    private static boolean isDownOnHorizontalSwipeView(ViewGroup viewGroup, float rawX, float rawY) {
        int location[] = new int[2];
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            v.getLocationOnScreen(location);
            //在v的范围内
            if (rawX >= location[0] && rawX <= location[0] + v.getWidth()
                    && rawY >= location[1] && rawY <= location[1] + v.getHeight())
                if (v instanceof ViewGroup) {
                    if (v instanceof ViewPager || v instanceof HorizontalScrollView) {
                        return true;
                    } else {
                        //存在子布局 递推调用
                        return isDownOnHorizontalSwipeView((ViewGroup) v, rawX, rawY);
                    }
                } else {
                    if (v instanceof SeekBar) {
                        return true;
                    }
                }
        }
        return false;
    }
    public void enablePullToRefresh(boolean enable) {
        setEnabled(enable);
    }
}
