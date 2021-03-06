package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.entity.common.SearchWords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface SearchWordsRepository extends JpaRepository<SearchWords,Integer> {
    SearchWords findAllByContent(String content);
    Page<SearchWords> findAll(Pageable pageable);
}
