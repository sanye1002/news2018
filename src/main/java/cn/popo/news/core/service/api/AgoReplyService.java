package cn.popo.news.core.service.api;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ReplyVO;
import cn.popo.news.core.entity.form.ReplyForm;
import cn.popo.news.core.entity.form.ReplyReportForm;
import org.springframework.data.domain.Pageable;


public interface AgoReplyService {
    void replySave(ReplyForm replyForm);
    void replyReportSave(ReplyReportForm replyReportForm);
    PageDTO<ReplyVO> findReplyByCommentId(Pageable pageable, String commentId,String userId, Integer showState);
    void replyPraise(String userId,String replyId);
    void deleteReplyPraise(Integer praiseId,String replyId);
}
