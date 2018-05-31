package cn.popo.news.core.dto;

import cn.popo.news.core.entity.common.ArticleInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/23 19:11
 * @Desc
 */

@Data
public class ArticleReportDTO {
    private String id;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 文章
     */
    private ArticleInfo article;

    /**
     * 举报类型
     */
    private String type;

    /**
     * 举报内容
     */
    private String info;

    /**
     * 举报时间
     */
    private String time;

    /**
     * 文章举报处理状态
     */
    private Integer disposeState;
}
