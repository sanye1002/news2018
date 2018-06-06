package cn.popo.news.core.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentVO implements Serializable {
    private String id;

    private String userID;

    private String commentInfo;

    @JsonProperty("username")
    private String nickName;

    private String avatar;

    @JsonProperty("time")
    private String manyTimeAgo;

    @JsonProperty("good")
    private Integer praiseNum;

    private Integer replyNum;

    @JsonProperty("goodFlag")
    private Integer commentPraiseId;

}
