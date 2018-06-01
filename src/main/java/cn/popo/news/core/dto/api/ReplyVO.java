package cn.popo.news.core.dto.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplyVO implements Serializable {
    private String id;
    private String byReplyId;
    private String replyInfo;
    private String byReplyInfo;
    private String nickName;
    private String byNickName;
    private String avatar;
    private String byAvatar;
    private String manyTimeAgo;
    private Integer praiseNum;
    private Integer replyPraiseId;
}
