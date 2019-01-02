package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.entity.common.UserMsg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface UserMsgRepository extends JpaRepository<UserMsg,Integer> {
    List<UserMsg> findByStateAndUserId(Integer state,String userId);
    UserMsg findByUserIdAndToUserId(String userId,String toUserId);
}
