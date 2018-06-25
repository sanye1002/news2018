package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.IPStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPStatisticsRepository extends JpaRepository<IPStatistics,String> {
    List<IPStatistics> findAllByIpOrderByNewestTimeDesc(String ip);
}
