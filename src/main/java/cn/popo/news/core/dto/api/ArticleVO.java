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

    private Integer classifyId;

    @JsonProperty("img")
    private List<String> imgList;

    @JsonProperty("nowTime")
    private Integer lookNum;

    private String manyTimeAgo;

    private Integer commentNum;

    @JsonProperty("type")
    private Integer typeId;

    private Integer original;

    private Author author;

    private Integer imgNum;

    private Integer collectId;

    private String video;

    private Integer isOwn;

    @JsonProperty("goodNum")
    private Integer praiseNum;

    private Integer goodFlag;

    private Integer attentionId;

    //用户名
    private String username;

    //用户头像
    private String avatar;


}
