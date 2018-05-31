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
            time = lll+"分钟";
        }else{
            s1 = BigDecimal.valueOf(Math.floor(lll/60)).stripTrailingZeros().toPlainString();
            time = s1 +"小时";
        }
        return time;
    }
    public static String getDateFormat(long time){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(time);
    }
}
