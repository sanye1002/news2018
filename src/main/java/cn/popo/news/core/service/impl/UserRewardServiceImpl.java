package cn.popo.news.core.service.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.WithdrawNotesDTO;
import cn.popo.news.core.entity.common.PointsReward;
import cn.popo.news.core.entity.common.RewardNotes;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.common.WithdrawNotes;
import cn.popo.news.core.repository.PointsRewardRepository;
import cn.popo.news.core.repository.RewardNotesRepository;
import cn.popo.news.core.repository.UserRepository;
import cn.popo.news.core.repository.WithdrawNotesRepository;
import cn.popo.news.core.service.UserRewardService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SendMessageUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-05 下午 8:21
 * @Description description
 */
@Service
@Transactional
public class UserRewardServiceImpl implements UserRewardService {

    @Autowired
    private PointsRewardRepository pointsRewardRepository;
    @Autowired
    private WithdrawNotesRepository withdrawNotesRepository;
    @Autowired
    private RewardNotesRepository rewardNotesRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void addPoints(String userId, String articleId, String articleTitle, String articleType, Integer rewardIntegral) {
        User user = userRepository.findOne(userId);
        PointsReward pointsReward = new PointsReward();
        if (pointsRewardRepository.findByUserId(userId)!=null){
            pointsReward = pointsRewardRepository.findByUserId(userId);
        }
        RewardNotes rewardNotes = new RewardNotes();
        if (user != null) {
            pointsReward.setAllIntegral(pointsReward.getAllIntegral() + rewardIntegral);
            pointsReward.setLaveIntegral(pointsReward.getLaveIntegral() + rewardIntegral);
            pointsReward.setUserId(userId);
            pointsReward.setPhone(user.getPhone());
            pointsReward.setUsername(user.getNikeName());
            pointsRewardRepository.save(pointsReward);
            rewardNotes.setArticleId(articleId);
            rewardNotes.setArticleTitle(articleTitle);
            rewardNotes.setArticleType(articleType);
            rewardNotes.setRewardIntegral(rewardIntegral);
            rewardNotes.setCreateTime(GetTimeUtil.getDate());
            rewardNotes.setUserId(userId);
            rewardNotesRepository.save(rewardNotes);
        }
    }

    @Override
    public PointsReward findPointsRewardByUserId(String userId) {
        PointsReward pointsReward = new PointsReward();
        if (pointsRewardRepository.findByUserId(userId)!=null){
            pointsReward = pointsRewardRepository.findByUserId(userId);
            pointsReward.setLaveSalary(BigDecimal.valueOf((double)pointsReward.getLaveIntegral()/100));
            return pointsRewardRepository.save(pointsReward);
        }
        return pointsReward;

    }

    @Override
    public Page<WithdrawNotes> findAllWithdrawNotesByUserId(Pageable pageable,String userId) {

        return withdrawNotesRepository.findAllByUserId(pageable, userId);

    }

    @Override
    public ResultVO<Map<String, Object>> WithdrawIntegral(String userId, BigDecimal withdrawSalary) {
        PointsReward pointsReward = pointsRewardRepository.findByUserId(userId);
        if (pointsReward.getLaveSalary().compareTo(withdrawSalary)==-1){
            return ResultVOUtil.error(100,"提现金额大于可提现金额~");
        }
        pointsReward.setLaveSalary(pointsReward.getLaveSalary().subtract(withdrawSalary));
        pointsReward.setLaveIntegral(pointsReward.getLaveIntegral()-(withdrawSalary.multiply(BigDecimal.valueOf(100))).intValue());
        pointsRewardRepository.save(pointsReward);
        WithdrawNotes withdrawNotes = new WithdrawNotes();
        withdrawNotes.setCreateTime(GetTimeUtil.getDate());
        withdrawNotes.setUserId(userId);
        withdrawNotes.setWithdrawSalary(withdrawSalary);
        withdrawNotesRepository.save(withdrawNotes);
        Map<String,Object> map = new HashMap();
        map.put("message","成功申请提现:"+withdrawSalary);
        return ResultVOUtil.success(map);
    }

    @Override
    public PageDTO<WithdrawNotesDTO> findAllWithdrawNotesByCheckStatusAndResultStatus(Pageable pageable, Integer checkStatus, Integer resultStatus) {
        PageDTO pageDTO = new PageDTO();
        Page<WithdrawNotes> withdrawNotesPage = withdrawNotesRepository.findAllByCheckStatusAndResultStatus(pageable, checkStatus, resultStatus);
        pageDTO.setTotalPages(withdrawNotesPage.getTotalPages());
        List<WithdrawNotesDTO> list = new ArrayList<>();
        if (!withdrawNotesPage.getContent().isEmpty()){
            withdrawNotesPage.getContent().forEach( l->{
                WithdrawNotesDTO withdrawNotesDTO = new WithdrawNotesDTO();
                BeanUtils.copyProperties(l,withdrawNotesDTO);
                withdrawNotesDTO.setUser(userRepository.findOne(l.getUserId()));
                list.add(withdrawNotesDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    @Override
    public ResultVO<Map<String, Object>> checkWithdraw(Integer withdrawId,String remark, Integer resultStatus) {
        WithdrawNotes withdrawNotes = withdrawNotesRepository.findOne(withdrawId);
        if (withdrawNotes==null){
            return ResultVOUtil.error(100,"服务器已无记录~");
        }
        withdrawNotes.setCheckTime(GetTimeUtil.getDate());
        withdrawNotes.setCheckStatus(1);
        withdrawNotes.setResultStatus(resultStatus);
        withdrawNotes.setRemark(remark);
        withdrawNotesRepository.save(withdrawNotes);
        //发送短信
        Map<String,Object> map = new HashMap<>();
        User messageUser = userRepository.findOne(withdrawNotes.getUserId());
        String phone = messageUser.getPhone();
        String username = messageUser.getName();
        String type = "积分提现审核";
        String result;
        if (resultStatus == 1) {
            result = "通过,已拨款";
        } else {
            result = "审核失败";
        }

        if(SendMessageUtil.sendSalaryTypeMessage(phone, username, type, result)){
            map.put("message","短信发送成功！");
        }else {
            map.put("message","短信发送失败！");
        }
        return ResultVOUtil.success(map);
    }

    @Override
    public Page<RewardNotes> findAllRewardNotesByUserId(Pageable pageable, String userId) {
        return rewardNotesRepository.findAllByUserId(pageable, userId);
    }

    @Override
    public Integer findAllWithdrawNotesSize(Integer status) {
        if (withdrawNotesRepository.findAllByCheckStatus(status).isEmpty()){
            return 0;
        }
        return withdrawNotesRepository.findAllByCheckStatus(status).size();
    }


}
