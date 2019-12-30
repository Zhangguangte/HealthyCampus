package com.example.HealthyCampus.common.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@SuppressLint("ConstantLocale")
public class DateUtils {
    private static final String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
    public static final String[] weekStr = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public static final DateFormat FORMAT_HP_yyyyMMdd = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    private static final DateFormat FORMAT_yyyyMMdd = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    private static final DateFormat FORMAT_yyyy_MM_dd_H_m_s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final DateFormat FORMAT_MM_dd = new SimpleDateFormat("MM月dd日", Locale.getDefault());

    private static final DateFormat FORMAT_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        return FORMAT_yyyy_MM_dd_H_m_s.format(currentTime);
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
    private static Date string2Date(final String time, final DateFormat format) {
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
    private static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }

    /**
     * 获取星期几
     */
    public static int getWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    //由出生日期获得年龄
    public static int getAge(String birthstr) {

        Date birthDay = string2Date(birthstr, FORMAT_yyyy_MM_dd);

        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static String arab2Chinese(int number) {
        return weeks[number % 7];
    }

    public static int chines2Number(String day) {
        return Arrays.asList(weekStr).indexOf(day);
    }

}
