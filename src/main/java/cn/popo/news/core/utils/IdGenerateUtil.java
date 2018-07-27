package cn.popo.news.core.utils;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-24 下午 3:30
 * @Description description
 */
public class IdGenerateUtil {
    public static String getId(Integer userSize,String newestId){
            int d = (int)(Math.random()*1000);
            String id = (Integer.parseInt(newestId)+d)+"";
            return id;
    }
}
