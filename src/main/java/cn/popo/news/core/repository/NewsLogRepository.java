package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.ArticleViewUser;
import cn.popo.news.core.entity.common.NewsLogs;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author zhaoxiang
 * @Date 2018/11/09
 * @Desc
 */
public interface NewsLogRepository extends JpaRepository<NewsLogs,String> {

}
