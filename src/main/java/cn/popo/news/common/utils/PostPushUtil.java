package cn.popo.news.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-06 下午 3:02
 * @Description description
 */
public class PostPushUtil {
    /**
     * @param //args
     */
    /*public static void main(String[] args) {
        String url = "http://data.zz.baidu.com/urls?site=www.immnc.com&token=zwI3w0PBhjCJwmYs";//网站的服务器连接
        String[] param = {
                "http://www.immnc.com/sortPage?navId=2",
                "http://www.immnc.com/home",
                "http://www.immnc.com/imgText?articleId=1530694024462",
                "http://www.immnc.com/imgs?articleId=1530265148509"//需要推送的网址
        };
        String json = Post(url, param);//执行推送方法
        System.out.println("结果是"+json);  //打印推送结果

    }*/

    public static void push(Integer type,String articleId){
        String url = "http://data.zz.baidu.com/urls?site=www.immnc.com&token=zwI3w0PBhjCJwmYs";//网站的服务器连接
        String[] param = new String[5];
        param[0] = "http://www.immnc.com/home";
        if (type == 1){
            param[1] = "http://www.immnc.com/imgText?articleId="+articleId;
        }
        if (type == 2){
            param[1] = "http://www.immnc.com/imgs?articleId="+articleId;
        }
        if (type == 3){
            param[1] = "http://www.immnc.com/video?articleId="+articleId;

        }
        String json = Post(url, param);//执行推送方法
        System.out.println("结果是"+json);  //打印推送结果
    }

    /**
     * 百度链接实时推送
     * @param PostUrl
     * @param Parameters
     * @return
     */
    public static String Post(String PostUrl,String[] Parameters){
        if(null == PostUrl || null == Parameters || Parameters.length ==0){
            return null;
        }
        String result="";
        PrintWriter out=null;
        BufferedReader in=null;
        try {
            //建立URL之间的连接
            URLConnection conn=new URL(PostUrl).openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("Host","data.zz.baidu.com");
            conn.setRequestProperty("User-Agent", "curl/7.12.1");
            conn.setRequestProperty("Content-Length", "83");
            conn.setRequestProperty("Content-Type", "text/plain");

            //发送POST请求必须设置如下两行
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //获取conn对应的输出流
            out=new PrintWriter(conn.getOutputStream());
            //发送请求参数
            String param = "";
            for(String s : Parameters){
                param += s+"\n";
            }
            out.print(param.trim());
            //进行输出流的缓冲
            out.flush();
            //通过BufferedReader输入流来读取Url的响应
            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line=in.readLine())!= null){
                result += line;
            }

        } catch (Exception e) {
            System.out.println("发送post请求出现异常！"+e);
            e.printStackTrace();
        } finally{
            try{
                if(out != null){
                    out.close();
                }
                if(in!= null){
                    in.close();
                }

            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
