package cn.popo.news.core.entity.form;

import lombok.Data;

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
    private String aid;

    /**
     * 评论内容
     */
    private String commentInfo;
}
