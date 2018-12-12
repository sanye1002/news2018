package cn.popo.news.core.entity.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author  Administrator
 * @Date    2018/5/25 18:04
 * @Desc    评论参数
 */
@Data
public class CommentForm {
    /**
     * 评论人id
     */
    private String uid;

    /**
     * 评论文章id
     */
    @NotEmpty
    private String aid;

    /**
     * 评论内容
     */
    @NotEmpty
    private String commentInfo;

    private String nickName;

    private String avatar;
}
