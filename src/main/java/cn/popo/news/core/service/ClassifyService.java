package cn.popo.news.core.service;

import cn.popo.news.core.entity.common.Classify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:52
 * @Desc
 */
public interface ClassifyService {
    List<Classify> findAllClassify();
    void saveClassify(String classify,Integer id);
    void deleteClassify(Integer id);
    Classify findClassifyById(Integer id);
    Page<Classify> findClassify(Pageable pageable);

}
