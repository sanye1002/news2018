package cn.popo.news.core.service.api.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.CommentVO;
import cn.popo.news.core.dto.api.ReplyVO;
import cn.popo.news.core.entity.common.*;
import cn.popo.news.core.entity.form.ReplyForm;
import cn.popo.news.core.entity.form.ReplyReportForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.*;
import cn.popo.news.core.service.api.AgoReplyService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2018/5/31 19:14
 * @Desc 前台回复逻辑控制中心
 */

@Service
@Transactional
public class AgoReplyServiceImpl implements AgoReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReplyReportRepository replyReportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReplyPraiseRepository replyPraiseRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;


    /**
     * 回复上传
     */
    @Override
    public void replySave(ReplyForm replyForm) {
        //人气+1
        String articleId = commentRepository.findOne(replyForm.getCommId()).getAid();
        ArticleInfo articleInfo = articleRepository.findOne(articleId);
        Integer lookNum = articleInfo.getLookNum();
        articleInfo.setLookNum(lookNum+1);


        Reply reply = new Reply();
        BeanUtils.copyProperties(replyForm, reply);
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
        BeanUtils.copyProperties(replyReportForm, replyReport);
        Long l = System.currentTimeMillis();
        replyReport.setTime(l);
        replyReport.setId(KeyUtil.genUniqueKey());
        replyReport.setDisposeState(ResultEnum.SUCCESS.getCode());
        replyReportRepository.save(replyReport);
    }


    /**
     * 通过评论id查找可以显示的回复
     */
    @Override
    public PageDTO<ReplyVO> findReplyByCommentId(Pageable pageable, String commentId,String userId, Integer showState) {
        PageDTO<ReplyVO> pageDTO = new PageDTO<>();
        Page<Reply> replyPage = replyRepository.findAllByCommIdAndShowState(pageable, commentId, showState);
        System.out.println(replyPage);
        Long time = System.currentTimeMillis();
        List<ReplyVO> list = new ArrayList<ReplyVO>();
        if (replyPage != null) {
            pageDTO.setTotalPages(replyPage.getTotalPages());
            if (!replyPage.getContent().isEmpty()) {
                replyPage.getContent().forEach(l -> {
                    System.out.println(l.getId());
                    ReplyVO replyVO = new ReplyVO();
                    BeanUtils.copyProperties(l, replyVO);
                    User user = userRepository.findOne(l.getRid());//回复者的id查找用户
                    User byUser = userRepository.findOne(l.getCid());//被回复者的id查找用户
                    replyVO.setAvatar(user.getAvatar());
                    replyVO.setNickName(user.getNikeName());
                    replyVO.setByNickName(byUser.getNikeName());
                    if (!l.getByReplyId().equals("0")) {
                        replyVO.setByReplyInfo(replyRepository.findOne(l.getByReplyId()).getReplyInfo());
                    }
                    replyVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time, l.getTime()));
                    ReplyPraise replyPraise = replyPraiseRepository.findAllByUidAndReplyId(userId,l.getId());
                    if (replyPraise!=null){
                        replyVO.setReplyPraiseId(replyPraise.getId());
                    }else {
                        replyVO.setReplyPraiseId(0);
                    }
                    list.add(replyVO);
                });
            }
        }
        pageDTO.setPageContent(list);

        return pageDTO;
    }

    /**
     * 点赞
     */
    @Override
    public void replyPraise(String userId, String replyId) {
        Reply reply = replyRepository.findOne(replyId);
        Integer praiseNum = reply.getPraiseNum()+1;
        reply.setPraiseNum(praiseNum);
        ReplyPraise replyPraise = new ReplyPraise();
        replyPraise.setReplyId(replyId);
        replyPraise.setUid(userId);
        replyPraiseRepository.save(replyPraise);
    }

    /**
     * 取消点赞
     */
    @Override
    public void deleteReplyPraise(Integer praiseId, String replyId) {
        Reply reply = replyRepository.findOne(replyId);
        Integer praiseNum = reply.getPraiseNum()-1;
        reply.setPraiseNum(praiseNum);
        replyPraiseRepository.delete(praiseId);
    }

}
