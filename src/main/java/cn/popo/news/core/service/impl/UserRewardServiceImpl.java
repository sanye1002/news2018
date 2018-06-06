package cn.popo.news.core.service.impl;

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
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
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
        PointsReward reward = pointsRewardRepository.findByUserId(userId);
        reward.setLaveSalary(BigDecimal.valueOf((double)reward.getLaveIntegral()/100));
        return pointsRewardRepository.save(reward);
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
    public Page<WithdrawNotes> findAllWithdrawNotesByCheckStatusAndResultStatus(Pageable pageable, Integer checkStatus, Integer resultStatus) {
        return withdrawNotesRepository.findAllByCheckStatusAndResultStatus(pageable, checkStatus, resultStatus);
    }

}
