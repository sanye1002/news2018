package cn.popo.news.core.entity.param;

import lombok.Data;

@Data
public class PersonalParam {
    private String userId;
    private String QQ;
    private String email;
    private String city;
    private String birthday;
    /**
     * 0:女 1:男
     */
    private Integer sex;
    /**
     * 个性签名
     */
    private String signature;
    /**
     * 用户名
     */
    private String nikeName;

    private String backgroundImg;
}
