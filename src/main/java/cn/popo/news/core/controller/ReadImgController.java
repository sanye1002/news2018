package cn.popo.news.core.controller;


import cn.popo.news.core.config.UploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by 超级战机
 * 2018-03-28 21:34
 */

@Controller
@RequestMapping("/read")
public class ReadImgController {

    @Autowired
    private UploadConfig uploadConfig;
    @GetMapping("/img/{type}/{name:.+}")
    public void readImg(@PathVariable(value = "name")String name,
                        @PathVariable(value = "type")String type,
                        HttpServletResponse response) throws IOException {
        String path = uploadConfig.getPath()+File.separator+type+File.separator+name;
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        OutputStream out = response.getOutputStream();
        byte[] b = new byte[10240];
        int length = 0;
        while((length=inputStream.read(b))!=-1){
            out.write(b,0,length);
        }
        out.close();
        inputStream.close();
    }
    @GetMapping("/mp4/{userId}/{name:.+}")
    public void readMp4(@PathVariable(value = "name")String name,
                        @PathVariable(value = "userId")String userId,
                        HttpServletResponse response) throws IOException {
        String path = uploadConfig.getPath()+File.separator+"mp4"+File.separator+userId+File.separator+name;
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        OutputStream out = response.getOutputStream();
        byte[] b = new byte[102400];
        int length = 0;
        while((length=inputStream.read(b))!=-1){
            out.write(b,0,length);
        }
        out.close();
        inputStream.close();
    }

}
