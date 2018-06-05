package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.PointsReward;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-05 下午 7:28
 * @Description description
 */

public interface PointsRewardRepository extends JpaRepository<PointsReward,Integer>{

    PointsReward findByUserId(String userId);
}
