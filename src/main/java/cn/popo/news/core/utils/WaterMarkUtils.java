package cn.popo.news.core.utils;

import cn.popo.news.core.config.UploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-05 上午 11:45
 * @Description description
 */
@Component
public class WaterMarkUtils {

   /* public static void main(String[] args) {
        try {
            new WaterMarkUtils().AddImgWaterMark("C:\\Users\\Administrator\\Desktop\\E8BA72E00FA028AACF7057795D279185.png", "C:\\Users\\Administrator\\Desktop\\Text2.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    // 加图片水印
    public static void mark(BufferedImage bufImg, Image img, Image markImg, int width, int height, int x, int y) {
        Graphics2D g = bufImg.createGraphics();
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);

        g.drawImage(markImg, x, y, width, height, null);
        g.dispose();
    }

    /**
     * 给图片增加图片水印
     *
     * @param inputImg
     *            -源图片，要添加水印的图片
     * @param
     *            - 水印图片 static/images/watermarklogo.png
     * @param outputImg
     *            -输出图片(可以是源图片)
     * @param
     *            - 水印图片宽度
     * @param
     *            -水印图片高度
     * @param
     *            -横坐标，相对于源图片
     * @param
     *            -纵坐标，同上
     */
    public static void AddImgWaterMark(String inputImg, String outputImg,String markImg ) throws IOException {
        // 读取原图片信息
        File inputImgFile = null;
        File markImgFile = null;
        Image img = null;
        Image mark = null;
        //D:\Temp\watermarklogo.png
       // String markImg = uploadConfig.getWaterMarkPath();
       // String markImg = "D:\Temp\water.png";
        try {
            if (inputImg != null && markImg != null) {
                inputImgFile = new File(inputImg);
                markImgFile = new File(markImg);
            }
            if (inputImgFile != null && inputImgFile.exists() && inputImgFile.isFile() && inputImgFile.canRead()) {

                img = ImageIO.read(inputImgFile);

            }
            if (markImgFile != null && markImgFile.exists() && markImgFile.isFile() && markImgFile.canRead()) {

                mark = ImageIO.read(markImgFile);

            }
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);
            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            int x = imgWidth-110;
            int y = imgHeight-110;
            int heights = imgHeight/20;
            int widths = heights*4;

            mark(bufImg, img, mark, widths, heights, 0, 0);
            FileOutputStream outImgStream = new FileOutputStream(outputImg);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
