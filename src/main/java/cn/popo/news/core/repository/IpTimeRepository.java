package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.IpTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IpTimeRepository extends JpaRepository<IpTime,Integer> {
    List<IpTime> findAllByTime(String time);
    Page<IpTime> findAllByTime(Pageable pageable,String time);
    List<IpTime> findAllByTimeEndingWith(String week);
    List<IpTime> findAllByTimeAndUtil(String time,String util);
    List<IpTime> findAllByTimeAndAddressId(String time,Integer addressId);
    List<IpTime> findAllByTimeAndBrowser(String time,String browser);
}
