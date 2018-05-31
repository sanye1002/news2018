package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/22 11:39
 * @Desc    侧边栏，和轮播图
 */
@Data
@Entity
@Table(name="special_article")
public class SpecialArticles {
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 文章Id
     */
    private Integer special_id;

    /**
     * 1-轮播图(头条)，2-搞笑段子，精彩视频等（侧边栏）
     */
    private String modelType;
}
