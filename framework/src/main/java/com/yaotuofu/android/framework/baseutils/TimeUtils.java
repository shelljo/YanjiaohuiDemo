package com.yaotuofu.android.framework.baseutils;

import android.annotation.SuppressLint;
import android.content.Context;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.yaotuofu.android.framework.R;

/**
 * Created by Felix on 2016/4/22.
 */
public class TimeUtils {
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_DATE_STR = new SimpleDateFormat("yyyyMMdd");

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * date, format yyyy-MM-dd
     *
     * @return
     */
    public static String getDate() {
        return getTime(getCurrentTimeInLong(), DATE_FORMAT_DATE);
    }
    public static String getTodayDateStr() {
        return getTime(getCurrentTimeInLong(), DATE_FORMAT_DATE_STR);
    }

    public static String getDate(int seconds) {
        return getTime(((long) seconds) * 1000, DATE_FORMAT_DATE);
    }

    public static Date str2Date(String str) {
        Date d = null;
        try {
            d = DEFAULT_DATE_FORMAT.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }
    public static int getCurrentTimeInt() {
        return (int)(System.currentTimeMillis()/1000);
    }

    public static Long date2Millisecond(Date date){
        return date.getTime();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }

    public static boolean shouldReplace() {
        return TimeUtils.getCurrentTimeInLong() >= new Date(116, 7, 14).getTime();
    }

    public static int getDay4Clock(int seconds) {
        String day = TimeUtils.getTime(seconds * 1000L);
        String[] a = day.split(" ");
        String clock4 = "04:00:00";
        StringBuffer sb = new StringBuffer(a[0]);
        sb.append(" ");
        sb.append(clock4);

        int b = (int) (TimeUtils.date2Millisecond(TimeUtils.str2Date(sb.toString())) / 1000);

        return b;
    }

    public static int judgeDate(Date date) {
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.set(Calendar.HOUR_OF_DAY, 0);
        calendarToday.set(Calendar.MINUTE, 0);
        calendarToday.set(Calendar.SECOND, 0);
        calendarToday.set(Calendar.MILLISECOND, 0);
        Calendar calendarYesterday = Calendar.getInstance();
        calendarYesterday.add(Calendar.DAY_OF_MONTH, -1);
        calendarYesterday.set(Calendar.HOUR, 0);
        calendarYesterday.set(Calendar.MINUTE, 0);
        calendarYesterday.set(Calendar.SECOND, 0);
        calendarYesterday.set(Calendar.MILLISECOND, 0);
        Calendar calendarTarget = Calendar.getInstance();
        calendarTarget.setTime(date);
        return calendarTarget.before(calendarYesterday) ? 2 : (calendarTarget.before(calendarToday) ? 1 : 0);
    }

    public static String getVideoFormatDate(Context context, Date date) {
        String formatString;
        if (date == null) {
            return "";
        } else {
            String formatDate = null;
            int type = judgeDate(date);
            switch (type) {
                case 0:
                    formatString = context.getResources().getString(R.string.rc_today_format);
                    formatDate = String.format(formatString, formatDate(date, "HH:mm"));
                    break;
                case 1:
                    formatString = context.getResources().getString(R.string.rc_yesterday_format);
                    formatDate = String.format(formatString, formatDate(date, "HH:mm"));
                    break;
                case 2:
                    formatDate = formatDate(date, "yyyy/MM/dd");
            }

            return formatDate;
        }
    }


    public static Calendar getCurrentCalendar(){
        // 创建 Calendar 对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        return calendar;
    }


    private static String formatDate(Date date, String fromat) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(fromat, Locale.CHINA);
        return sdf.format(date);
    }

}
