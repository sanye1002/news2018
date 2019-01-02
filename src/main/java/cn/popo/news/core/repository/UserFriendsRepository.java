package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.UserFriends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc sd
 */
public interface UserFriendsRepository extends JpaRepository<UserFriends,Integer> {
    UserFriends findByUserIdAndFriendUserId(String userId,String friendUserId);
    List<UserFriends> findAllByUserId(String userId);
}
