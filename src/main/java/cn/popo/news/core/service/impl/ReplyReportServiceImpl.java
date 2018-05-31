package cn.popo.news.core.service.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.ReplyReportDTO;
import cn.popo.news.core.entity.common.Comment;
import cn.popo.news.core.entity.common.CommentReport;
import cn.popo.news.core.entity.common.Reply;
import cn.popo.news.core.entity.common.ReplyReport;
import cn.popo.news.core.entity.form.ReplyReportForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.ReplyReportRepository;
import cn.popo.news.core.repository.ReplyRepository;
import cn.popo.news.core.repository.ReportTypeRepository;
import cn.popo.news.core.service.ReplyReportService;
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
 * @Desc    回复举报逻辑控制中心
 */

@Transactional
@Service
public class ReplyReportServiceImpl implements ReplyReportService {

    @Autowired
    ReplyReportRepository replyReportRepository;

    @Autowired
    ReportTypeRepository reportTypeRepository;

    @Autowired
    ReplyRepository replyRepository;



    /**
     * 举报回复管理
     */

    @Override
    public void updateReplyShowState(String id, Integer disposeState, String replyId, Integer dispose) {
        ReplyReport replyReport = replyReportRepository.findOne(id);
        replyReport.setDisposeState(ResultEnum.PARAM_NULL.getCode());
        if(dispose == 1){
            Reply reply = replyRepository.findOne(replyId);
            reply.setShowState(ResultEnum.SUCCESS.getCode());
            replyReportRepository.findAllByReplyIdAndDisposeState(replyId,disposeState).forEach(l->{
                l.setDisposeState(ResultEnum.PARAM_NULL.getCode());
            });
        }
    }

    /**
     * 评论回复举报展示
     */
    @Override
    public PageDTO<ReplyReportDTO> findAllReplyReportByDisposeSate(Pageable pageable, Integer disposeState) {
        PageDTO<ReplyReportDTO> pageDTO = new PageDTO<>();
        List<ReplyReportDTO> list = new ArrayList<>();
        Page<ReplyReport> replyReportPage = replyReportRepository.findAllByDisposeState(pageable,disposeState);
        pageDTO.setTotalPages(replyReportPage.getTotalPages());
        if (!replyReportPage.getContent().isEmpty()){
            replyReportPage.getContent().forEach(l->{
                ReplyReportDTO replyReportDTO = new ReplyReportDTO();
                BeanUtils.copyProperties(l,replyReportDTO);
                replyReportDTO.setType(reportTypeRepository.findOne(l.getTypeId()).getReportType());
                replyReportDTO.setReply(replyRepository.findOne(l.getReplyId()));
                replyReportDTO.setTime(GetTimeUtil.getDateFormat(l.getTime()));
                list.add(replyReportDTO);
            });
        }
        pageDTO.setPageContent(list);
        return pageDTO;
    }

    /**
     * 评论回复-数据长度
     */
    @Override
    public Integer findReplyReportByDisposeStateNum(Integer disposeState) {
        List<ReplyReport> list = replyReportRepository.findAllByDisposeState(disposeState);
        if (list.isEmpty()){
            return 0;
        }else {
            return list.size();
        }
    }

    /**
     * 删除某回复的所有举报
     */
    @Override
    public void deleteReplyReportByReplyId(String replyId) {
        replyReportRepository.deleteAllByReplyId(replyId);
    }
}
