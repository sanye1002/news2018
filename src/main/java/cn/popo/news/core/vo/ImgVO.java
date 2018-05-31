package cn.popo.news.core.vo;

import lombok.Data;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-16 下午 6:39
 */
@Data
public class ImgVO<T> {

    private Integer id;
    private String title;
    private Integer start;
    private T Data;
}
