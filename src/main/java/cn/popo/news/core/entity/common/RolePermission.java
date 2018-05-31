package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 3:52
 */
@Entity
@Data
@Table(name = "role_permission")
public class RolePermission {
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 权限id
     */
    private Integer permissionId;
}
