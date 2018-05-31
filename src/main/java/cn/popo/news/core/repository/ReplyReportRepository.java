package cn.popo.news.core.repository;


import cn.popo.news.core.entity.common.CommentReport;
import cn.popo.news.core.entity.common.ReplyReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:41
 * @Desc
 */
public interface ReplyReportRepository extends JpaRepository<ReplyReport,String> {
    Page<ReplyReport> findAllByDisposeState(Pageable pageable, Integer disposeState);
    List<ReplyReport> findAllByDisposeState(Integer disposeState);
    List<ReplyReport> findAllByReplyIdAndDisposeState(String reply,Integer disposeState);
    void deleteAllByReplyId(String replyId);
}
