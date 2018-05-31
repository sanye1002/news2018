package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.entity.common.ArticleReport;
import cn.popo.news.core.entity.common.Type_info;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/24 10:53
 * @Desc
 */
public interface ReportRepository extends JpaRepository<ArticleReport,String> {
    Page<ArticleReport> findAllByDisposeState(Pageable pageable,Integer disposeState);
    List<ArticleReport> findAllByDisposeState(Integer disposeState);
    List<ArticleReport> findAllByAidAndDisposeState(String article,Integer disposeState);
    void deleteAllByAid(String articleId);
}
