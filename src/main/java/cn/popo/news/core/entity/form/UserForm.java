package cn.popo.news.core.entity.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author  Administrator
 * @Date    2018/5/28 19:26
 * @Desc
 */
@Data
public class UserForm {

    private String userId;

    @NotEmpty(message = "姓名不能必填")
    private String name;

    @NotEmpty(message = "手机号码必填，以便登录")
    private String phone;

    @NotEmpty(message = "昵称不能必填")
    private String nikeName;

    @NotEmpty(message = "密码必须填！")
    private String password;

    private Integer status;//状态  1 0
    /**
     * 角色Id
     */
    private Integer roleId;
    /**
     * 显示
     */
    private Integer showStatus =1;
}
