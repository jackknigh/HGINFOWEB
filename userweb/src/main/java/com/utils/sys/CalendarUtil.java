package com.utils.sys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 自定义事件 CalendarUtil
 * @description 时间函数util
 * @author zhou_xtian
 * @date 2018/6/12 14:21
 */
public class CalendarUtil {

    /**
     * 方法:inTheTime
     * @description 判断两个时间是否为同一天
     * @param calendar 时间对象1
     * @param time 时间对象2
     * @return boolean
     * @author zhou_xtian
     * @date 2018/6/12 10:20
     */
    public static boolean inTheTime(Calendar calendar, Date time){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_YEAR);

        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(time);
        return year == newCalendar.get(Calendar.YEAR) && month == newCalendar.get(Calendar.MONTH)+1
                && day == newCalendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 方法:getBeginTime
     * @description 获取当天开始时间
     * @param calendar
     * @return java.util.Date
     * @author zhou_xtian
     * @date 2018/6/12 10:28
     */
    public static Date getBeginTime(Calendar calendar){
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(calendar.getTime());
        newCalendar.set(Calendar.HOUR_OF_DAY, 0);
        newCalendar.set(Calendar.MINUTE, 0);
        newCalendar.set(Calendar.SECOND, 0);
        //设置时间为前1秒，因为在hql的between里是不含首的
        newCalendar.add(Calendar.SECOND,-1);
        return newCalendar.getTime();
    }

    /**
     * 方法:getBeginTime2
     * @description 获取当天开始时间
     * @param calendar
     * @return java.util.Date
     * @author zhou_xtian
     * @date 2018/10/10 08:56
     */
    public static Date getBeginTime2(Calendar calendar){
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(calendar.getTime());
        newCalendar.set(Calendar.HOUR_OF_DAY, 0);
        newCalendar.set(Calendar.MINUTE, 0);
        newCalendar.set(Calendar.SECOND, 0);
        return newCalendar.getTime();
    }

    /**
     * 方法:getEndTime
     * @description 获取当天结束时间
     * @param calendar
     * @return java.util.Date
     * @author zhou_xtian
     * @date 2018/6/12 10:28
     */
    public static Date getEndTime(Calendar calendar){
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(calendar.getTime());
        newCalendar.set(Calendar.HOUR_OF_DAY, 23);
        newCalendar.set(Calendar.MINUTE, 59);
        newCalendar.set(Calendar.SECOND, 59);
        return newCalendar.getTime();
    }

    /**
     * 方法:combineDate
     * @description 时间段结合
     * @param dateList
     * @param newBeginDate
     * @param newEndDate
     * @return java.util.List<java.util.Date[]>
     * @author zhou_xtian
     * @date 2018/6/12 13:38
     */
    public static List<Date[]> combineDate(List<Date[]> dateList, Calendar calendar, Date newBeginDate, Date newEndDate){
        if (null == newBeginDate || null == newEndDate) {
            return dateList;
        }
        //重新设置时间为同一天
        newBeginDate = setSameDate(calendar,newBeginDate);
        newEndDate = setSameDate(calendar,newEndDate);

        Date[] date = new Date[2];
        date[0] = newBeginDate;
        date[1] = newEndDate;

        List<Date[]> returnList = new ArrayList<>();
        returnList.add(date);
        for (Date[] dateArray:dateList) {
            Date beginDate = dateArray[0];
            Date endDate = dateArray[1];
            if (beginDate.getTime()>date[1].getTime() || date[0].getTime()>endDate.getTime()) {
                //没有交集，添加新list
                returnList.add(dateArray);
            } else {
                //有交集，融合
                date[0] = beginDate.getTime()<date[0].getTime()?beginDate:date[0];
                date[1] = endDate.getTime()>date[1].getTime()?endDate:date[1];
            }
        }
        return returnList;
    }

    /**
     * 方法:differenceDate
     * @description 取出dateList对于传入时间段的差集
     * @param dateList
     * @param calendar
     * @param newBeginDate
     * @param newEndDate
     * @return java.util.List<java.util.Date[]>
     * @author zhou_xtian
     * @date 2018/6/18 22:38
     */
    public static List<Date[]> differenceDate(List<Date[]> dateList, Calendar calendar, Date newBeginDate, Date newEndDate){
        if (null == newBeginDate || null == newEndDate) {
            return dateList;
        }
        //重新设置时间为同一天
        newBeginDate = setSameDate(calendar,newBeginDate);
        newEndDate = setSameDate(calendar,newEndDate);

        Date[] date = new Date[2];

        List<Date[]> returnList = new ArrayList<>();
        for (Date[] dateArray:dateList) {
            Date beginDate = dateArray[0];
            Date endDate = dateArray[1];
            if (beginDate.getTime()>newEndDate.getTime() || endDate.getTime()<newBeginDate.getTime()) {
                //没有交集，不处理原封不动返回
                returnList.add(dateArray);
            } else {
                //有交集
                //当dateList被包裹在内，剔除该时间段
                if (beginDate.getTime()>newBeginDate.getTime() && endDate.getTime()<newEndDate.getTime()) {
                    continue;
                }
                //当传入的时间段被包裹在内，则切开dateList
                if (beginDate.getTime()<newBeginDate.getTime() && endDate.getTime()>newEndDate.getTime()) {
                    date[0] = newEndDate;
                    date[1] = endDate;
                    returnList.add(date);
                    date = new Date[2];
                    date[0] = beginDate;
                    date[1] = newBeginDate;
                    returnList.add(date);
                    continue;
                }
                //默认的正常交集
                date[0] = beginDate.getTime()<newBeginDate.getTime()?beginDate:newEndDate;
                date[1] = endDate.getTime()>newEndDate.getTime()?endDate:newBeginDate;
                returnList.add(date);
            }
        }
        return returnList;
    }

    /**
     * 方法:ifMixedDate
     * @description 是否有交集
     * @param dateArray1 时间段1
     * @param dateArray2 时间段2
     * @return boolean
     * @author zhou_xtian
     * @date 2018-09-11 14:55
     */
    public static boolean ifMixedDate(Date[] dateArray1, Date[] dateArray2){
        Date beginDate1 = dateArray1[0];
        Date endDate1 = dateArray1[1];
        Date beginDate2 = dateArray2[0];
        Date endDate2 = dateArray2[1];

        if (beginDate1.getTime()>=endDate2.getTime() || endDate1.getTime()<=beginDate2.getTime()) {
            //没有交集
            return false;
        } else {
            //有交集
            return true;
        }
    }

    /**
     * 方法:setSameDate
     * @description 设置相同的年、月、日
     * @param calendar 指定的日期
     * @param time 被设置的日期
     * @return java.util.Date
     * @author zhou_xtian
     * @date 2018/6/18 17:46
     */
    public static Date setSameDate(Calendar calendar, Date time) {
        if (null == time) {
            return calendar.getTime();
        }
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(time);
        timeCalendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
        timeCalendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
        timeCalendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR));
        return timeCalendar.getTime();
    }

    /**
     * 方法:minusDateTOMinutes
     * @description 计算两时间相差分钟数
     * @param date1
     * @param date2
     * @return int
     * @author zhou_xtian
     * @date 2018/8/3 16:42
     */
    public static int minusDateTOMinutes(Date date1, Date date2){
        long millisecond = date1.getTime() - date2.getTime();
        return (int) Math.floor(millisecond/1000/60);
    }

    /**
     * 方法:setNextHalfOrFullPoint
     * @description 设置时间到下一个半点或者整点
     * @param calendar
     * @return java.util.Calendar
     * @author zhou_xtian
     * @date 2018-09-10 17:10
     */
    public static Calendar setNextHalfOrFullPoint(Calendar calendar){
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(calendar.getTime());
        newCalendar.add(Calendar.MINUTE,15);
        if (newCalendar.get(Calendar.MINUTE) <= 30) {
            //默认半点
            newCalendar.set(Calendar.MINUTE,30);
        } else {
            //如果时间在半点之后，则调整为下个小时数的整点
            newCalendar.add(Calendar.HOUR,1);
            newCalendar.set(Calendar.MINUTE,0);
        }
        return newCalendar;
    }

    /**
     * 方法:getLaterOne
     * @description 获取两个时间中较小的一个
     * @param date1
     * @param date2
     * @return java.util.Date
     * @author zhou_xtian
     * @date 2018-09-21 8:41
     */
    public static Date getEarlierOne(Date date1, Date date2){
        if (date1.getTime()>date2.getTime()) {
            return date2;
        }
        return date1;
    }

    /**
     * 方法:getLaterOne
     * @description 获取两个时间中较大的一个
     * @param date1
     * @param date2
     * @return java.util.Date
     * @author zhou_xtian
     * @date 2018-09-21 8:41
     */
    public static Date getLaterOne(Date date1, Date date2){
        if (date1.getTime()<date2.getTime()) {
            return date2;
        }
        return date1;
    }
}
