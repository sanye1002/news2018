package cn.popo.news.core.controller.oa;



import cn.popo.news.core.config.FFMPEGConfig;
import cn.popo.news.core.config.NginxConfig;
import cn.popo.news.core.config.UploadConfig;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.UploadUtil;
import cn.popo.news.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-03-16 上午 11:06
 */
@RestController
@RequestMapping("/oa/upload")
@Slf4j
public class UploadController {

    @Autowired
    private UploadConfig uploadConfig;
    @Autowired
    private FFMPEGConfig ffmpegConfig;
    @Autowired
    public NginxConfig nginxConfig;
    @Autowired
    private UploadUtil uploadUtil;
    @PostMapping("/img/{type}")

    private ResultVO<Map<String, String>> uploadFile(HttpServletRequest request,
                                                     @PathVariable String type,
                                                     MultipartFile file) throws Exception {
        Map<String, String> map = new HashMap<>();

        String path = uploadConfig.getPath() + File.separator + type;

        log.info("path={}", uploadConfig.getPath() + File.separator + type);

        String src = uploadUtil.uploadFile(file, path, type);
        if (src != null) {
            map.put("src", src);
            return ResultVOUtil.success(map);
        } else {
            return ResultVOUtil.error(100, "上传失败");
        }
    }

    @PostMapping("/mp4/{userId}")
    private ResultVO<Map<String, Object>> uploadMp4(HttpServletRequest request,
                                                     @PathVariable String userId,
                                                     MultipartFile file) throws Exception {
        Map<String, Object> map = new HashMap<>();

        String path = uploadConfig.getPath() + File.separator + "mp4"+ File.separator + userId;

        log.info("path={}", uploadConfig.getPath() + File.separator + userId);
        log.info("【可执行文件路径】={}",ffmpegConfig.getExePath());
        map = UploadUtil.builder().build().uploadVideo(file,nginxConfig.getStatic_url(), path, userId);
        return ResultVOUtil.success(map);

    }
    @PostMapping("article/img/{type}")

    private ResultVO<Map<String, String>> uploadArticleImg(HttpServletRequest request,
                                                     @PathVariable String type,
                                                     MultipartFile file) throws Exception {
        Map<String, String> map = new HashMap<>();

        String path = uploadConfig.getPath() + File.separator + type;

        log.info("path={}", uploadConfig.getPath() + File.separator + type);

        String src = uploadUtil.uploadArticleImg(file, path, type);
        if (src != null) {
            map.put("src", src);
            return ResultVOUtil.success(map);
        } else {
            return ResultVOUtil.error(100, "上传失败");
        }
    }
}
