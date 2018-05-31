package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 八哥
 * @computer：Administrator
 * @create 2018-04-09 下午 3:34
 */
@Entity
@Data
@Table(name = "role_info")
public class Role {
    @Id
    @GeneratedValue
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





}
