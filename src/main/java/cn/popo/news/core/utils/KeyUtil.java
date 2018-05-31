package cn.popo.news.core.utils;

import java.util.Random;

/**
 * Created by Administrator
 *
 * @author sanye
 * @Description: 唯一的主键
 * @date 2017/9/22
 */
public class KeyUtil {

    public  static synchronized String genUniqueKey(){

        Random random = new Random();
        Integer number = random.nextInt(900000)+10000;
        return String.valueOf(System.currentTimeMillis()+number);


    }
    public static String[] splitString(String sourceStr){
         return sourceStr.split(",");
    }



}
