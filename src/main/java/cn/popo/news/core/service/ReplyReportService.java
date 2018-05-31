package cn.popo.news.core.service;

import cn.popo.news.core.dto.CommentReportDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.ReplyReportDTO;
import cn.popo.news.core.entity.form.CommentReportForm;
import cn.popo.news.core.entity.form.ReplyReportForm;
import org.springframework.data.domain.Pageable;

public interface ReplyReportService {
    void replyReportSave(ReplyReportForm replyReportForm);
    void updateReplyShowState(String id, Integer disposeState, String replyId, Integer dispose);
    PageDTO<ReplyReportDTO> findAllReplyReportByDisposeSate(Pageable pageable, Integer disposeState);
    Integer findReplyReportByDisposeStateNum(Integer disposeState);
    void deleteReplyReportByReplyId(String replyId);
}
