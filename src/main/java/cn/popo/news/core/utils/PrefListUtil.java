package cn.popo.news.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2018/11/14
 * @Desc 用户喜欢的关键字工具类
 */
public class PrefListUtil {

    public static String update(String prefList, List<Integer> ids) {
        return null;
    }

    public static String set(List<Integer> ids) {
        String pref = "{";
        if (!ids.isEmpty()) {
            for (int i = 0; i < ids.size(); i++) {
                pref = pref + "\"" + ids.get(i) + "\":{}";
                if (i != ids.size() - 1) {
                    pref = pref + ",";
                }else {
                    pref = pref + "}";
                }
            }
        }
        return pref;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(set(list));
    }

}
