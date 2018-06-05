package cn.popo.news.core.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-31 下午 5:43
 * @Description description
 */
@Data
@Builder
@ApiModel(value = "用户参数对象",description = "用户参数对象")
public class UserParam {

    @ApiModelProperty(hidden=true)
    private String userId;

    /**
     * 手机号
     */
    @ApiModelProperty(value="用户名", name="phone", example="18145032533", required=true)
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

    private String createDate; //创建时间

    private String updateDate; //修改时间


    /**
     * 我的粉丝数量
     */
    private Integer fansCounts;

    /**
     * 我关注的人总数
     */
    private Integer followCounts;

    private String QQ;
    private String email;
    private String city;
    private String birthday;
    /**
     * 0:女 1:男
     */
    private Integer sex;
}
