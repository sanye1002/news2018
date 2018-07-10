package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.ArticleIssueNum;
import cn.popo.news.core.entity.common.Classify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface ArticleIssueNumRepository extends JpaRepository<ArticleIssueNum,Integer> {
    ArticleIssueNum findAllByTimeAndType(String time,Integer type);
    ArticleIssueNum findAllByTime(String time);
}
