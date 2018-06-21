package cn.popo.news.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-03-29 上午 10:11
 */
@Component
@Data
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {
    private String path;
    private String waterMarkPath;
}
