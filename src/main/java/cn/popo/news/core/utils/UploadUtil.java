package cn.popo.news.core.utils;


import cn.popo.news.common.utils.QiniuUpload;
import cn.popo.news.core.config.FFMPEGConfig;
import cn.popo.news.core.config.UploadConfig;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-03-16 下午 3:39
 * @description: 单文件与多文件的上传
 */
@Slf4j
@Builder
@Component
public class UploadUtil {

    @Autowired
    public FFMPEGConfig ffmpegConfig;
    @Autowired
    public UploadConfig uploadConfig;

    public String uploadFile(MultipartFile file, String path, String type) {
        File dir = new File(path);
        //判断目录是否存在
        log.info("【文件上传】 path={}", path);
        log.info("【文件上传】 dir.exists()={}", dir.exists());
        //判断目录是否存在
        if (!dir.exists()) {
            // 如果不存在，自动创建
            dir.mkdirs();
        }
        //上传文件名
        //String fileName = KeyUtil.genUniqueKey() + file.getOriginalFilename();
        String fileName = KeyUtil.genUniqueKey() + ".jpg";
        //保存文件
        File saveFile = new File(path + File.separator + fileName);
        try {
            file.transferTo(saveFile);
            //log.info("file-size={}", );
            //压缩比
            /*Thumbnails.of(saveFile.getAbsolutePath()).size(800, 600).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("C:\\Users\\Administrator\\Pictures\\logo\\logo3.png")), 0.5f)
                    .outputQuality(0.8f).toFile("C:\\Users\\Administrator\\Pictures\\image_watermark_bottom_right.jpg");
            Thumbnails.of(saveFile.getAbsolutePath()).size(800, 600).watermark(Positions.CENTER, ImageIO.read(new File("C:\\Users\\Administrator\\Pictures\\logo\\logo3.png")), 0.5f)
                    .outputQuality(0.8f).toFile("C:\\Users\\Administrator\\Pictures\\image_watermark_bottom_CENTER.jpg");*/
            //压缩
            /*if (saveFile.length()>2048000){
                Thumbnails.of(saveFile.getAbsolutePath()).scale(1f).outputQuality(0.5f).toFile(saveFile.getAbsolutePath());
            }*/
            //打水印
//            if (!type.equals("user")||!type.equals("userDynamic")){
//                WaterMarkUtils.AddImgWaterMark(saveFile.getAbsolutePath(),saveFile.getAbsolutePath(),uploadConfig.getWaterMarkPath());
//            }
            try {
                new QiniuUpload().uploadFile(saveFile, "po/read/img/" + type + "/" + fileName);
            } catch (IOException e) {
                log.info("【上传】={}", "error");
            }
            if (type.equals("user")) {
                return "https://p0.cdrysj.com/po/read/img/" + type + "/" + fileName;
            }
            return "/read/img/" + type + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Map<String, Object> uploadVideo(MultipartFile file, String exePath, String path, String userId) {
        File dir = new File(path);
        Map<String, Object> map = new HashMap<>();
        //判断目录是否存在
        log.info("【视频上传】 path={}", path);
        log.info("【视频上传】 dir.exists()={}", dir.exists());
        //判断目录是否存在
        if (!dir.exists()) {
            // 如果不存在，自动创建
            dir.mkdirs();
        }
        log.info("【视频类型】={}", file.getContentType());
        if (!file.getContentType().equals("video/mp4")) {
            map.put("code", 100);
            map.put("message", "请选择MP4格式视频");
            return map;
        }
        //上传视频名
        //String fileName = KeyUtil.genUniqueKey() + file.getOriginalFilename();
        String fileName = KeyUtil.genUniqueKey() + ".MP4";
        //保存视频
        File saveFile = new File(path + File.separator + fileName);

        try {
            file.transferTo(saveFile);
            log.info("fileName={}", fileName);
           /* //图片水印
           FFmpegUtil fFmpegUtil = new FFmpegUtil(exePath);
           map = fFmpegUtil.getMp4AddWaterMark(saveFile.getAbsolutePath(),ffmpegConfig.getWaterPath(),saveFile.getAbsolutePath());
           //封面图
           String coverOutputPath = uploadConfig.getPath() + File.separator + type+ KeyUtil.genUniqueKey() + ".jpg";
           map.put("coverPath",coverOutputPath);
           */
            map.put("code", 0);
            map.put("message", "视频上传成功！");
            map.put("videoPath", "/read/mp4/" + userId + "/" + fileName);
            try {
                new QiniuUpload().uploadFile(saveFile, "po/read/mp4/" + userId + "/" + fileName);
            } catch (IOException e) {
                log.info("【视频上传】={}", "error");
                e.printStackTrace();
            }
            return map;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Map<String, Object> uploadAudio(MultipartFile file, String exePath, String path, String userId) {
        File dir = new File(path);
        Map<String, Object> map = new HashMap<>();
        //判断目录是否存在
        log.info("【音频上传】 path={}", path);
        log.info("【音频上传】 dir.exists()={}", dir.exists());
        //判断目录是否存在
        if (!dir.exists()) {
            // 如果不存在，自动创建
            dir.mkdirs();
        }
        log.info("【音频类型】={}", file.getContentType());
        if (!file.getContentType().equals("audio/amr")) {
            map.put("code", 100);
            map.put("message", "请选择MP3格式音频");
            return map;
        }
        //上传视频名
        //String fileName = KeyUtil.genUniqueKey() + file.getOriginalFilename();
        String fileName = KeyUtil.genUniqueKey() + ".amr";
        //保存视频
        File saveFile = new File(path + File.separator + fileName);

        try {
            file.transferTo(saveFile);
            log.info("fileName={}", fileName);
            map.put("code", 0);
            map.put("message", "音频上传成功！");
            map.put("videoPath", "/read/amr/" + userId + "/" + fileName);
            try {
                new QiniuUpload().uploadFile(saveFile, "po/read/amr/" + userId + "/" + fileName);
            } catch (IOException e) {
                log.info("【音频上传】={}", "error");
                e.printStackTrace();
            }
            return map;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Map<String, Object> uploadImgs(MultipartFile file, String path, String type) {
        File dir = new File(path);
        File dis = new File(path + File.pathSeparator + "small");
        //判断目录是否存在
        if (!dir.exists()) {
            // 如果不存在，自动创建
            dir.mkdirs();
        }
        if (!dis.exists()) {
            // 如果不存在，自动创建
            dis.mkdirs();
        }
        Map<String, Object> map = new HashMap<>();
        //上传文件名
        //String fileName = KeyUtil.genUniqueKey() + file.getOriginalFilename();
        String fileName = KeyUtil.genUniqueKey() + ".jpg";
        //保存文件
        File saveFile = new File(path + File.separator + fileName);
        try {
            file.transferTo(saveFile);
            log.info("fileName={}", fileName);
            //压缩比
            Thumbnails.of(saveFile.getAbsolutePath()).scale(0.75f).toFile(path + File.pathSeparator + "small" + File.separator + fileName);
            map.put("bigUrl", "/read/img/" + type + "/" + fileName);
            map.put("smallUrl", "/read/img/small/" + type + "/" + fileName);
            return map;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String uploadArticleImg(MultipartFile file, String path, String type) {
        File dir = new File(path);
        //判断目录是否存在
        log.info("【文件上传】 path={}", path);
        log.info("【文件上传】 dir.exists()={}", dir.exists());
        //判断目录是否存在
        if (!dir.exists()) {
            // 如果不存在，自动创建
            dir.mkdirs();
        }
        //上传文件名
        //String fileName = KeyUtil.genUniqueKey() + file.getOriginalFilename();
        String fileName = KeyUtil.genUniqueKey() + ".jpg";
        //保存文件
        File saveFile = new File(path + File.separator + fileName);
        try {
            file.transferTo(saveFile);

            /*if (!type.equals("user")) {
                WaterMarkUtils.AddImgWaterMark(saveFile.getAbsolutePath(), saveFile.getAbsolutePath(), uploadConfig.getWaterMarkPath());
            }*/

            try {
                new QiniuUpload().uploadFile(saveFile, "po/read/img/" + type + "/" + fileName);
            } catch (IOException e) {
                log.info("【上传】={}", "error");
            }
            return "https://p0.cdrysj.com/po/read/img/" + type + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
