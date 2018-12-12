package cn.popo.news.core.entity.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author  Administrator
 * @Date    2018/5/22 12:02
 * @Desc    评论
 */

@Data
@Entity
@Table(name="comment")
public class Comment {
    @Id
    private String id;

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

    /**
     * 评论时间
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

    private String nickName;

    private String avatar;


}
