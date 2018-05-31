package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/26 13:19
 * @Desc    回复举报
 */

@Data
@Entity
@Table(name = "comment_report")
public class CommentReport {
    @Id
    private String id;

    /**
     * 举报者用户id
     */
    private String reportId;

    /**
     * 举报发送的内容
     */
    private String content;

    /**
     * 举报类型
     */
    private Integer typeId;

    /**
     * 被举报的回复Id
     */
    private String commentId;

    /**
     * 评论回复举报处理状态
     */
    private Integer disposeState;

    /**
     * 举报的时间
     */
    private Long time;
}
