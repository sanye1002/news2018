package cn.popo.news.core.service.api;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.CommentVO;
import cn.popo.news.core.entity.form.CommentForm;
import cn.popo.news.core.entity.form.CommentReportForm;
import org.springframework.data.domain.Pageable;

public interface AgoCommentService {
    PageDTO<CommentVO> findComment(Pageable pageable,String articleId,Integer showState);
    void praise(String pid,String userId);
    void deletePraise(Integer praiseId);
    void commontSave(CommentForm commentForm);
    void commentReplyReportSave(CommentReportForm commentReplyReportForm);
}
