package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.ArticlePraise;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface ArticlePraiseRepository extends JpaRepository<ArticlePraise,Integer> {
    ArticlePraise findAllByUidAndArticleId(String uid, String articleId);
}
