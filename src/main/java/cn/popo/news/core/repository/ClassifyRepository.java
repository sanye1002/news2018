package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.Classify;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface ClassifyRepository extends JpaRepository<Classify,Integer> {
    List<Classify> findAllByClassifyLike(String content);
}
