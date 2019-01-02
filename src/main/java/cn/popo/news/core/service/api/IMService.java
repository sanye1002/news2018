package cn.popo.news.core.service.api;

import cn.popo.news.core.dto.api.UserMsgVO;
import cn.popo.news.core.entity.common.UserMsg;

import java.util.List;

public interface IMService {
    List<UserMsgVO> findUserList(String userId);
    boolean findUserAndToUser(String userId,String toUserId);
    void save(String userId,String toUserId,String avatar,String nickName);
    void updateLastTime(Integer id);
    void updateLastMsg(Integer id,String msg);
    void updateUnReadNum(String userId, String toUserId);
    Integer findUserAndToUserId(String userId,String toUserId);
    void addUnreadNum(String userId, String toUserId);
    void updateUnreadNumZero(String userId, String toUserId);
}
