package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.ReplyPraise;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface ReplyPraiseRepository extends JpaRepository<ReplyPraise,Integer> {
    ReplyPraise findAllByUidAndReplyId(String uid,String replyId);
}
