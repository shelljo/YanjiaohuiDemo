package com.yaotuofu.android.framework.baseutils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Felix on 2016/4/12.
 */
public class FormatUtils {
    static DecimalFormat dFormat = new DecimalFormat("0.00");

    public static String formatNumber(double number) {
        try {
            return dFormat.format(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(number);

    }

    public static String formatNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return "0.00";
        }
        try {
            return dFormat.format(new BigDecimal(number));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    /**
     * 格式化价格显示
     *
     * @param price
     * @return
     */

    public static String formatPriceWithRBMSymbol(String price) {
        return SymbolUtils.CNY_SYMBOL + formatNumber(price);
    }

    /**
     * 格式化价格显示
     *
     * @param price
     * @return
     */

    public static SpannableString formatPriceWithUSDSymbol(double price) {
        String str = SymbolUtils.USD_SYMBOL + formatNumber(price);
        SpannableString msp = new SpannableString(str);
        //第二个参数boolean dp，如果为true，表示前面的字体大小单位为dp，否则为像素，同上。
//        msp.setSpan(new AbsoluteSizeSpan(10, true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //2.0f表示默认字体大小的0.5倍
        msp.setSpan(new RelativeSizeSpan(0.75f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    /**
     * 格式化价格显示
     *
     * @param price
     * @return
     */

    public static SpannableString formatPriceWithUSDSymbol(String price) {
        String str = SymbolUtils.USD_SYMBOL + formatNumber(price);
        SpannableString msp = new SpannableString(str);
        //第二个参数boolean dp，如果为true，表示前面的字体大小单位为dp，否则为像素，同上。
//        msp.setSpan(new AbsoluteSizeSpan(10, true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //2.0f表示默认字体大小的0.5倍
        msp.setSpan(new RelativeSizeSpan(0.75f), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    /**
     * 格式化价格显示
     *
     * @param price
     * @return
     */

    public static String formatPriceWithRBMSymbol(double price) {
        return SymbolUtils.CNY_SYMBOL + formatNumber(price);
    }

    /**
     * 格式化百分比
     * @return
     */
    public static String formatPercent(double d) {
        return new DecimalFormat("0.00%").format(d);
    }

    /**
     * 格式化百分比加百分号
     * @return
     */
    public static String addPercent(String d) {
        return d + "%";
    }

    /**
     * 解析时间 "yyyy-MM-dd HH:mm:ss" 格式
     * @param time
     * @return
     */

    public static long parseTimeForyyyyMMddHHmmss(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        String str = FormatUtils.formatPercent(-0.342843);
        System.out.println(str);
    }
}
