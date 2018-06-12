package cn.popo.news.core.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReplyVO implements Serializable {
    //回复id
    private String id;
    //回复的用户id
    @JsonProperty("userID")
    private String rid;
    //被回复的id
    private String byReplyId;
    //回复的信息
    private String replyInfo;
    //被回复的信息
    private String byReplyInfo;
    //回复的用户名
    @JsonProperty("username")
    private String nickName;
    //被回复的用户名
    @JsonProperty("byUsername")
    private String byNickName;
    //回复的头像
    private String avatar;

    @JsonProperty("time")
    private String manyTimeAgo;
    @JsonProperty("good")
    private Integer praiseNum;
    @JsonProperty("goodFlag")
    private Integer replyPraiseId;
}
