package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.RadioInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RadioInfoRepository extends JpaRepository<RadioInfo,Integer> {
    Page<RadioInfo> findAllByShowState(Pageable pageable,Integer showState);
}
