package cn.popo.news.common.utils;

public class GetMaxUtil {
   /* public static void main(String[] args) {
        int s=210;
        int g = s%10;
        int sw = s/10%10;
        int b = s/100%10;
        int q = s/1000%10;
        int o = s/10000%10;
        if (q==0){
            if(b==0){
                s=s;
            }else{
                s = 100*(b+1);
            }
        }else {
            s = 1000*(q+1);
        }
        System.out.println(s);
        System.out.println("个位数是:"+g+";十位数是:"+sw+";百位数是："+b+";千位数是："+q+"-----"+o);
    }*/

    public static Integer maxValue(Integer s){
        Integer max = 10;
        int sw = s/10%10;
        Integer b = s/100%10;
        Integer q = s/1000%10;
        if (q==0){
            if(b==0){
                if (sw==0) {
                    return max;
                }else {
                    max = 10*(sw+1);
                }
            }else{
                max = 100*(b+1);
            }
        }else {
            max = 1000*(q+1);
        }
        return max;
    }
}
