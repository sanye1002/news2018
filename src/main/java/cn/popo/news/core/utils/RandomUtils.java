package cn.popo.news.core.utils;

import java.util.Random;

/**
 * Created by my
 * 2018-03-14 17:39
 */
public class RandomUtils {
    public static Integer getRandom(){
        int max = 1000;
        int min = 100;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1)+min;
    }
    public static Integer getRandom4Font(){
        return (int)(Math.random()*8998)+1000+1;
    }

}
