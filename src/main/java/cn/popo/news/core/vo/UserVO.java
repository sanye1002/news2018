package cn.popo.news.core.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-31 下午 5:07
 * @Description description
 */
@Data
@Builder
public class UserVO {

    private String userId;

    private String userToken;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户名
     */
    private String nikeName;

    /**
     * 个人中心背景图
     */
    @JsonProperty("backgroundImg")
    private String backgrundImg;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 状态  1 0
     */
    private Integer status =0;

    /**
     * 角色Id
     */
    private Integer roleId;

    /**
     * 伪删除
     */
    private Integer showStatus;

    /**
     * 前台后台账号分离
     */
    private String userType;

    /**
     *姓名
     */
    private String name;

    /**
     * 我的粉丝数量
     */
    private Integer fansCounts;

    /**
     * 我关注的人总数
     */
    private Integer followCounts;

    private String createDate; //创建时间

    private String updateDate; //修改时间
}
