package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.Dynamic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DynamicRepository extends JpaRepository<Dynamic,Integer> {

    Page<Dynamic> findAllByUserId(Pageable pageable,String userId);
}
