package cn.popo.news.core.dto;

import cn.popo.news.core.entity.common.User;
import lombok.Data;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/23 19:11
 * @Desc
 */

@Data
public class ArticleDTO {

    private String articleId;
    /**
     * 标题
     */
    private String title;

    /**
     * 类型 1-图文 2-多图文 3-视频
     */
    private String type;

    private Integer typeId;

    /**
     * 文章类容
     */
    private String content;

    /**
     * 列别名称 头条   科技   娱乐  游戏   搞笑  文史   视频
     */
    private String classify;

    private Integer classifyId;

    /**
     * 用户id
     */
    private User user;

    /**
     * 创建时间
     */
    private String Time;

    /**
     * 关键词
     */
    private List<String> keywords;

    /**
     * 关键词总
     */
    private String keyword;

    /**
     * 审核状态：0未通过，1通过，2待审核
     */
    private Integer state;

    /**
     * 封面图路径
     */
    private List<String> imgList;

    /**
     *管理是否上板块
     */

    private Integer manageId;

    /**
     * 是否展示
     */
    private Integer showState;

    /**
     * 多图描述
     */
    private List<String> manyImgDesc;

    /**
     * 封面图路径
     */
    private String imgUrl;

    /**
     *  1：草稿，2：不是草稿
     */
    private Integer draft;

    /**
     * 多少时间之前
     */

    private String manyTimeAgo;

    /**
     * 评论条数
     */
    private Integer commentNum;

    private Integer original;

    /**
     * 是否上轮播图
     */
    private Integer slideState;

    /**
     * 是否上侧边栏
     */
    private Integer recommendState;

    private String des;

    private String auditTime;

    private Integer topState;

    private Integer topSort;


}
