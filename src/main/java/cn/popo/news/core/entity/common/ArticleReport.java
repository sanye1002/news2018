package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/25 14:49
 * @Desc    文章举报
 */

@Data
@Entity
@Table(name = "article_report")
public class ArticleReport {
    @Id
    private String id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 文章id
     */
    private String aid;

    /**
     * 举报类型id
     */
    private Integer typeId;

    /**
     * 举报内容
     */
    private String info;

    /**
     * 举报时间
     */
    private Long time;

    /**
     * 文章举报处理状态
     */
    private Integer disposeState;
}
