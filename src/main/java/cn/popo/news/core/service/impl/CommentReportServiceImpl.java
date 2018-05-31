package cn.popo.news.core.service.impl;

import cn.popo.news.core.dto.CommentReportDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.Comment;
import cn.popo.news.core.entity.common.CommentReport;
import cn.popo.news.core.entity.form.CommentReportForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.CommentReportRepository;
import cn.popo.news.core.repository.CommentRepository;
import cn.popo.news.core.repository.ReportTypeRepository;
import cn.popo.news.core.service.CommentReportService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author  Administrator
 * @Date    2018/5/26 13:33
 * @Desc    评论举报逻辑控制中心
 */

@Transactional
@Service
public class CommentReportServiceImpl implements CommentReportService {

    @Autowired
    CommentReportRepository commentReportRepository;

    @Autowired
    ReportTypeRepository reportTypeRepository;

    @Autowired
    CommentRepository commentRepository;



    /**
     * 举报回复管理
     */

    @Override
    public void updateCommentShowState(String id, Integer disposeState, String commentId, Integer dispose) {
        CommentReport commentReport = commentReportRepository.findOne(id);
        commentReport.setDisposeState(ResultEnum.PARAM_NULL.getCode());
        if(dispose == 1){
            Comment comment = commentRepository.findOne(commentId);
            comment.setShowState(ResultEnum.SUCCESS.getCode());
            commentReportRepository.findAllByCommentIdAndDisposeState(commentId,disposeState).forEach(l->{
                l.setDisposeState(ResultEnum.PLATFORM_BOOS_NULL.getCode());
            });
        }
    }

    /**
     * 根据commentID-评论举报删除
     */
    @Override
    public void deleteCommentReportByCommentId(String commentId) {
        commentReportRepository.deleteAllByCommentId(commentId);
    }

    /**
     * 评论回复举报展示
     */
    @Override
    public PageDTO<CommentReportDTO> findAllCommentReportByDisposeSate(Pageable pageable, Integer disposeState) {
        PageDTO<CommentReportDTO> pageDTO = new PageDTO<>();
        List<CommentReportDTO> list = new ArrayList<>();
        Page<CommentReport> commentReplyReportPage = commentReportRepository.findAllByDisposeState(pageable,disposeState);
        pageDTO.setTotalPages(commentReplyReportPage.getTotalPages());
        if (!commentReplyReportPage.getContent().isEmpty()){
            commentReplyReportPage.getContent().forEach(l->{
                CommentReportDTO commentReportDTO = new CommentReportDTO();
                BeanUtils.copyProperties(l,commentReportDTO);
                commentReportDTO.setType(reportTypeRepository.findOne(l.getTypeId()).getReportType());
                commentReportDTO.setComment(commentRepository.findOne(l.getCommentId()));
                commentReportDTO.setTime(GetTimeUtil.getDateFormat(l.getTime()));
                list.add(commentReportDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    /**
     * 评论回复-数据长度
     */
    @Override
    public Integer findCommentReportByDisposeStateNum(Integer disposeState) {
        List<CommentReport> list = commentReportRepository.findAllByDisposeState(disposeState);
        if (list.isEmpty()){
            return 0;
        }else {
            return list.size();
        }
    }
}
