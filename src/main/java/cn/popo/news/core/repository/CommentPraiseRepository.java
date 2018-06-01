package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.CommentPraise;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface CommentPraiseRepository extends JpaRepository<CommentPraise,Integer> {
    CommentPraise findAllByUidAndCommentId(String uid,String commentId);
}
