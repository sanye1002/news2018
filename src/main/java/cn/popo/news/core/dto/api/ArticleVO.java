package cn.popo.news.core.dto.api;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticleVO implements Serializable {
    private String articleId;
    private String uid;
    private String nikeName;
    private String avatar;
    private String type;
    private String classify;
    private List<String> imgList;
    private String manyTimeAgo;
    private Integer commentNum;
    private Integer manageId;
    private Integer typeId;
}
