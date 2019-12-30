package com.example.HealthyCampus.common.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimestampUtils {
    private static final DateFormat FORMAT_yyyy_MM_dd_H_m_s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    /**
     * 获得当前时间戳
     *
     * @return
     */
    public static long getIntTimestamp() {
        long unixTimeGMT = 0;
        try {
            unixTimeGMT = System.currentTimeMillis();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return unixTimeGMT;

    }

    /**
     * 返回时间戳间隔
     *
     * @return
     */
    public static boolean compareTimestamp(long currentTimestap,
                                           long oldTimestap) {
        Boolean isExceed = false;
        long day = 7;
        if (gapTimestamp(currentTimestap, oldTimestap) > 86400 * day) {
            isExceed = true;
        }
        return isExceed;
    }

    public static long gapTimestamp(long currentTimestap, long oldTimestap) {
        return (currentTimestap - oldTimestap);
    }

    /**
     * 对时间戳格式进行格式化，保证时间戳长度为13位
     *
     * @param timestamp 时间戳
     * @return 返回为13位的时间戳
     */
    public static String formatTimestamp(String timestamp) {
        if (timestamp == null || "".equals(timestamp)) {
            return "";
        }
        String tempTimeStamp = timestamp + "00000000000000";
        StringBuffer stringBuffer = new StringBuffer(tempTimeStamp);
        return tempTimeStamp = stringBuffer.substring(0, 13);
    }

    /**
     * 根据 timestamp 生成各类时间状态串
     *
     * @param timestamp 距1970 00:00:00 GMT的秒数
     * @param format    格式
     * @return 时间状态串(如 ： 刚刚5分钟前)
     */
    public static String getTimeState(String timestamp, String format) {
        if (timestamp == null || "".equals(timestamp) || timestamp.startsWith("0000-00-00")) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date dt = null;// 转换成功的Date对象
        try {
            dt = dateFormat.parse(timestamp);
            Long time = dt.getTime();// 这就是距离1970年1月1日0点0分0秒的毫秒数
            timestamp = formatTimestamp(String.valueOf(time));
            long _timestamp = Long.parseLong(timestamp);
            if (System.currentTimeMillis() - _timestamp < 1 * 60 * 1000) {
                return "刚刚";
            } else if (System.currentTimeMillis() - _timestamp < 30 * 60 * 1000) {
                return ((System.currentTimeMillis() - _timestamp) / 1000 / 60)
                        + "分钟前";
            } else {
                Calendar now = Calendar.getInstance();
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(_timestamp);
                Date nowDate = now.getTime();
                Date mutiDate = c.getTime();
                int dayBetween = daysBetween(nowDate, mutiDate);
                if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                        && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                        && c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("今天 HH:mm");
                    return sdf.format(c.getTime());
                } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                        && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                        && c.get(Calendar.DATE) == now.get(Calendar.DATE) - 1) {
                    SimpleDateFormat sdf = new SimpleDateFormat("昨天 HH:mm");
                    return sdf.format(c.getTime());
                } else if (dayBetween < 31) {
                    return dayBetween + "天前";
                } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                    SimpleDateFormat sdf = null;
                    sdf = new SimpleDateFormat("M月d日 HH:mm");
                    return sdf.format(c.getTime());
                } else {
                    SimpleDateFormat sdf = null;
                    if (format != null && !format.equalsIgnoreCase("")) {
                        sdf = new SimpleDateFormat(format);

                    } else {
                        sdf = new SimpleDateFormat("yyyy年M月d日 HH:mm");
                    }
                    return sdf.format(c.getTime());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }




    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    private static int daysBetween(Date smdate, Date bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }



    /**
     * 判断两个时间间隔是不是大于5分钟
     */
    public static boolean compareTimestamp5Min(long currentTimestap, long oldTimestap) {
        return Math.abs((currentTimestap - oldTimestap) / (60 * 1000)) >= 5;
    }

    /**
     * 判断两个时间间隔是不是大于5分钟
     */
    public static boolean compareTimestamp5Min(String currentTimestap, String oldTimestap) throws ParseException {
        return Math.abs((FORMAT_yyyy_MM_dd_H_m_s.parse(currentTimestap).getTime() - FORMAT_yyyy_MM_dd_H_m_s.parse(oldTimestap).getTime()) / (60 * 1000)) >= 5;
    }


    public static float compareTimestampHour(long currentTimestap, long oldTimestap) {
        return Math.abs((currentTimestap - oldTimestap) / (1.0f * 60 * 60 * 1000));
    }


    public static String getTimeStringAutoShort2(String srcStr, boolean mustIncludeTime) throws ParseException {
        Date srcDate = FORMAT_yyyy_MM_dd_H_m_s.parse(srcStr);
        String ret = "";
        try {
            GregorianCalendar gcCurrent = new GregorianCalendar();
            gcCurrent.setTime(new Date());
            int currentYear = gcCurrent.get(GregorianCalendar.YEAR);
            int currentMonth = gcCurrent.get(GregorianCalendar.MONTH) + 1;
            int currentDay = gcCurrent.get(GregorianCalendar.DAY_OF_MONTH);
            GregorianCalendar gcSrc = new GregorianCalendar();
            gcSrc.setTime(srcDate);
            int srcYear = gcSrc.get(GregorianCalendar.YEAR);
            int srcMonth = gcSrc.get(GregorianCalendar.MONTH) + 1;
            int srcDay = gcSrc.get(GregorianCalendar.DAY_OF_MONTH);
            // 要额外显示的时间分钟
            String timeExtraStr = (mustIncludeTime ? " " + getTimeString(srcDate, "HH:mm") : "");
            // 当年
            if (currentYear == srcYear) {
                long currentTimestamp = gcCurrent.getTimeInMillis();
                long srcTimestamp = gcSrc.getTimeInMillis();
                // 相差时间（单位：毫秒）
                long delta = (currentTimestamp - srcTimestamp);
                // 当天（月份和日期一致才是）
                if (currentMonth == srcMonth && currentDay == srcDay) {
                    // 时间相差60秒以内
                    if (delta < 60 * 1000)
                        ret = "刚刚";
                        // 否则当天其它时间段的，直接显示“时:分”的形式
                    else
                        ret = getTimeString(srcDate, "HH:mm");
                }
                // 当年 && 当天之外的时间（即昨天及以前的时间）
                else {
                    // 昨天（以“现在”的时候为基准-1天）
                    GregorianCalendar yesterdayDate = new GregorianCalendar();
                    yesterdayDate.add(GregorianCalendar.DAY_OF_MONTH, -1);

                    // 前天（以“现在”的时候为基准-2天）
                    GregorianCalendar beforeYesterdayDate = new GregorianCalendar();
                    beforeYesterdayDate.add(GregorianCalendar.DAY_OF_MONTH, -2);

                    // 用目标日期的“月”和“天”跟上方计算出来的“昨天”进行比较，是最为准确的（如果用时间戳差值
                    // 的形式，是不准确的，比如：现在时刻是2019年02月22日1:00、而srcDate是2019年02月21日23:00，
                    // 这两者间只相差2小时，直接用“delta/(3600 * 1000)” > 24小时来判断是否昨天，就完全是扯蛋的逻辑了）
                    if (srcMonth == (yesterdayDate.get(GregorianCalendar.MONTH) + 1)
                            && srcDay == yesterdayDate.get(GregorianCalendar.DAY_OF_MONTH)) {
                        ret = "昨天" + timeExtraStr;// -1d
                    }
                    // “前天”判断逻辑同上
                    else if (srcMonth == (beforeYesterdayDate.get(GregorianCalendar.MONTH) + 1)
                            && srcDay == beforeYesterdayDate.get(GregorianCalendar.DAY_OF_MONTH)) {
                        ret = "前天" + timeExtraStr;// -2d
                    } else {
                        // 跟当前时间相差的小时数
                        long deltaHour = (delta / (3600 * 1000));
                        // 如果小于 7*24小时就显示星期几
                        if (deltaHour < 7 * 24) {
                            String[] weekday = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
                            // 取出当前是星期几
                            String weedayDesc = weekday[gcSrc.get(GregorianCalendar.DAY_OF_WEEK) - 1];
                            ret = weedayDesc + timeExtraStr;
                        }
                        // 否则直接显示完整日期时间
                        else
                            ret = getTimeString(srcDate, "yyyy/M/d") + timeExtraStr;
                    }
                }
            } else
                ret = getTimeString(srcDate, "yyyy/M/d") + timeExtraStr;
        } catch (Exception e) {
            Log.e("TimestampUtils" + "123456", "【DEBUG-getTimeStringAutoShort】计算出错：" + e.getMessage() + " 【NO】");
        }
        return ret;
    }

    public static String getTimeString(Date dt, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);//"yyyy-MM-ClassifyAdapter HH:mm:MedicineRepository"
            sdf.setTimeZone(TimeZone.getDefault());
            return sdf.format(dt);
        } catch (Exception e) {
            return "";
        }
    }
}
