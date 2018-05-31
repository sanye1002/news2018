package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.entity.common.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface ReplyRepository extends JpaRepository<Reply,String> {
    void deleteAllByCommId(String commentId);
    List<Reply> findAllByCommId(String commentId);
}
