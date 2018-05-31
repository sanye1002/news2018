package cn.popo.news.core.entity.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-15 下午 2:22
 */
@Data
public class PermissionForm {

    private Integer id;
    /**
     * 权限名称
     */
    @NotEmpty(message = "名称不能为空")
    private String name;
    /**
     * 方法
     */
    @NotEmpty(message = "方法不能为空")
    private String method;
    /**
     * 权限说明
     **/
    @NotEmpty(message = "描述不能为空")
    private String description;
}
