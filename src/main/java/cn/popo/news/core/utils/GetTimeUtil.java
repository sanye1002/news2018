package cn.popo.news.core.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-10 下午 6:40
 * 当前时间
 */

public class GetTimeUtil {
    public static String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }
    public static String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
    public static String getMonth(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(new Date());
    }

    public static String getCurrentTimeMillisDiff(Long l,Long ll){
        Long lll = (l-ll)/(1000*60);
        String time = "";
        String s1 = "";
        if(lll<60){
            s1 = BigDecimal.valueOf(Math.floor(lll)).stripTrailingZeros().toPlainString();
            time = lll+"分钟前";
        }else if (lll>60 && lll<60*24){
            s1 = BigDecimal.valueOf(Math.floor(lll/60)).stripTrailingZeros().toPlainString();
            time = s1 +"小时前";
        }else if (lll>60*24 && lll<60*24*30){
            s1 = BigDecimal.valueOf(Math.floor(lll/(60*24))).stripTrailingZeros().toPlainString();
            time = s1 +"天前";
        }else if (lll<60*24*30*12 && lll>60*24*30){
            s1 = BigDecimal.valueOf(Math.floor(lll/(60*24*30))).stripTrailingZeros().toPlainString();
            time = s1 +"月前";
        }else {
            s1 = BigDecimal.valueOf(Math.floor(lll/(60*24*30*12))).stripTrailingZeros().toPlainString();
            time = s1 +"年前";
        }
        return time;
    }

    public static String getDateFormat(long time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(time);
    }

    public static String getZeroDateFormat(long time){
        time = time * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(time);
    }


    //获得当天0点时间
    public static int getTimesmorning(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis()/1000);
    }
    //获得当天24点时间
    public static int getTimesnight(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis()/1000);
    }
    //获得本周一0点时间
    public static int getTimesWeekmorning(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return (int) (cal.getTimeInMillis()/1000);
    }
    //获得本周日24点时间
    public static int getTimesWeeknight(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return (int) ((cal.getTime().getTime()+ (7 * 24 * 60 * 60 * 1000))/1000);
    }
    //获得本月第一天0点时间
    public static int getTimesMonthmorning(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return (int) (cal.getTimeInMillis()/1000);
    }
    //获得本月最后一天24点时间
    public static int getTimesMonthnight(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return (int) (cal.getTimeInMillis()/1000);
    }

    //设置月日
    public static long getMonthDay(Integer day,Integer month) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);// 设置为1号,当前日期既为本月第一天
        return calendar.getTimeInMillis()/1000;
    }

    //设置年月日
    public static long getYearMonthDay(Integer day,Integer month,Integer year) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);// 设置为1号,当前日期既为本月第一天
        return calendar.getTimeInMillis()/1000;
    }

    //获取七天前时间
    public static long getBeforeSevenDay() {
        Calendar c = Calendar.getInstance();
        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        return c.getTimeInMillis();
    }

    //获得当前月
    public static Integer getNowMonth() {
        Calendar cale =  Calendar.getInstance();
        int month = cale.get(Calendar.MONTH) + 1;
        return month;
    }

    //获取当前年
    public static Integer getNowYear() {
        Calendar cale =  Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        return year;
    }


}



