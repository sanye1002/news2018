package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.CommentPraise;
import cn.popo.news.core.entity.common.Dynamic;
import cn.popo.news.core.entity.common.DynamicPraise;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface DynamicPraiseRepository extends JpaRepository<DynamicPraise,Integer> {
    DynamicPraise findAllByUidAndDynamicId(String uid, String dynamicId);
}
