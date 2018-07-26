package cn.popo.news.core.utils;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-24 下午 3:30
 * @Description description
 */
public class IdGenerateUtil {
    public static String getId(Integer userSize,String newestId){

        if (userSize<100){

            int a = 100000;
            int b = 101000;
            int c = 102000;
            int d = (int)(Math.random()*1000);
            System.out.println(d);
            String g = (Integer.parseInt(newestId)+d)+"";
            /*String e = g.substring(1,6);
            String id = 1+e;*/
            return g;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getId(1,"1001926"));
    }
}
