package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/22 12:00
 * @Desc    收藏
 */

@Data
@Entity
@Table(name="collect")
public class Collect {
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 收藏者id
     */
    private String uid;

    /**
     * 收藏文章id
     */
    private String aid;
}
