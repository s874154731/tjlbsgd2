package com.sgd.tjlb.zhxf.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * @ProjectName: AssembleChat2
 * @Package: com.duanfeng.assemblechat.utils
 * @ClassName: DateUtil
 * @Description: 日期工具类
 * @CreateDate: 2022/2/21/021 14:46
 * @UpdateUser: shi
 * @UpdateDate: 2022/2/21/021 14:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DateUtil {
    public static final String YEAR_MONTH = "yyyy-MM";
    public static final String STANDARD_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FULL_TIME = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_CN = "yyyy年MM月dd号";
    public static final String YEAR_MONTH_DAY_CN2 = "yyyy年MM月dd日";
    public static final String HOUR_MINUTE_SECOND = "HH:mm:ss";
    public static final String HOUR_MINUTE_SECOND_CN = "HH时mm分ss秒";
    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    public static final String HOUR = "HH";
    public static final String MINUTE = "mm";
    public static final String SECOND = "ss";
    public static final String MILLISECOND = "SSS";
    public static final String YESTERDAY = "昨天";
    public static final String TODAY = "今天";
    public static final String TOMORROW = "明天";
    public static final String SUNDAY = "星期日";
    public static final String MONDAY = "星期一";
    public static final String TUESDAY = "星期二";
    public static final String WEDNESDAY = "星期三";
    public static final String THURSDAY = "星期四";
    public static final String FRIDAY = "星期五";
    public static final String SATURDAY = "星期六";
    public static final String[] WEEK_DAYS = {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};

    /**
     * 获取标准时间
     *
     * @return 例如 2021-07-01 10:35:53
     */
    public static String getDateTime() {
        return new SimpleDateFormat(STANDARD_TIME, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    public static long getNowTimeStamp() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }

    /**
     * 获取当前时间
     *
     * @param format 格式 eq:yyyy-MM-dd
     * @return 2022-02-02
     */
    public static String getDateTime(String format) {
        return new SimpleDateFormat(format, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取完整时间
     *
     * @return 例如 2021-07-01 10:37:00.748
     */
    public static String getFullDateTime() {
        return new SimpleDateFormat(FULL_TIME, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取年月日(今天)
     *
     * @return 例如 2021-07-01
     */
    public static String getTheYearMonthAndDay() {
        return new SimpleDateFormat(YEAR_MONTH_DAY, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取年月日
     *
     * @return 例如 2021年07月01号
     */
    public static String getTheYearMonthAndDayCn() {
        return new SimpleDateFormat(YEAR_MONTH_DAY_CN, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取年月日
     *
     * @param delimiter 分隔符
     * @return 例如 2021年07月01号
     */
    public static String getTheYearMonthAndDayDelimiter(CharSequence delimiter) {
        return new SimpleDateFormat(YEAR + delimiter + MONTH + delimiter + DAY, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取时分秒
     *
     * @return 例如 10:38:25
     */
    public static String getHoursMinutesAndSeconds() {
        return new SimpleDateFormat(HOUR_MINUTE_SECOND, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取时分秒
     *
     * @return 例如 10时38分50秒
     */
    public static String getHoursMinutesAndSecondsCn() {
        return new SimpleDateFormat(HOUR_MINUTE_SECOND_CN, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取时分秒
     *
     * @param delimiter 分隔符
     * @return 例如 2021/07/01
     */
    public static String getHoursMinutesAndSecondsDelimiter(CharSequence delimiter) {
        return new SimpleDateFormat(HOUR + delimiter + MINUTE + delimiter + SECOND, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取年
     *
     * @return 例如 2021
     */
    public static String getYear() {
        return new SimpleDateFormat(YEAR, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取月
     *
     * @return 例如 07
     */
    public static String getMonth() {
        return new SimpleDateFormat(MONTH, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取天
     *
     * @return 例如 01
     */
    public static String getDay() {
        return new SimpleDateFormat(DAY, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取小时
     *
     * @return 例如 10
     */
    public static String getHour() {
        return new SimpleDateFormat(HOUR, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取分钟
     *
     * @return 例如 40
     */
    public static String getMinute() {
        return new SimpleDateFormat(MINUTE, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取指定日期的前几天或后几天
     *
     * @param dateStr 指定日期
     * @param format  日期格式
     * @param flag    true为获取后几天, false:为获取前几天
     * @param day     指定需要获取的天数
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate(String dateStr, String format, boolean flag, int day) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day1 = c.get(Calendar.DATE);
        if (flag) {
            c.set(Calendar.DATE, day1 + day);
        } else {
            c.set(Calendar.DATE, day1 - day);
        }
        return new SimpleDateFormat(format).format(c.getTime());
    }

    /**
     * 获取秒
     *
     * @return 例如 58
     */
    public static String getSecond() {
        return new SimpleDateFormat(SECOND, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取毫秒
     *
     * @return 例如 666
     */
    public static String getMilliSecond() {
        return new SimpleDateFormat(MILLISECOND, Locale.CHINESE).format(new Date());
    }

    /**
     * 获取时间戳
     *
     * @return 例如 1625107306051
     */
    public static long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取第二天凌晨0点时间戳
     *
     * @return
     */
    public static long getMillisNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        //日期加1
        cal.add(Calendar.DAY_OF_YEAR, 1);
        //时间设定到0点整
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 将时间转换为时间戳
     *
     * @param time 例如 2021-07-01 10:44:11
     * @return 1625107451000
     */
    public static long dateToStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_TIME, Locale.CHINESE);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(date).getTime();
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 2021-07-01 10:47:17
     */
    public static String stampToDate(long timeMillis) {
        return new SimpleDateFormat(STANDARD_TIME, Locale.CHINESE).format(new Date(timeMillis));
    }

    public static String stampToDate2(long timeMillis) {
        timeMillis = timeMillis * 1000;
        return new SimpleDateFormat(STANDARD_TIME, Locale.CHINESE).format(new Date(timeMillis));
    }

    /**
     * 获取今天是星期几
     *
     * @return 例如 星期四
     */
    public static String getTodayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (index < 0) {
            index = 0;
        }
        return WEEK_DAYS[index];
    }

    /**
     * 根据输入的日期时间计算是星期几
     *
     * @param dateTime 例如 2021-06-20
     * @return 例如 星期日
     */
    public static String getWeek(String dateTime) {
        Calendar cal = Calendar.getInstance();
        if (TextUtils.isEmpty(dateTime)) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault());
            Date date;
            try {
                date = sdf.parse(dateTime);
            } catch (ParseException e) {
                date = null;
                e.printStackTrace();
            }
            if (date != null) {
                cal.setTime(new Date(date.getTime()));
            }
        }
        return WEEK_DAYS[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取输入日期的昨天
     *
     * @param date 例如 2021-07-01
     * @return 例如 2021-06-30
     */
    public static String getYesterday(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        return new SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault()).format(date);
    }

    /**
     * 获取输入日期的明天
     *
     * @param date 例如 2021-07-01
     * @return 例如 2021-07-02
     */
    public static String getTomorrow(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, +1);
        date = calendar.getTime();
        return new SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault()).format(date);
    }

    /**
     * 根据年月日计算是星期几并与当前日期判断  非昨天、今天、明天 则以星期显示
     *
     * @param dateTime 例如 2021-07-03
     * @return 例如 星期六
     */
    public static String getDayInfo(String dateTime) {
        String dayInfo;
        String yesterday = getYesterday(new Date());
        String today = getTheYearMonthAndDay();
        String tomorrow = getTomorrow(new Date());

        if (dateTime.equals(yesterday)) {
            dayInfo = YESTERDAY;
        } else if (dateTime.equals(today)) {
            dayInfo = TODAY;
        } else if (dateTime.equals(tomorrow)) {
            dayInfo = TOMORROW;
        } else {
            dayInfo = getWeek(dateTime);
        }
        return dayInfo;
    }

    /**
     * 获取本月天数
     *
     * @return 例如 31
     */
    public static int getCurrentMonthDays() {
        Calendar calendar = Calendar.getInstance();
        //把日期设置为当月第一天
        calendar.set(Calendar.DATE, 1);
        //日期回滚一天，也就是最后一天
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得指定月的天数
     *
     * @param year  例如 2021
     * @param month 例如 7
     * @return 例如 31
     */
    public static int getMonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        //把日期设置为当月第一天
        calendar.set(Calendar.DATE, 1);
        //日期回滚一天，也就是最后一天
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }


    /**
     * 判断指定日期是否为新年的第一天，2021-01-01\2021-02-01
     *
     * @param strDate
     * @return
     */
    public static boolean isFirstDayOfYear(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(YEAR_MONTH_DAY);
        ParsePosition pos = new ParsePosition(0);
        Date tempDate = formatter.parse(strDate, pos);
        //设置日期
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(tempDate);

        return tempCalendar.get(Calendar.DAY_OF_YEAR) == 1;
    }

    /**
     * 判断指定日期是否为新年的第一个月，2021-01\2021-02
     *
     * @param strDate
     * @return
     */
    public static boolean isFirstMonthOfYear(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(YEAR_MONTH);
        ParsePosition pos = new ParsePosition(0);
        Date tempDate = formatter.parse(strDate, pos);
        //设置日期
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(tempDate);

//        int month = tempCalendar.get(Calendar.MONTH);//0为一月

        return tempCalendar.get(Calendar.MONTH) == 0;
    }

    /***
     * 比较两个日期的大小
     * @param beginDay
     * @param endDay
     * @return
     */
    public static boolean compareTwoDay(Date beginDay, Date endDay) {
        boolean boo = false;
        if (beginDay.before(endDay)) {
            boo = false;
        } else {
            boo = true;
        }
        return boo;
    }

    /**
     * 比较两个日期的大小
     *
     * @param beginDay
     * @param endDay
     * @return
     */
    public static boolean compareTwoDay(String beginDay, String endDay, String format) {
        Date bDay = getDatefromString(beginDay, format);
        Date eDay = getDatefromString(endDay, format);
        boolean boo;
        if (bDay.before(eDay)) {
            boo = false;
        } else {
            boo = true;
        }
        return boo;
    }

    /**
     * String 类型日期 转date
     *
     * @param strDate str日期
     * @param format  格式
     * @return Date 日期
     */
    public static Date getDatefromString(String strDate, String format) {
        Date date;
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(format);
            date = formatter.parse(strDate);
            return date;
        } catch (Exception e) {
            Log.e("eee", e.toString());
        }
        return null;
    }

    /**
     * String 类型日期 转date
     *
     * @param date 日期
     * @return String 日期
     */
    public static String getStringFromDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
        return formatter.format(date);
    }

    /**
     * 根据日期获取年份
     *
     * @param date 日期
     * @return 年 int
     */
    public static int getIntYear(Date date) {
        Calendar cdate = Calendar.getInstance();
        cdate.setTime(date);
        return cdate.get(Calendar.YEAR);
    }

    /**
     * string 转 String
     *
     * @param day  日期
     * @param type 格式
     * @return
     */
    public static String getStringFromString(String day, String type) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(type);
        Date date = null;
        try {
            date = formatter.parse(day);
            return formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 获取date from String
     * @param day
     * @return
     */
    public static Date getDateFromString(String day) {
        SimpleDateFormat formatter = new SimpleDateFormat();
        try {
            Date date = formatter.parse(day);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 获取date from String
     * @param day
     * @return
     */
    public static Date getDateFromString(String day, String format) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(day);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * 获取date是几天前(待完成)
     * @param day
     * @return
     */
    public static Date getAFewDaysAgo(String day) {
        return null;
    }


    /***
     * 判断两天是否为同一年
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isTheSameYear(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR));
    }

    /***
     * 判断两天是否为同一天
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isTheSameDay(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))
                && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 根据日期获取它的年月日
     *
     * @param date 日期
     * @return list  0：年 1：月 2：日
     */
    public static List<Integer> getYearMonthDay(Date date) {
        List<Integer> dateList = new ArrayList<>();
        Calendar dateCalender = Calendar.getInstance();
        dateCalender.setTime(date);

        dateList.add(dateCalender.get(Calendar.YEAR));
        dateList.add(dateCalender.get(Calendar.MONTH) + 1);
        dateList.add(dateCalender.get(Calendar.DAY_OF_MONTH));

        return dateList;
    }


    /**
     * 将时间转换为 刚刚、几分钟前、几小时前、等。。。
     *
     * @param long_time
     * @return
     */
    public static String getTimeStateNew(String long_time) {
        if (TextUtils.isEmpty(long_time))
            return "";

        String long_by_13 = "1000000000000";
        String long_by_10 = "1000000000";
        if (Long.parseLong(long_time) / Long.parseLong(long_by_13) < 1) {
            if (Long.parseLong(long_time) / Long.parseLong(long_by_10) >= 1) {
                long_time = long_time + "000";
            }
        }
        Timestamp time = new Timestamp(Long.parseLong(long_time));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // System.out.println("传递过来的时间:"+format.format(time));
        // System.out.println("现在的时间:"+format.format(now));
        long day_conver = 1000 * 60 * 60 * 24;
        long hour_conver = 1000 * 60 * 60;
        long min_conver = 1000 * 60;
        long time_conver = now.getTime() - time.getTime();
        long temp_conver;
        // System.out.println("天数:"+time_conver/day_conver);
        if ((time_conver / day_conver) < 3) {
            temp_conver = time_conver / day_conver;
            if (temp_conver <= 2 && temp_conver >= 1) {
                return temp_conver + "天前";
            } else {
                temp_conver = (time_conver / hour_conver);
                if (temp_conver >= 1) {
                    return temp_conver + "小时前";
                } else {
                    temp_conver = (time_conver / min_conver);
                    if (temp_conver >= 1) {
                        return temp_conver + "分钟前";
                    } else {
                        return "刚刚";
                    }
                }
            }
        } else {
            return format.format(time);
        }
    }

    /**
     * 根据Date获取指定格式日期
     *
     * @param date 日期
     * @return String
     */
    public static String getStrFromDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 秒转换为时分秒
     *
     * @param seconds 秒
     * @return
     */
    public static String secondsToFormat(int seconds) {
        SimpleDateFormat formatter;
        if (seconds < 60) {
            formatter = new SimpleDateFormat("m:ss");
        } else if (seconds < 3600) {
            formatter = new SimpleDateFormat("mm:ss");
        } else {
            formatter = new SimpleDateFormat("HH:mm:ss");
        }
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return formatter.format(seconds * 1000);
    }


    /**
     * @param time    传入的时间 20061002
     * @param format1 传入的时间格式  yyyyMMdd
     * @param format2 转化的时间格式  yyyy年MM月dd日
     * @return
     */
    public static String setDateFormat(String time, String format1, String format2) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        String returnTime = "";
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat ft = new SimpleDateFormat(format1);
            Date date = ft.parse(time);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat ft2 = new SimpleDateFormat(format2);
            returnTime = ft2.format(date);
            return returnTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static long dateToStamp2(String time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNull(date).getTime();
    }

    /**
     * 获取身份证是否过期
     *
     * @param time 时间  2021-07-01 10:44:11
     * @return
     */
    public static boolean isLate(String time) {
        long nowTime = dateToStamp2(getDateTime("yyyy-MM-dd"));
        long toTime = dateToStamp2(time);
        return nowTime > toTime;
    }

    /**
     * 判断当前时间是否是三天前
     *
     * @param time 时间
     * @return
     */
    public static boolean threeDaysAgo(String time) {
        long nowTime = dateToStamp(time);
        long agoTime = dateToStamp(getThreeDaysAgoDate());
        return nowTime >= agoTime;
    }

    private static String getThreeDaysAgoDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_TIME);
        String maxDateStr = DateUtil.getDateTime();
        String minDateStr = "";
        Calendar calc = Calendar.getInstance();
        try {
            calc.setTime(sdf.parse(maxDateStr));
            calc.add(calc.DATE, -3);
            Date minDate = calc.getTime();
            minDateStr = sdf.format(minDate);
//            System.out.println("minDateStr:"+minDateStr);//minDateStr:2017-01-09   正确！！！！
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return minDateStr;
    }

}
