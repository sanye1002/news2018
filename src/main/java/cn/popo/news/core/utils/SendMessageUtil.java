package cn.popo.news.core.utils;

import lombok.Synchronized;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-12 上午 10:54
 * @Description description
 */

public class SendMessageUtil {
    private static final String AppKey = "25043";
    private static final String Sign = "67722d3b24a93459e930c6df81540f02";

    /**
     * 尊敬的用户，您本次的验证码是{code}【成都益豪娱创】
     * http://api.k780.com/?app=sms.send&tempid=你创建的模板ID&;param=替换参数&phone=手机号码&appkey=APPKEY&sign=SIGN&format=json
     * @param phone
     * @param code
     */
    @Synchronized
    public static Boolean sendCodeMessage(String phone, String code)  {
        Integer tempid = 51402;
        Boolean flog = false;
        try {
            String param = "code="+URLEncoder.encode(code,"utf-8");
            flog = modelMessage(tempid,param,phone);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flog;
    }

    /**
     * 尊敬的{username}，你的{type}审核{result}，请查看。【成都益豪娱创】
     * @param phone
     * @param username
     * @param type
     * @param result
     * @return Boolean
     */
    @Synchronized
    public static Boolean sendSalaryTypeMessage(String phone, String username, String type, String result) {
        Integer tempid = 51403;

        String param = null;
        Boolean flog = false;
        try {
            param = "username="+ URLEncoder.encode(username+"&","utf-8")+"type="+
                    URLEncoder.encode(type+"&","utf-8")+"result="+URLEncoder.encode(result,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            flog = modelMessage(tempid,param,phone);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flog;
    }

    /**
     * 尊敬的{username}，你的{type}金额{salary}，已打款请查收。【成都益豪娱创】
     * @param phone
     * @param username
     * @param type
     * @param salary
     */
    @Synchronized
    public static Boolean sendSalaryMessage(String phone, String username, String type, String salary) {
        Integer tempid = 51404;

        String param = null;
        Boolean result = false;
        try {
            param = "username="+ URLEncoder.encode(username+"&","utf-8")+"type="+
                    URLEncoder.encode(type+"&","utf-8")+"salary="+URLEncoder.encode(salary,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            result =  modelMessage(tempid,param,phone);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param phone
     * @param username
     * @param content
     * @return
     */
    public static boolean sendNoticeMessage(String phone,String username,String content){
        Integer tempid = 51406;
        String param = null;
        Boolean result = false;
        try {
            param = URLEncoder.encode("username="+ username+"&","utf-8")+
                    URLEncoder.encode("content="+content,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("param="+param);
        try {
            result =  modelMessage(tempid,param,phone);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    private static Boolean modelMessage(Integer tempid,String param,String phone) throws IOException {
        String url = "https://api.nowapi.com/?app=sms.send&tempid="+tempid+"&param="+param+"&phone="+phone+"&appkey="+AppKey+"&sign="+Sign+"&format=json";
        URL u=new URL(url);
        InputStream in=u.openStream();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            byte buf[]=new byte[1024];
            int read = 0;
            while ((read = in.read(buf)) > 0) {
                out.write(buf, 0, read);
            }
        }  finally {
            if (in != null) {
                in.close();
            }
        }
        byte b[]=out.toByteArray( );
        String result = new String(b,"utf-8");
        System.out.println(result);
        if (result.substring(12,13).equals("1")){
            System.out.println("发送短信成功success="+result.substring(12,13));
            return true;
        }else {
            System.out.println("发送短信失败success="+result.substring(12,13));
            return false;
        }
    }

}
