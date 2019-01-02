package cn.popo.news.core.service.api.impl;

import cn.popo.news.core.dto.api.UserMsgVO;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.common.UserMsg;
import cn.popo.news.core.repository.UserMsgRepository;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.service.api.AgoPersonalService;
import cn.popo.news.core.service.api.IMService;
import cn.popo.news.core.utils.GetTimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class IMServiceImpl implements IMService {

    @Autowired
    private UserMsgRepository userMsgRepository;
    @Autowired
    private AgoPersonalService agoPersonalService;
    @Autowired
    private UserRepository userRepository;


    /**
     * 查找用户消息列表
     * @param userId 用户id
     * @return 返回查询到的列表
     */
    @Override
    public List<UserMsgVO> findUserList(String userId) {
        List<UserMsg> userMsgList = userMsgRepository.findByStateAndUserId(1, userId);
        List<UserMsgVO> list = new ArrayList<>();
        if(!userMsgList.isEmpty()){
            userMsgList.forEach(l->{
                UserMsgVO userMsgVO = new UserMsgVO();
                BeanUtils.copyProperties(l,userMsgVO);
                userMsgVO.setLastTime(GetTimeUtil.getMsgTime(l.getLastTime(),System.currentTimeMillis()));
                list.add(userMsgVO);
            });
        }
        return list;
    }


    /**
     * 查找是否存在
     * @param userId 用户id
     * @param toUserId 对方的id
     * @return 存在返回false，不存在返回true
     */
    @Override
    public boolean findUserAndToUser(String userId, String toUserId) {
        UserMsg userMsg = userMsgRepository.findByUserIdAndToUserId(userId, toUserId);
        return userMsg == null;
    }

    /**
     * 查找该条记录id
     * @param userId 用户id
     * @param toUserId 对方的id
     * @return id
     */
    public Integer findUserAndToUserId(String userId, String toUserId) {
       return  userMsgRepository.findByUserIdAndToUserId(userId, toUserId).getId();
    }


    /**
     * 存入数据库
     * @param userId 用户id
     * @param toUserId 对方用户id
     * @param avatar 对方用户头像
     * @param nickName 对方用户昵称
     */
    @Override
    public void save(String userId, String toUserId, String avatar, String nickName) {
        boolean saveFlag = findUserAndToUser(userId, toUserId);
        if (saveFlag){
            UserMsg userMsg = new UserMsg();
            userMsg.setState(1);
            userMsg.setUserId(userId);
            userMsg.setToUserAvatar(avatar);
            userMsg.setToUserName(nickName);
            userMsg.setToUserId(toUserId);
            userMsg.setLastTime(System.currentTimeMillis());
            userMsgRepository.save(userMsg);
        }
    }

    /**
     * 更新最后发送消息的时间
     * @param id id
     */
    @Override
    public void updateLastTime(Integer id) {
        UserMsg userMsg = userMsgRepository.findOne(id);
        userMsg.setLastTime(System.currentTimeMillis());
        userMsgRepository.save(userMsg);
    }

    /**
     * 更新最后发送的消息
     * @param id id
     * @param msg 内容
     */
    @Override
    public void updateLastMsg(Integer id, String msg) {
        UserMsg userMsg = userMsgRepository.findOne(id);
        userMsg.setLastMsg(msg);
        userMsgRepository.save(userMsg);
    }

    /**
     * 更新未读状态
     * @param userId 用户id
     * @param toUserId 对方用户id
     */
    @Override
    public void updateUnReadNum(String userId, String toUserId) {
        UserMsg userMsg = userMsgRepository.findByUserIdAndToUserId(toUserId,userId);
        Integer size = agoPersonalService.findUnReadNum(userId,toUserId);
        userMsg.setUnReadNum(size);
        userMsgRepository.save(userMsg);
    }

    /**
     * 未读消息+1
     * @param userId 用户id
     * @param toUserId 对方用户id
     */
    @Override
    public void addUnreadNum(String userId, String toUserId) {
        UserMsg userMsg = userMsgRepository.findByUserIdAndToUserId(userId,toUserId);
        userMsg.setUnReadNum(userMsg.getUnReadNum()+1);
        userMsgRepository.save(userMsg);
    }

    /**
     * 更新未读数量为0
     * @param userId 用户id
     * @param toUserId 对方用户id
     */
    @Override
    public void updateUnreadNumZero(String userId, String toUserId) {
        UserMsg userMsg = userMsgRepository.findByUserIdAndToUserId(userId,toUserId);
        if(userMsg!=null){
            if (userMsg.getUnReadNum()!=0){
                userMsg.setUnReadNum(0);
                userMsgRepository.save(userMsg);
            }
        }

    }
}
