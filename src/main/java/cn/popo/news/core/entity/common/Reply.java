package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/22 12:06
 * @Desc    回复
 */

@Data
@Entity
@Table(name="reply")
public class Reply {
    @Id
    private String id;

    /**
     * 评论的id
     */
    private String commId;

    /**
     * 被回复的id
     */
    private String byReplyId;

    /**
     * 被回复者的id
     */
    private String cid;

    /**
     * 回复者id
     */
    private String rid;

    /**
     * 回复内容
     */
    private String replyInfo;

    /**
     * 回复时间
     */
    private Long time;

    /**
     * 赞数量
     */
    private Integer praiseNum;

    /**
     * 展示否
     */
    private Integer showState;
}
