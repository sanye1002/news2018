package cn.popo.news.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-25 上午 11:00
 * @Description description
 */
@Component
@Data
@ConfigurationProperties(prefix = "ffmpeg")
public class FFMPEGConfig {

    public String  exePath;

    public String  waterPath;
}
