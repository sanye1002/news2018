package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.RewardNotes;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-06-05 下午 8:22
 * @Description description
 */
public interface RewardNotesRepository extends JpaRepository<RewardNotes,Integer> {

    RewardNotes findByUserId(String userId);
}
