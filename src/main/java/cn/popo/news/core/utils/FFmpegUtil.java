package cn.popo.news.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-25 上午 10:55
 * @Description 视频转换工具
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FFmpegUtil {


    public String ffmpegEXE;


   /* public static void main(String[] args) {
        FFmpegUtil fFmpegUtil = new FFmpegUtil("D:\\program files\\ffmpeg\\bin\\ffmpeg.exe");
        try {
            //fFmpegUtil.getCoverToMp4("D:\\Temp\\555.mp4","D:\\Temp\\生成的封面图.jpg");
            //fFmpegUtil.GetMp4AddMp3("D:\\Temp\\123.mp4","D:\\Temp\\bgm.mp3",10.1,"D:\\Temp\\合成以后的视频.mp4");
            fFmpegUtil.getMp4AddwaterMark("D:\\Temp\\110.mp4","D:\\Temp\\bgm.mp3",5.1,"D:\\Temp\\ll.png","D:\\Temp\\合成以后的视频.mp4");
            //fFmpegUtil.GetMp4AddwaterMark2("D:\\Temp\\123.mp4","D:\\Temp\\a.png","D:\\Temp\\合成以后的视频2.mp4");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void getMp4AddMp3(String videoInputPath, String mp3InputPath,
                          double seconds, String videoOutputPath) throws Exception {
//		ffmpeg.exe -i 苏州大裤衩.mp4 -i bgm.mp3 -t 7 -y 新的视频.mp4
//		ffmpeg.exe -i 苏州大裤衩.mp4 -i bgm.mp3 -t 7 -i logo.png -filter_complex overlay -y 新的视频.mp4
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);

        command.add("-i");
        command.add(videoInputPath);

        command.add("-i");
        command.add(mp3InputPath);

        command.add("-t");
        command.add(String.valueOf(seconds));


        command.add("-y");
        command.add(videoOutputPath);
        this.process(command);
        for (String c : command) {
            System.out.print(c + " ");
        }
    }

    public void getMp4AddMp3AndWaterMark(String videoInputPath, String mp3InputPath,
                          double seconds,String waterMark, String videoOutputPath) throws Exception {
//		ffmpeg.exe  -i src.avi -vf "movie=<LogoName>[logo];[in][logo]overlay=100:100[out]" -y 新的视频.mp4
//		ffmpeg -i Wildlife.wmv -vf "movie=panda.png[watermark];[in][watermark] overlay=10:10[out]" Marked.wmv
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);

        command.add("-i");
        command.add(videoInputPath);
        command.add("-i");
        command.add(waterMark);
        command.add("-filter_complex");
        command.add("overlay=main_w-overlay_w-10:main_h-overlay_h-10");
        //command.add("drawtext=text='雷':x=100:y=10:fontsize=24:fontcolor=yellow:shadowy=2");
        command.add("-i");
        command.add(mp3InputPath);
        command.add("-t");
        command.add(String.valueOf(seconds));
        command.add("-y");
        command.add(videoOutputPath);
        this.process(command);
        for (String c : command) {
            System.out.print(c + " ");
        }
    }

    /**
     * 给视频打水印
     * @param videoInputPath
     * @param waterMark
     * @param videoOutputPath
     * @return
     */
    public Map<String,Object> getMp4AddWaterMark(String videoInputPath,String waterMark, String videoOutputPath) {
//		ffmpeg.exe  -i src.avi -vf "movie=<LogoName>[logo];[in][logo]overlay=100:100[out]" -y 新的视频.mp4
//		ffmpeg -i Wildlife.wmv -vf "movie=panda.png[watermark];[in][watermark] overlay=10:10[out]" Marked.wmv
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);

        command.add("-i");
        command.add(videoInputPath);
        command.add("-i");
        command.add(waterMark);
        command.add("-filter_complex");
        command.add("overlay=main_w-overlay_w-10:main_h-overlay_h-10");
        //command.add("drawtext=text='雷':x=100:y=10:fontsize=24:fontcolor=yellow:shadowy=2");
        command.add("-y");
        command.add(videoOutputPath);
        return this.process(command);
    }
    public void getCoverToMp4(String videoInputPath,String coverOutputPath){
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);

        command.add("-ss");
        command.add("00:00:01");
        command.add("-y");
        command.add("-i");
        command.add(videoInputPath);
        command.add("-vframes");
        command.add("1");
        command.add(coverOutputPath);
        for (String c : command) {
            System.out.print(c + " ");
        }
        this.process(command);

    }
    public void GetMp4AddWaterMark2(String videoInputPath, String waterMark, String videoOutputPath) throws Exception {
//		ffmpeg -i test.mp4 -i logo.png -filter_complex overlay test1.mp4
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);

        command.add("-i");
        command.add(videoInputPath);

        command.add("-i");
        command.add(waterMark);

        command.add("-filter_complex");
        command.add("overlay");
        command.add("-y");
        command.add(videoOutputPath);
        this.process(command);
        for (String c : command) {
            System.out.print(c + " ");
        }
    }

    /**
     * 执行方法
     * @param command
     * @return
     */
    public Map<String,Object> process(List<String> command){
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        Map<String,Object> map = new HashMap<>();
        try {
            process = builder.start();
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = "";
            while ( (line = br.readLine()) != null ) {
            }

            if (br != null) {
                br.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (errorStream != null) {
                errorStream.close();
            }
            map.put("message","视频处理成功");
            map.put("code",0);
        } catch (IOException e) {
            map.put("message","视频处理失败，请重试");
            map.put("code",100);
            e.printStackTrace();
        }


        return map;
    }
}
