package cn.popo.news.core.dto;

import cn.popo.news.core.entity.common.Comment;
import lombok.Data;

/**
 * @Author  Administrator
 * @Date    2018/5/26 13:49
 * @Desc
 */

@Data
public class CommentReportDTO {
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
    private String type;

    /**
     * 评论回复举报处理状态
     */
    private Integer disposeState;

    /**
     * 举报的时间
     */
    private String   time;

    /**
     * 被举报的回复
     */
    private Comment comment;
}
