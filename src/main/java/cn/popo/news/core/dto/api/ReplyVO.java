package cn.popo.news.core.dto.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplyVO implements Serializable {
    //回复id
    private String id;
    //回复的用户id
    private String rid;

    //被回复的id
    private String byReplyId;
    //回复的信息
    private String replyInfo;
    //被回复的信息
    private String byReplyInfo;
    //回复的用户名
    private String nickName;
    //被回复的用户名
    private String byNickName;
    //回复的头像
    private String avatar;

    private String manyTimeAgo;
    private Integer praiseNum;
    private Integer replyPraiseId;
}
