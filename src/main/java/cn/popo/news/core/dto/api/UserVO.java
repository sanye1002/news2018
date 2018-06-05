package cn.popo.news.core.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {
    private String userId;
    @JsonProperty("username")
    private String nikeName;
    private String avatar;
    private Integer vip;
    private Integer attentionId;
    List<UserReCommentVO> userReCommentList;
}
