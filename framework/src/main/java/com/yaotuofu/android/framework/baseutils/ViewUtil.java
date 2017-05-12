package com.yaotuofu.android.framework.baseutils;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * @author AirZcm on 2016330.
 */
public class ViewUtil {

    public static void setVisibility(final View view, final int visibilityFlag) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != visibilityFlag) {
            view.setVisibility(visibilityFlag);
        }
    }

    public static void setScale(final View view, final float scalaFactor) {
        if (view == null) {
            return;
        }
        if (view.getScaleX() != scalaFactor) {
            view.setScaleX(scalaFactor);
        }
        if (view.getScaleY() != scalaFactor) {
            view.setScaleY(scalaFactor);
        }
    }

    public static void setAlpha(final View view, final float alpha) {
        if (view == null) {
            return;
        }
        if (view.getAlpha() != alpha) {
            ViewCompat.setAlpha(view, alpha);
        }
    }

    public static int convertToPx(Context context, int dp) {
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
