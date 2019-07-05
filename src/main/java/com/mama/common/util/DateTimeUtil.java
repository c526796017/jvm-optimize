/**
 * FileName: DateTimeUtil
 * Author:   何潮
 * Date:     2018/12/14 14:19
 * Description: 日期时间工具类
 * History:
 * <author>          <time>                <version>          <desc>
 * 何潮           2018/12/14 14:19           1.0             创建版本
 */
package com.mama.common.util;


import com.mama.common.constant.Constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期时间工具类
 *
 * @author 何潮
 * @create 2018/12/14 14:19
 * @since 1.0.0
 */
public class DateTimeUtil {

    public enum TimeFormat {
        //短时间格式
        SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
        SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
        SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
        SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

        //长时间格式
        LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
        LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
        LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
        LONG_DATE_PATTERN_NONE("yyyyMMddHHmmss"),

        //长时间格式 带毫秒
        LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMddHHmmssSSS");


        private transient DateTimeFormatter formatter;

        TimeFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }

    /**
     * 时间格式，时分秒
     */
    public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";

    /**
     * 当天结束时间：yyyy-MM-dd 23:59:59
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_23_59_59 = "yyyy-MM-dd 23:59:59";

    /**
     * 当天开始时间：yyyy-MM-dd 00:00:00
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_00_00_00 = "yyyy-MM-dd 00:00:00";

    /**
     * 日期格式yyyy-MM-dd
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 标准时间格式yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准时间格式yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 返回当前的日期
     *
     * @return
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * 返回当前日期时间
     *
     * @return
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 返回当前日期时间毫秒
     *
     * @return
     */
    public static long getCurrentLocalDateTimeMs() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 功能描述: 根据日期字符串返回日期类
     *
     * @param: [date] 必须传日期，不然会报错
     * @return: java.time.LocalDate
     * @auther: 何潮
     * @date: 2018/12/17 10:52
     */
    public static LocalDate stringToLocalDate(String date) {
        TemporalAccessor dateTempora = TimeFormat.SHORT_DATE_PATTERN_LINE.formatter.parse(date);
        return LocalDate.from(dateTempora);
    }

    /**
     * 功能描述: 根据日期时间字符串返回日期时间类
     *
     * @param: [dateTime]必须传日期时间，不然会报错
     * @return: java.time.LocalDateTime
     * @auther: 何潮
     * @date: 2018/12/17 10:49
     */
    public static LocalDateTime stringToLocalDateTime(String dateTime) {
        TemporalAccessor dateTempora = TimeFormat.LONG_DATE_PATTERN_LINE.formatter.parse(dateTime);
        return LocalDateTime.from(dateTempora);
    }

    /**
     * 返回当前日期字符串，必须传日期格式，不然会报错
     *
     * @return
     */
    public static String getCurrentDateStr(DateTimeFormatter formatter) {
        return LocalDate.now().format(formatter);
    }

    /**
     * 返回当前日期时间字符串，必须传日期时间格式，不然会报错
     *
     * @return
     */
    public static String getCurrentDateTimeStr(DateTimeFormatter formatter) {
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 日期相隔天数
     *
     * @param startDateInclusive
     * @param endDateExclusive
     * @return
     */
    public static long periodDays(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return startDateInclusive.until(endDateExclusive, ChronoUnit.DAYS);
    }

    /**
     * 是否当天
     *
     * @param date
     * @return
     */
    public static boolean isToday(LocalDate date) {
        return getCurrentLocalDate().equals(date);
    }

    /**
     * 获取本月的第一天
     *
     * @return
     */
    public static String getFirstDayOfThisMonth() {
        return getCurrentLocalDate().with(TemporalAdjusters.firstDayOfMonth()).format(TimeFormat.SHORT_DATE_PATTERN_LINE.formatter);
    }

    /**
     * 获取本月的最后一天
     *
     * @return
     */
    public static String getLastDayOfThisMonth() {
        return getCurrentLocalDate().with(TemporalAdjusters.lastDayOfMonth()).format(TimeFormat.SHORT_DATE_PATTERN_LINE.formatter);
    }

    public static String getAfterMonth(String inputDate, int number) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(inputDate);//初始日期
        } catch (Exception e) {

        }
        c.setTime(date);//设置日历时间
        c.add(Calendar.MONTH, number);//在日历的月份上增加6个月
        String strDate = sdf.format(c.getTime());//的到你想要得6个月后的日期
        return strDate;
    }

    /**
     * 获取过去几个月的时间
     *
     * @param inputDate
     * @param number
     * @return
     */
    public static String getBefourMonth(String inputDate, int number) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(inputDate);//初始日期
        } catch (Exception e) {

        }
        c.setTime(date);
        c.add(Calendar.MONTH, -number);
        Date m = c.getTime();
        String mon = sdf.format(m);
        return mon;
    }

    /**
     * 获取过去几个月的时间 返回值Date
     *
     * @return java.util.Date
     * @author ChenHaiTao
     * @date 2019/3/13 18:02No such property: code for class: Script1
     */
    public static Date getBefourMonth(Date date, int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.MONTH, -number);
        return calendar.getTime();
    }

    //比较两个时间的大小(时分)，例如12:00,13:12
    public static boolean compTime(String s1, String s2) {
        try {
            if (s1.indexOf(":") < 0 || s1.indexOf(":") < 0) {
                System.out.println("格式不正确");
            } else {
                String[] array1 = s1.split(":");
                int total1 = Integer.valueOf(array1[0]) * 3600 + Integer.valueOf(array1[1]) * 60;
                String[] array2 = s2.split(":");
                int total2 = Integer.valueOf(array2[0]) * 3600 + Integer.valueOf(array2[1]) * 60;
                return total1 - total2 > 0 ? true : false;
            }
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }

    //日期转为字符串，默认yyyy-MM-dd
    public static String dateToString(Date d) {
        return dateToString(d, "yyyy-MM-dd");
    }

    //日期转为字符串，时间格式yyyy-MM-dd HH:mm:ss
    public static String datetoString(Date date) {
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    //日期转为字符串，自定义格式
    public static String dateToString(Date d, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(d);
    }

    //字符串转为日期，默认yyyy-MM-dd
    public static Date stringToDate(String str) {
        return stringToDate(str, "yyyy-MM-dd");
    }

    //字符串转为日期，默认yyyy-MM-dd HH:mm:ss
    public static Date stringtoDate(String str) {
        return stringToDate(str, "yyyy-MM-dd HH:mm:ss");
    }

    //字符串转为日期，自定义格式
    public static Date stringToDate(String str, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date d = sdf.parse(str);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 当天剩余秒数
     *
     * @return
     */
    public static long getRemainingSecondsByDay() {
        Date currentDate = new Date();
        Calendar midnight = Calendar.getInstance();
        midnight.setTime(currentDate);
        midnight.add(midnight.DAY_OF_MONTH, 1);
        midnight.set(midnight.HOUR_OF_DAY, 0);
        midnight.set(midnight.MINUTE, 0);
        midnight.set(midnight.SECOND, 0);
        midnight.set(midnight.MILLISECOND, 0);
        return (midnight.getTime().getTime() - currentDate.getTime()) / 1000;
    }

    /*获得指定天数的i点，i等于几就是几点*/
    public static Date getTimesnights(Date time, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, i);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // 获得当天的i点，i等于几就是几点
    public static Date getTimesnight(int i) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, i);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // 获得当天的后i天的时间，i等于几就是后几天
    public static Date getBehindTimesnight(Date nowTime, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTime);
        cal.add(Calendar.DAY_OF_MONTH, i);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获得本周一0点时间
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    // 获得本周日0点时间
    public static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return cal.getTime();
    }

    //获得上周日的0点
    public static Date getTimesPreSumDay() {
        Calendar preWeekSundayCal = Calendar.getInstance();
        preWeekSundayCal.set(Calendar.DAY_OF_WEEK, 1);
        preWeekSundayCal.set(Calendar.MINUTE, 0);
        preWeekSundayCal.set(Calendar.SECOND, 0);
        preWeekSundayCal.set(Calendar.HOUR_OF_DAY, 0);
        return preWeekSundayCal.getTime();
    }

    //获得上周一的0点
    public static Date getTimesPreMonDay() {
        Calendar preWeekMondayCal = Calendar.getInstance();
        preWeekMondayCal.set(Calendar.DAY_OF_WEEK, 1);
        preWeekMondayCal.set(Calendar.MINUTE, 0);
        preWeekMondayCal.set(Calendar.SECOND, 0);
        preWeekMondayCal.set(Calendar.HOUR_OF_DAY, 0);
        preWeekMondayCal.add(Calendar.DATE, -6);
        return preWeekMondayCal.getTime();
    }

    /**
     * 取指定时间戳日期时间
     *
     * @param dayTimestamp
     * @return
     */
    public static Date getTheDayTimestamp(Long dayTimestamp) {
        Date day = new Date(dayTimestamp);
        String startDateStr = DateTimeUtil.formatDate(day, DateTimeUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        Date startDate = DateTimeUtil.formatDate(startDateStr, DateTimeUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        return startDate;
    }

    /**
     * 取指定时间戳所在天的开始时间(0点0分0秒)
     *
     * @param dayTimestamp
     * @return
     */
    public static Date getTheDayStartTimestamp(Long dayTimestamp) {
        Date day = new Date(dayTimestamp);
        String startDateStr = DateTimeUtil.formatDate(day, DateTimeUtil.DATE_FORMAT_YYYY_MM_DD_00_00_00);
        Date startDate = DateTimeUtil.formatDate(startDateStr, DateTimeUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        return startDate;
    }

    /**
     * 取指定时间戳所在天的结束时间(23点59分59秒)
     *
     * @param dayTimestamp
     * @return
     */
    public static Date getTheDayEndTimestamp(Long dayTimestamp) {
        Date day = new Date(dayTimestamp);
        String endDateStr = DateTimeUtil.formatDate(day, DateTimeUtil.DATE_FORMAT_YYYY_MM_DD_23_59_59);
        Date endDate = DateTimeUtil.formatDate(endDateStr, DateTimeUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        return endDate;
    }

    /**
     * 将时间对象转为字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        DateFormat timeFormator = new SimpleDateFormat(format);
        timeFormator.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return timeFormator.format(date);
    }

    /**
     * 将字符串时间转为时间对象
     *
     * @param strDate
     * @param format
     * @return
     */
    public static Date formatDate(String strDate, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date temp = sdf.parse(strDate);
            return temp;
        } catch (Exception e) {
            throw new RuntimeException(strDate + "不是[" + format + "]格式的日期", e);
        }
    }

    /**
     * 相差分钟数
     *
     * @param pBeginTime
     * @param pEndTime
     * @return
     * @throws Exception
     */
    public static Long timeDiffMin(Date pBeginTime, Date pEndTime) {
        return timeDiffMinByTime(pBeginTime.getTime(), pEndTime.getTime());
    }

    /**
     * 相差分钟数
     *
     * @param beginL
     * @param endL
     * @return
     * @throws Exception
     */
    public static Long timeDiffMinByTime(Long beginL, Long endL) {
        //Long day = (endL - beginL) / 86400000;
        //Long hour = ((endL - beginL) % 86400000) / 3600000;
        Long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
        //return ("实际请假" + day + "小时" + hour + "分钟" + min);
        return min;
    }

    /**
     * 时间戳转为时间字符串
     *
     * @param time
     * @return
     * @throws Exception
     */
    public static String timeToDate(Long time) {
        String str = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            str = format.format(time);
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * 时间戳转为年月日字符串
     *
     * @param time
     * @return
     * @throws Exception
     */
    public static String timeToDateYMD(Long time) {
        String str = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            str = format.format(time);
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * 判断当前时间在开始结束时间之前、之间、之后
     *
     * @param startTime
     * @param endTime
     * @return 1当前时间之前 2当前时间之间 3当前时间之后
     */
    public static Short timeInterval(Long startTime, Long endTime) {
        Long nowTime = new Date().getTime();
        if (nowTime < startTime) {
            return Constant.ONE_SHORT;
        } else if (startTime <= nowTime && nowTime < endTime) {
            return Constant.TWO_SHORT;
        } else if (nowTime >= endTime) {
            return Constant.THREE_SHORT;
        } else {
            return Constant.ZERO_SHORT;
        }
    }

    /**
     * 过去当前时间前后天数的时间戳
     *
     * @param day
     * @return
     */
    public static Long specificDaysStamp(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + day);
        return calendar.getTimeInMillis();
    }

    /**
     * 判断两个日期时间的大小(接受的日期时间格式为 yyyy-MM-dd)
     *
     * @param date1 日期
     * @param date2 日期
     * @return int date1大于date2返回1，返回-1则date1小于date2，返回0则相同
     */
    public static int compareDateYMD(long date1, long date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(df.format(date1));
            Date dt2 = df.parse(df.format(date2));
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "解析日期时间格式出错，期望的字符串格式为[yyyyMMdd HH:mm:ss]");
        }
    }

    /**
     * 获取某个时间点
     *
     * @param curDate
     * @param dateFormat
     * @return
     */
    public static Date getDate(String curDate, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sdf.parse(curDate);//初始日期
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}