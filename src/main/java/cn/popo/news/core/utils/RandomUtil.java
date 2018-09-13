package cn.popo.news.core.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomUtil {
    public static Set<Integer> moreRandom(Integer min,Integer max,Integer size){
        Set<Integer> set = new HashSet<Integer>();
        while(set.size()<size){
            Integer index = new Random().nextInt((max-min))+min;
            set.add(index);
        }
        return set;
    }
}
