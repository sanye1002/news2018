package cn.popo.news.core.exception;

import cn.popo.news.core.enums.ResultEnum;
import lombok.Getter;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-03-19 下午 2:48
 * 自定义异常
 */
@Getter
public class WebException extends RuntimeException{


    private Integer code;

    public WebException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    public WebException(Integer code, String message){
        super(message);
        this.code = code;
    }
}
