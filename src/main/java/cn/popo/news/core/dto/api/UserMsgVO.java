package cn.popo.news.core.dto.api;

import lombok.Data;

@Data
public class UserMsgVO {
    private Integer id;

    private String userId;

    private String toUserId;

    //对方用户的头像
    private String toUserAvatar;

    //对方用户的昵称
    private String toUserName;

    //最后一次发送消息的时间
    private String lastTime;

    //最后一条消息
    private String lastMsg;

    //未读数量
    private Integer unReadNum;
}
