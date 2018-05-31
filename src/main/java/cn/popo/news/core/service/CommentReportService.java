package cn.popo.news.core.service;

import cn.popo.news.core.dto.CommentReportDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.form.CommentReportForm;
import org.springframework.data.domain.Pageable;

public interface CommentReportService {
    void updateCommentShowState(String id, Integer disposeState, String commentId, Integer dispose);
    void deleteCommentReportByCommentId(String commentId);
    PageDTO<CommentReportDTO> findAllCommentReportByDisposeSate(Pageable pageable, Integer disposeState);
    Integer findCommentReportByDisposeStateNum(Integer disposeState);
}
