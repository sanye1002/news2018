package cn.popo.news.core.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticleVO implements Serializable {
    @JsonProperty("id")
    private String articleId;


    private String title;

    @JsonProperty("sort")
    private String classify;

    @JsonProperty("img")
    private List<String> imgList;

    @JsonProperty("nowTime")
    private String manyTimeAgo;

    private Integer commentNum;

    @JsonProperty("type")
    private Integer typeId;

    private Integer original;

    private Author author;

    private Integer imgNum;

    private Integer collectId;

}
