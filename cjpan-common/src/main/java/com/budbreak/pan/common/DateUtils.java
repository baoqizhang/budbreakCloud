package com.budbreak.pan.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 代码生成器自动生成
 * @author:
 * @Createed Date: 2019-12-5 10:53:20
 * @ModificationHistory: Who  When  What
 * ---------     -------------   --------------------------------------
 **/
public class DateUtils {

    public static final String DTLONG = "yyyyMMddHHmmss";
    public static final String SIMPLE = "yyyy-MM-dd HH:mm:ss";
    public static final String DTSHORT = "yyyyMMdd";
    public static final String DTDAY = "yyyy-MM-dd";
    public static final String DTMONTH = "yyyy-MM";
    public static final String MONTHSHORT = "yyyyMM";

    public static Date getNextMonthDay(Date date, Integer index){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.add(Calendar.MONTH, 1);
        Integer nextMaxIndex = calendar.getMaximum(Calendar.DAY_OF_MONTH);
        if(index > nextMaxIndex) {
            index = nextMaxIndex - 1;
        }
        calendar.set(Calendar.DAY_OF_MONTH, index);
        return calendar.getTime();
    }

    public static Date getNextMonthDay(Integer index){
        return getNextMonthDay(new Date(), index);
    }

    public static Date parseToDate(String date) {
        if(date == null || StringUtils.isEmpty(date)){
            return null;
        }
        DateFormat df = null;
        if(date.length() == DTDAY.length()){
            df = new SimpleDateFormat(DTDAY);
        }else if(date.length() == SIMPLE.length()){
            df = new SimpleDateFormat(SIMPLE);
        }else if(date.length() == DTMONTH.length()){
            df = new SimpleDateFormat(DTMONTH);
        }
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getDay() {
        return getDay(new Date());
    }

    private static String getDay(Date date) {
        DateFormat df = new SimpleDateFormat(MONTHSHORT);
        return df.format(date);
    }

    public static Integer getNextDateIndex(Integer index) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.add(Calendar.MONTH, 1);
        Integer nextMaxIndex = calendar.getMaximum(Calendar.DAY_OF_MONTH);
        if(index > nextMaxIndex) {
            index = nextMaxIndex - 1;
        }
        return index;
    }

    public static Date getMonth(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, i);
        return calendar.getTime();
    }

    public static String getDateStr() {
        DateFormat df = new SimpleDateFormat(DTLONG);
        return df.format(new Date());
    }

    public static String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DTSHORT);
        return simpleDateFormat.format(new Date());
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Integer getDayOfMonth(Date checkInDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Date getDate(Integer cycle, Date preDate, Integer index) {
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(preDate);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Integer nextMaxIndex = calendar.getMaximum(Calendar.DAY_OF_MONTH);
        if(index > nextMaxIndex){
            index = nextMaxIndex - 1;
        }
        calendar.set(Calendar.DAY_OF_MONTH, index);
        calendar.add(Calendar.MONTH, cycle);
        return calendar.getTime();
    }

    public static String toDateString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Integer getWeek(Date scheduleDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(scheduleDate);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Date转LocalDate
     * @param date
     * @return
     */
    public static LocalDate date2LocalDate(Date date) {

        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * 计算月份差
     * @param startDate 起始时间
     * @param endDate 结束时间
     * @param roundUp 不满一月是否向上取整
     * @return 月份
     */
    public static Integer monthBetween(Date startDate, Date endDate, boolean roundUp) {
        LocalDate from = date2LocalDate(startDate);
        LocalDate to = date2LocalDate(endDate);

        return monthBetween(from, to, roundUp);
    }

    /**
     * 计算月份差
     * @param startLocalDate 起始时间
     * @param endLocalDate 结束时间
     * @param roundUp 不满一月是否向上取整
     * @return 月份
     */
    public static Integer monthBetween(LocalDate startLocalDate, LocalDate endLocalDate, boolean roundUp) {
        Period period = Period.between(startLocalDate, endLocalDate);
        int month = period.getMonths();
        // 如果是n个月零几天，月份+1
        if (roundUp && period.getDays() > 0) {
            month ++;
        }

        return month;
    }

    /**
     * 取得对应天的开始时间
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        if (date == null){
            return null;
        }

        Instant instant = date2LocalDate(date)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();

        return Date.from(instant);
    }

    /**
     * 取得对应天的结束时间
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        if (date == null){
            return null;
        }

        Instant instant = date2LocalDate(date)
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        return Date.from(instant);
    }

    /**
     * 取得对应天的结束时间
     * @param date
     * @return
     */
    public static String getEndOfDay(String date, String format) {
        if (StringUtils.isEmpty(date)){
            return null;
        }

        if(StringUtils.isEmpty(format)) {
            format = DateUtils.SIMPLE;
        }

        Date endOfDay = getEndOfDay(parseToDate(date));

        return toDateString(endOfDay, format);
    }

    /**
     * 判断时间是否在指定时间范围内
     * @param date 需要判断的时间
     * @param rangeBegin 时间范围开始
     * @param rangeEnd 时间范围结束
     * @return
     */
    public static boolean isDateBetween(Date date, Date rangeBegin, Date rangeEnd) {
        // date >= begin
        return (date.after(rangeBegin) || date.equals(rangeBegin))
                // && date <= end
                && (date.before(rangeEnd) || date.equals(rangeEnd));
    }

    public static Date max(Date date1, Date date2) {
        return date1.after(date2) ? date1 : date2;
    }

    /**
     * 时间戳转化为正常时间
     * @param timestamp
     * @return
     */
    public static String stampToDate(long timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE);
        sdf.format(new Date(timestamp));
        return sdf.format(new Date(timestamp));
    }

    /**
     * @param date 时间 返回String
     * @param num 天
     * @return
     */
    public static String addDay(Date date, int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        SimpleDateFormat sdf = new SimpleDateFormat(DTDAY);
        return sdf.format(calendar.getTime());
    }

    /**
     * @param date 时间 返回Date
     * @param num 天
     * @return
     */
    public static Date addDays(Date date, int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return calendar.getTime();
    }

    /**
     * @param date 时间
     * @param num 天
     * @return
     */
    public static Date addHour(Date date, int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, num);
        return calendar.getTime();
    }

    /**
     * 转换成yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE);
        return sdf.format(date);
    }

}
