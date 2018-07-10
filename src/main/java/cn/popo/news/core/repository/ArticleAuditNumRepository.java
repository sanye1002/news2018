package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.ArticleAuditNum;
import cn.popo.news.core.entity.common.ArticleIssueNum;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface ArticleAuditNumRepository extends JpaRepository<ArticleAuditNum,Integer> {
    ArticleAuditNum findAllByTimeAndAuditStateAndType(String time,Integer auditState,Integer type);
    ArticleAuditNum findAllByTimeAndAuditState(String time,Integer auditState);
    ArticleAuditNum findAllByTime(String time);
}
