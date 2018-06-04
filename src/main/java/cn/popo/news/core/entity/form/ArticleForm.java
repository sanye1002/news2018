package cn.popo.news.core.entity.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @Author  Administrator
 * @Date    2018/5/23 12:08
 * @Desc    文章内容
 */

@Data
public class ArticleForm {

    private String articleId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String keywords;

    private Integer classifyId;

    private Integer typeId;

    @NotEmpty
    private String content;

    @NotEmpty
    private String imgUrl;

    private String des;

    private Integer draft;

    private Integer original;
}
