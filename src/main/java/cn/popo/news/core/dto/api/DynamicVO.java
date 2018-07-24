package cn.popo.news.core.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
public class DynamicVO {

    private String id;

    private String userId;

    private String username;

    private String avatar;

    private String content;

    @JsonProperty("imgs")
    private List<String> imgList;

    private List<String> videoList;

    private Integer good;

    private Integer goodFlag;

    @JsonProperty("time")
    private String ManyTimeAgo;
}
