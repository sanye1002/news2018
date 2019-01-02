package cn.popo.news.core.service.api;

import cn.popo.news.core.dto.api.UserFriendsVO;
import cn.popo.news.core.entity.common.User;

import java.util.List;

public interface UserFriendsService {
    void saveFriend(String userId,String friendUserId,String friendNickname,String friendAvatar);
    void deleteFriend(Integer id);
    UserFriendsVO findUserFriendByPhone(String phone, String userId);
    UserFriendsVO findUserFriendByUserId(String searchUserId,String userId);
    List<UserFriendsVO> findUserFriendList(String userId);
    UserFriendsVO getUserFriendsVO(User user, String userId);
}
