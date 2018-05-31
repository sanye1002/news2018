package cn.popo.news.core.vo;

import lombok.Data;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-03-19 下午 2:57
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String message;


    private T Data;
}
