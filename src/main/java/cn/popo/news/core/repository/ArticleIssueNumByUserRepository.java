package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.ArticleIssueNumByUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleIssueNumByUserRepository extends JpaRepository<ArticleIssueNumByUser,Integer> {
    ArticleIssueNumByUser findAllByTimeAndUserIdAndType(String time,String userId,Integer type);
}
