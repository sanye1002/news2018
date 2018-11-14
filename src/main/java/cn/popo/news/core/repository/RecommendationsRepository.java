package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.Recommendations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * @Author zhaoxiang
 * @Date 2018/11/13
 * @Desc
 */
public interface RecommendationsRepository extends JpaRepository<Recommendations,String> {

    Page<Recommendations> findAllByUserIdAndDeriveTimeBetween(Pageable pageable, String userId, Date bTime, Date aTime);
}
