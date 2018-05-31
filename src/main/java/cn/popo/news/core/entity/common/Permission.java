package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 3:43
 */
@Entity
@Data
@Table(name = "permission_info")
public class Permission {
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 方法
     */
    private String method;
    /**
     * 权限说明
     **/
    private String description;

    public Permission() {

    }

    public Permission(String name, String method, String description) {
        this.name = name;
        this.method = method;
        this.description = description;
    }
}
