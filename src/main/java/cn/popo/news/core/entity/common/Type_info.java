package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/24 10:47
 * @Desc    分类类型
 */

@Entity
@Data
@Table(name="type_info")
public class Type_info {
    @Id
    @GeneratedValue
    private Integer id;

    private String type_name;
}
