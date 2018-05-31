package cn.popo.news.core.utils;


import cn.popo.news.core.config.FFMPEGConfig;
import cn.popo.news.core.config.UploadConfig;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

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
public class UploadUtil {

    @Autowired
    public FFMPEGConfig ffmpegConfig;
    @Autowired
    public UploadConfig uploadConfig;
    public static String uploadFile(MultipartFile file, String path, String type) {
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
            log.info("fileName={}", fileName);
            //压缩比
            Thumbnails.of(saveFile.getAbsolutePath()).scale(0.75f).toFile(saveFile.getAbsolutePath());
            return "/read/img/" + type + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    public Map<String,Object> uploadVideo(MultipartFile file, String exePath,String path, String userId) {
        File dir = new File(path);
        Map<String,Object> map = new HashMap<>();
        //判断目录是否存在
        log.info("【视频上传】 path={}", path);
        log.info("【视频上传】 dir.exists()={}", dir.exists());
        //判断目录是否存在
        if (!dir.exists()) {
            // 如果不存在，自动创建
            dir.mkdirs();
        }
        log.info("【视频类型】={}",file.getContentType());
        if (!file.getContentType().equals("video/mp4")){
            map.put("code",100);
            map.put("message","请选择MP4格式视频");
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
            map.put("coverPath",coverOutputPath);*/
           map.put("code",0);
           map.put("message","视频上传成功！");
            map.put("videoPath","/read/mp4/" + userId + "/" + fileName);

            return map;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
