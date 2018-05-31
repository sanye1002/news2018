package cn.popo.news.core.dto;

import cn.popo.news.core.entity.common.Permission;
import lombok.Data;

import java.util.List;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 7:34
 */
@Data
public class RoleDTO {
    private Integer id;
    /**
     * 角色名
     **/
    private String name;
    /**
     * 角色说明
     **/
    private String description;

    private Integer level;
    /**
     * 权限
     */

    private List<Permission> permissionList;
}
