package cn.popo.news.core.enums;

import lombok.Getter;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-03-19 下午 2:51
 */
@Getter
public enum ResultEnum {
    SUCCESS(0," 处理成功"),
    PARAM_NULL(1,"参数为空"),
    PLATFORM_BOOS_NULL(2,"平台管理员为空"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
