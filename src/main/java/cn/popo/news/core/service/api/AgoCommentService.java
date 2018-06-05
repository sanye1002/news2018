package cn.popo.news.core.service.api;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.CommentVO;
import cn.popo.news.core.entity.form.CommentForm;
import cn.popo.news.core.entity.form.CommentReportForm;
import org.springframework.data.domain.Pageable;

public interface AgoCommentService {
    PageDTO<CommentVO> findComment(Pageable pageable,String articleId,String userId,Integer showState);
    void commentPraise(String userId,String commentId);
    void deleteCommentPraise(Integer praiseId,String commentId);
    void commontSave(CommentForm commentForm);
    void commentReplyReportSave(CommentReportForm commentReplyReportForm);
    Integer findCommentNumByArticleId(String articleId,Integer showState);
}
