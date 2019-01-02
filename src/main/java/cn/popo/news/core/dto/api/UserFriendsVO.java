package cn.popo.news.core.dto.api;

import lombok.Data;

@Data
public class UserFriendsVO {
    private Integer id;

    private String friendUserId;

    private String friendNickname;

    private String friendAvatar;

    //是否有该好友 true为有 false为无
    private Boolean friendFlag;
}
