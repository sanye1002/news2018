package cn.popo.news.core.exception;

import cn.popo.news.core.enums.ResultEnum;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-01 上午 11:17
 * @Description description
 */
public class APIException extends RuntimeException {
    private Integer code;

    public APIException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    public APIException(Integer code, String message){
        super(message);
        this.code = code;
    }
}
