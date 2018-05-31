package cn.popo.news.core.entity.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author  Administrator
 * @Date    2018/5/23 12:08
 * @Desc    文章内容
 */

@Data
public class ArticleDraftForm {

    private String articleId;

    private String title;

    private String keywords;

    private Integer classifyId;

    private Integer typeId;

    private String content;

    private String imgUrl;

    private String des;

    private Integer draft;
}
