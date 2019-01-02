package cn.popo.news.core.service.api.impl;

import cn.popo.news.core.dto.api.UserFriendsVO;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.common.UserFriends;
import cn.popo.news.core.repository.UserFriendsRepository;
import cn.popo.news.core.service.UserService;
import cn.popo.news.core.service.api.UserFriendsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserFriendsServiceImpl implements UserFriendsService {
    private final UserFriendsRepository userFriendsRepository;
    private final UserService userService;

    @Autowired
    public UserFriendsServiceImpl(UserFriendsRepository userFriendsRepository, UserService userService) {
        this.userFriendsRepository = userFriendsRepository;
        this.userService = userService;
    }

    /**
     * 用户好友存储
     *
     * @param userId         用户id
     * @param friendUserId   好友id
     * @param friendNickname 好友昵称
     * @param friendAvatar   好友头像
     */
    @Override
    public void saveFriend(String userId, String friendUserId, String friendNickname, String friendAvatar) {
        UserFriends userFriends = new UserFriends();
        userFriends.setFriendAvatar(friendAvatar);
        userFriends.setFriendNickname(friendNickname);
        userFriends.setFriendUserId(friendUserId);
        userFriends.setUserId(userId);
        userFriendsRepository.save(userFriends);
    }

    /**
     * 好友删除
     *
     * @param id id
     */
    @Override
    public void deleteFriend(Integer id) {
        UserFriends userFriend = userFriendsRepository.findOne(id);
        //对方好友
        UserFriends byUserIdAndFriendUserId = userFriendsRepository.findByUserIdAndFriendUserId(
                userFriend.getFriendUserId(), userFriend.getUserId());
        userFriendsRepository.delete(byUserIdAndFriendUserId);
        userFriendsRepository.delete(id);
    }

    /**
     * 通过phone查找
     *
     * @param phone 手机号
     * @return 用户信息
     */
    @Override
    public UserFriendsVO findUserFriendByPhone(String phone, String userId) {
        User user = userService.findByPhoneAndStatus(phone);
        if (user == null) {
            return null;
        } else {
            return getUserFriendsVO(user, userId);
        }
    }

    /**
     * 通过用户id查找
     *
     * @param searchUserId 搜索的用户id
     * @param userId       该用户id
     * @return 用户信息
     */
    @Override
    public UserFriendsVO findUserFriendByUserId(String searchUserId, String userId) {
        User user = userService.findByUserIdAndState(searchUserId);
        if (user == null) {
            return null;
        } else {
            return getUserFriendsVO(user, userId);
        }
    }

    /**
     * 将user的数据封装给UserFriendsVO
     *
     * @param user   user
     * @param userId id
     * @return UserFriendsVO
     */
    @Override
    public UserFriendsVO getUserFriendsVO(User user, String userId) {
        UserFriendsVO userFriendsVO = new UserFriendsVO();
        userFriendsVO.setFriendAvatar(user.getAvatar());
        userFriendsVO.setFriendNickname(user.getNikeName());
        userFriendsVO.setFriendUserId(user.getUserId());

        UserFriends userFriends = userFriendsRepository
                .findByUserIdAndFriendUserId(userId, user.getUserId());
        if (userFriends == null) {
            userFriendsVO.setFriendFlag(false);
        } else {
            userFriendsVO.setFriendFlag(true);
        }
        return userFriendsVO;
    }

    /**
     * 查询好友列表
     *
     * @param userId 用户id
     * @return 列表list
     */
    @Override
    public List<UserFriendsVO> findUserFriendList(String userId) {
        List<UserFriends> allByUserId = userFriendsRepository.findAllByUserId(userId);
        List<UserFriendsVO> list = new ArrayList<>();
        if (!allByUserId.isEmpty()) {
            allByUserId.forEach(l -> {
                UserFriendsVO userFriendsVO = new UserFriendsVO();
                BeanUtils.copyProperties(l, userFriendsVO);
                list.add(userFriendsVO);
            });
        }
        return list;
    }
}
