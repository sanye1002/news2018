package cn.popo.news.core.dto.api;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticleDetailsVO implements Serializable {

    private String articleId;
    private String time;
    private String title;
    private String content;
    private List<String> keywordList;
    private List<String> imgList;
    private List<String> imgDesc;
    private Integer collectId;
    private Integer typeId;
    private Integer original;

}
