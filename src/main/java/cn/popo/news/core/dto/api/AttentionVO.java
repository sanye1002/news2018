package cn.popo.news.core.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttentionVO {
    private Integer id;
    private String aid;
    private String fid;
    @JsonProperty("byUsername")
    private String byNickName;
    private String byAvatar;
    private Integer attention;

    @JsonProperty("eachOtherAttention")
    private Integer attentionToo;

    private Integer unread;
}
