package cn.popo.news.core.dto.api;

import lombok.Data;

@Data
public class AttentionVO {
    private Integer id;
    private String aid;
    private String fid;
    private String byNickName;
    private String byAvatar;
    private Integer attentionToo;
}
