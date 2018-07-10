package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.IpTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IpTimeRepository extends JpaRepository<IpTime,Integer> {
    List<IpTime> findAllByTime(String time);
    List<IpTime> findAllByTimeEndingWith(String week);
}
