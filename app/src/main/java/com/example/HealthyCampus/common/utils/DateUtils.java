package com.example.HealthyCampus.common.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {


    private static final String[] weekNames = {"星期日", "星期一", "星期日二", "星期日三", "星期四", "星期五", " 星期六"};
    private static final String hourTimeFormat = "HH:mm";
    private static final String monthTimeFormat = "M月d日 HH:mm";
    private static final String yearTimeFormat = "yyyy年M月d日";


    public static final DateFormat FORMAT_HP_yyyyMMdd = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    public static final DateFormat FORMAT_yyyyMMdd = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    public static final DateFormat FORMAT_yyyy_MM_dd_H_m_s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final DateFormat FORMAT_MM_dd = new SimpleDateFormat("MM月dd日", Locale.getDefault());
    public static final DateFormat FORMAT_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private DateUtils() {

    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 将时间字符串转为Date 字符串
     * <p>time格式为format</p>
     *
     * @param time   时间字符串，格式为 yyyyMMdd
     * @param format 时间格式 需要转换的格式
     * @return Date类型
     */
    public static String string2DateString3(final String time, final DateFormat format) {
        Date formatDate = string2Date(time, FORMAT_yyyyMMdd);
        return date2String(formatDate, format);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @return Date类型
     */
    public static Date string2Date(final String time) {
        try {
            return FORMAT_yyyy_MM_dd_H_m_s.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将时间字符串转为String类型
     * <p>time格式为format</p>
     *
     * @param time 时间字符串
     * @return String类型
     */
    public static String string2Long(final String time) {
        try {
            return getTimeString(FORMAT_yyyy_MM_dd_H_m_s.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将Date类型转为时间字符串
     * <p>格式为format</p>
     *
     * @param date   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }


    public static String date2String(final Date date) {     //MM月dd日
        return FORMAT_MM_dd.format(date);
    }

    public static String date3String(final Date date) {     //MM月dd日
        return FORMAT_yyyy_MM_dd.format(date);
    }


    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;
        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

    public static String getTimeString(Long timestamp) {
        String result = "";
        try {
            Calendar todayCalendar = Calendar.getInstance();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);

            if (todayCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {// 当年
                if (todayCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {// 当月
                    int temp = todayCalendar.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);
                    switch (temp) {
                        case 0:// 今天
                            result = getTime(timestamp, hourTimeFormat);
                            break;
                        case 1:// 昨天
                            result = "昨天 " + getTime(timestamp, hourTimeFormat);
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                            result = weekNames[dayOfWeek - 1] + " " + getTime(timestamp, hourTimeFormat);
                            break;
                        default:
                            result = getTime(timestamp, monthTimeFormat);
                            break;
                    }
                } else {
                    result = getTime(timestamp, monthTimeFormat);
                }
            } else {
                result = getTime(timestamp, yearTimeFormat);
            }
            return result;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getTime(long time, String pattern) {
        Date date = new Date(time);
        return dateFormat(date, pattern);
    }

    public static String dateFormat(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }


}
