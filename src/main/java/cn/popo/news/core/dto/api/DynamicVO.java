package cn.popo.news.core.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
public class DynamicVO {
    @Id
    @GeneratedValue
    private Integer id;

    private String userId;

    private String username;

    private String avatar;

    private String content;

    @JsonProperty("imgs")
    private List<String> imgList;

    @JsonProperty("time")
    private String ManyTimeAgo;
}
