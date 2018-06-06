package cn.popo.news.core.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticleDetailsVO implements Serializable {

    @JsonProperty("id")
    private String articleId;
    private String time;
    private String title;
    private String content;
    @JsonProperty("keyword")
    private List<String> keywordList;
    @JsonProperty("img")
    private List<String> imgList;
    private List<String> imgDesc;
    @JsonProperty("collection")
    private Integer collectId;
    @JsonProperty("type")
    private Integer typeId;
    private Integer original;
    private Integer imgNum;



}
