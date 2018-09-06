package cn.popo.news.core.utils;

import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2018/09/06
 * @Desc seo工具类
 */
public class SEOUtil {
    /**
     * 头部keywords处理方法
     * @param stringList
     * @return
     */
    public static String getKeywordsByList(List<String> stringList){
        String s = "";
        if (stringList != null){
            if (stringList.isEmpty()){
                return s;
            }
            for (int i = 0;i < stringList.size();i++){
                if (i == stringList.size()-1){
                    s = s + stringList.get(i);
                } else  {
                    s = s + stringList.get(i)+",";
                }
            }
        }
       return s;
    }

    /**
     * 头部description处理方法
     * @param content
     * @return
     */
    public static String getDescription(String content){
        if (content == null){
            return "";
        }
        String newContent = HTMLSpirit.delHTMLTag(content);
        System.out.println(newContent.length());
        return newContent.length() > 100 ? newContent.substring(0,97)+"..." : newContent;
    }
}
