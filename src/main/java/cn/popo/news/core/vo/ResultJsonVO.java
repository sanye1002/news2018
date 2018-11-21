package cn.popo.news.core.vo;

import lombok.Data;

/**
 * @Author zhaoxiang
 * @Date 2018/11/15
 * @Desc
 */
@Data
public class ResultJsonVO {
    private Integer code;
    private Object data;
    private String msg;
    private Long timestamps;
}
