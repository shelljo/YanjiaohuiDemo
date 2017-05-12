package com.yaotuofu.android.framework.baseutils;

import android.content.Context;

/**
 * Created by Felix on 2016/4/8.
 */
public class AssetsUtils {
    /**
     * read file content
     *
     * @param context   the context
     * @param assetPath the asset path
     * @return String string
     */
    public static String readText(Context context, String assetPath) {
        L.d("read assets file as text: " + assetPath);
        try {
            return ConvertUtils.toString(context.getAssets().open(assetPath));
        } catch (Exception e) {
            L.e(e);
            return "";
        }
    }


}
