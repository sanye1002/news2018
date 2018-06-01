package cn.popo.news.core.entity.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author  Administrator
 * @Date    2018/5/25 18:06
 * @Desc    回复参数
 */

@Data
public class ReplyForm {
    /**
     * 评论的id
     */
    @NotEmpty
    private String commId;

    /**
     * 被回复者的id
     */
    @NotEmpty
    private String cid;

    /**
     * 回复者id
     */
    @NotEmpty
    private String rid;

    /**
     * 回复内容
     */
    @NotEmpty
    private String replyInfo;

    /**
     * 被回复的id
     */
    @NotEmpty
    private String byReplyId;
}
