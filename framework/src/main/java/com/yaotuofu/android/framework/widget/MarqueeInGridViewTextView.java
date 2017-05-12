package com.yaotuofu.android.framework.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by FelixFang on 6/7/16.
 */
public class MarqueeInGridViewTextView extends TextView {
    public MarqueeInGridViewTextView(Context context) {
        super(context);
    }

    public MarqueeInGridViewTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeInGridViewTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public MarqueeInGridViewTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean isFocused() {
//        return super.isFocused();
        return true;
    }
}
