package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/22 12:11
 * @Desc    评论赞
 */

@Data
@Entity
@Table(name="comment_praise")
public class CommentPraise {
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 被赞评论id
     */
    private String commentId;

    /**
     * 赞用户id
     */
    private String uid;

}
