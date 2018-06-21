package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.IPStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPStatisticsRepository extends JpaRepository<IPStatistics,String> {
    IPStatistics findAllByIp(String ip);
}
