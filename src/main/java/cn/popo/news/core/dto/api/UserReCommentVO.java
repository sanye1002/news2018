package cn.popo.news.core.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserReCommentVO {
    private String title;
    @JsonProperty("id")
    private String articleId;
    @JsonProperty("img")
    private List<String> imgList;
    private Integer imgNum;
}
