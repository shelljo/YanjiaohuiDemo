package com.yaotuofu.android.framework.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by FelixFang on 9/29/16.
 */

public class NoScrollRecylerView extends RecyclerView {

    public NoScrollRecylerView(Context context) {
        super(context);
    }

    public NoScrollRecylerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollRecylerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
