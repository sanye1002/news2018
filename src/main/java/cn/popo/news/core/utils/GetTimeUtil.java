package cn.popo.news.core.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
}
