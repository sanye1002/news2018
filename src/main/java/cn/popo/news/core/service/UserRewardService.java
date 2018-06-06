package cn.popo.news.core.service;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.WithdrawNotesDTO;
import cn.popo.news.core.entity.common.PointsReward;
import cn.popo.news.core.entity.common.RewardNotes;
import cn.popo.news.core.entity.common.WithdrawNotes;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-05 下午 8:07
 * @Description 奖励service
 */
public interface UserRewardService {

    /**
     * 添加积分加记录
     * @param userId
     * @param articleId
     * @param articleTitle
     * @param articleType
     * @param rewardIntegral 奖励积分
     */
    void addPoints(String userId,String articleId,String articleTitle,String articleType,Integer rewardIntegral);

    /**
     * 查询个人记录
     * @param userId
     * @return
     */
    PointsReward findPointsRewardByUserId(String userId);

    /**
     * 查询提现记录
     * @param userId
     * @return
     */
    Page<WithdrawNotes> findAllWithdrawNotesByUserId(Pageable pageable,String userId);

    /**
     *
     * @param userId
     * @param withdrawSalary 提现金额
     * @return
     */
    ResultVO<Map<String,Object>> WithdrawIntegral(String userId, BigDecimal withdrawSalary);

    /**
     * 审核提现
     * @param pageable
     * @param checkStatus
     * @param resultStatus
     * @return
     */
    PageDTO<WithdrawNotesDTO> findAllWithdrawNotesByCheckStatusAndResultStatus(Pageable pageable, Integer checkStatus, Integer resultStatus);


    /**
     * 审核
     * @param withdrawId
     * @param resultStatus
     * @return
     */
    ResultVO<Map<String,Object>> checkWithdraw(Integer withdrawId,String remark,Integer resultStatus);

    /**
     * 查询记录
     * @param pageable
     * @param userId
     * @return
     */
    Page<RewardNotes> findAllRewardNotesByUserId(Pageable pageable, String userId);

    /**
     *
     * @param status
     * @return
     */
    Integer findAllWithdrawNotesSize(Integer status);

}
