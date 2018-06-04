package cn.popo.news.core.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-03-19 下午 2:57
 */
@Data
public class ResultVO<T> implements Serializable{


    private static final long serialVersionUID = 8856971042517590532L;

    private Integer code;

    private String message;

    private T Data;
}
