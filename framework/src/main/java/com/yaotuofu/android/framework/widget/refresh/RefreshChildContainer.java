package com.yaotuofu.android.framework.widget.refresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * Created by Felix on 2016/4/14.
 */
public class RefreshChildContainer extends FrameLayout {

    public RefreshChildContainer(Context context) {
        super(context);
    }

    public RefreshChildContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshChildContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RefreshChildContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                                 int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    /**
     * 获得第一个可见的子View
     *
     * @param
     * @throws RuntimeException 必须有一个子View显示
     */
    public ViewGroup getVisibleChildView() {
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getVisibility() == View.VISIBLE) {
                return (ViewGroup) getChildAt(i);
            }
        }
        throw new RuntimeException("必须有一个子View显示");
    }

    /**
     * 判断子View能否向上滚动
     *
     * @param
     */
    public boolean canChildScrollUp() {
        View mTarget = getVisibleChildView();
        if (Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                //至少有可见的子视图，并且第一个可见子视图不在0处或者第一个子视图顶部在布局边界之上 说明能够上滚
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }
}
