package cn.popo.news.core.service.api.impl;

import cn.popo.news.core.entity.common.Reply;
import cn.popo.news.core.entity.common.ReplyReport;
import cn.popo.news.core.entity.form.ReplyForm;
import cn.popo.news.core.entity.form.ReplyReportForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.ReplyReportRepository;
import cn.popo.news.core.repository.ReplyRepository;
import cn.popo.news.core.service.api.AgoReplyService;
import cn.popo.news.core.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author  Administrator
 * @Date    2018/5/31 19:14
 * @Desc    前台回复逻辑控制中心
 */

@Service
@Transactional
public class AgoReplyServiceImpl implements AgoReplyService {
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReplyReportRepository replyReportRepository;

    /**
     * 回复上传
     */
    @Override
    public void replySave(ReplyForm replyForm) {
        Reply reply = new Reply();
        BeanUtils.copyProperties(replyForm,reply);
        Long l = System.currentTimeMillis();
        reply.setTime(l);
        reply.setPraiseNum(ResultEnum.SUCCESS.getCode());
        reply.setId(KeyUtil.genUniqueKey());
        reply.setShowState(ResultEnum.PARAM_NULL.getCode());
        replyRepository.save(reply);
    }

    /**
     * 评论回复举报上传
     */
    @Override
    public void replyReportSave(ReplyReportForm replyReportForm) {
        ReplyReport replyReport = new ReplyReport();
        BeanUtils.copyProperties(replyReportForm,replyReport);
        Long l = System.currentTimeMillis();
        replyReport.setTime(l);
        replyReport.setId(KeyUtil.genUniqueKey());
        replyReportRepository.save(replyReport);
    }
}
