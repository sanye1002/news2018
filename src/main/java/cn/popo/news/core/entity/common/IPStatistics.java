package cn.popo.news.core.entity.common;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/6/21 12:34
 * @Desc    ip用户访问记录
 */

@Data
@Entity
@Table(name="IP_statistics")
public class IPStatistics {
    @Id
    @GeneratedValue
    private Integer id;

    private String IP;

    private Long time;

    private String util;
}
