package cn.popo.news.common.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.IOException;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-19 下午 7:01
 * @Description description
 */
public class QiniuUpload {
    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static final String ACCESS_KEY = "dsv_HZXRS9HN9Q-yQLSFLjSMzoN5QL-WJOT1mzGz";
    private static final String SECRET_KEY = "2A4bgBLlI6i4liwKsAmNlUBeU1iaSflvTJsaO7SO";
    //要上传的空间
    private static final String BUCKET_NAME = "yhyc2018objectsave";

    public static void main(String args[]) throws IOException {
        new QiniuUpload().upload();
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken(BUCKET_NAME);
    }

    public void upload() throws IOException {
        //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);

        //上传到七牛后保存的文件名
        String key = "QQ图片20180327163221.jpg";
        //上传文件的路径
        String FilePath = "C:\\Users\\Administrator\\Pictures\\websiteAnchor\\QQ图片20180327163221.jpg";
        try {
            //调用put方法上传
            Response res = uploadManager.put(FilePath, key, getUpToken());
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            //响应的文本信息
            System.out.println(r.bodyString());
        }
    }
}
