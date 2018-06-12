package cn.popo.news.core.entity.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author  Administrator
 * @Date    2018/5/26 13:23
 * @Desc    评论回复举报参数
 */

@Data
public class ReplyReportForm {
    /**
     * 举报者用户id
     */
    private String reportId;

    /**
     * 举报发送的内容
     */
    @NotEmpty
    private String content;

    /**
     * 举报类型
     */
    private Integer typeId;

    /**
     * 文章举报处理状态
     */
    private Integer disposeState;

    /**
     * 举报的回复Id
     */
    @NotEmpty
    private String replyId;
}
