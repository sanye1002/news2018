package cn.popo.news.core.entity.common;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author  Administrator
 * @Date    2018/5/22 11:40
 * @Desc    文章表（图文，多图，视频）
 */
@Data
@Entity
@Table(name = "article_info")
public class ArticleInfo implements Serializable{

    @Id
    private String articleId;
    /**
     * 标题
     */
    private String title;

    /**
     * 类型 1-图文 2-多图文 3-视频
     */
    private Integer typeId;

    /**
     * 文章类容
     */
    private String content;

    /**
     * 列别名称 头条   科技   娱乐  游戏   搞笑  文史   视频
     */
    private Integer classifyId;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 创建时间
     */
    private Long crateTime;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 审核状态：0未通过，1通过，2待审核
     */
    @Column(name = "audit_state")
    private Integer state;

    /**
     * 封面图路径
     */
    private String imgUrl;

    /**
     * 是否安排
     */
    private Integer manageId;

    /**
     * 是否上轮播图
     */
    private Integer slideState;

    /**
     * 是否上侧边栏
     */
    private Integer recommendState;

    /**
     * 展示否 0不展示，1展示
     */
    private Integer showState;

    /**
     * 描述
     */
    private String des;

    /**
     *  1：草稿，2：不是草稿
     */
    private Integer draft;

    /**
     * 是否原创 0否 1是
     */
    private Integer original;

}
