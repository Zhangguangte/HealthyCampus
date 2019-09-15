package com.example.HealthyCampus.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final DateFormat FORMAT_HP_yyyyMMdd = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    public static final DateFormat FORMAT_yyyyMMdd = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

    private DateUtils() {

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


}