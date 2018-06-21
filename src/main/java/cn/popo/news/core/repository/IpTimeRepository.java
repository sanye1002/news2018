package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.IpTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpTimeRepository extends JpaRepository<IpTime,Integer> {
}
