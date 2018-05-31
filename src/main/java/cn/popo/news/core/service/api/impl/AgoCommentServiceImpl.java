package cn.popo.news.core.service.api.impl;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.CommentVO;
import cn.popo.news.core.entity.common.Comment;
import cn.popo.news.core.entity.common.CommentReport;
import cn.popo.news.core.entity.common.Praise;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.entity.form.CommentForm;
import cn.popo.news.core.entity.form.CommentReportForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.repository.*;
import cn.popo.news.core.service.api.AgoCommentService;
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

@Service
@Transactional
public class AgoCommentServiceImpl implements AgoCommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    PraiseRepository praiseRepository;

    @Autowired
    CommentReportRepository commentReportRepository;

    /**
     * 查找评论通过文章id
     */
    @Override
    public PageDTO<CommentVO> findComment(Pageable pageable, String articleId,Integer showState) {
        PageDTO<CommentVO> pageDTO = new PageDTO<>();
        Page<Comment> commentPage = commentRepository.findAllByAidAndShowState(pageable,articleId,showState);
        Long time = System.currentTimeMillis();
        List<CommentVO> list = new ArrayList<CommentVO>();
        if(commentPage != null){
            pageDTO.setTotalPages(commentPage.getTotalPages());
            if (!commentPage.getContent().isEmpty()){
                commentPage.getContent().forEach(l->{
                    CommentVO commentVO = new CommentVO();
                    BeanUtils.copyProperties(l,commentVO);
                    User user = userRepository.findOne(l.getUid());
                    commentVO.setAvatar(user.getAvatar());
                    commentVO.setNickName(user.getNikeName());
                    commentVO.setReplyNum(replyRepository.findAllByCommId(l.getId()).size());
                    commentVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time,l.getTime()));
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
    public void praise(String pid, String userId) {
        Praise praise = new Praise();
        praise.setPid(pid);
        praise.setUid(userId);
        praiseRepository.save(praise);
    }

    /**
     * 取消点赞
     */
    @Override
    public void deletePraise(Integer praiseId) {
        praiseRepository.delete(praiseId);
    }

    /**
     *评论保存
     */
    @Override
    public void commontSave(CommentForm commentForm) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentForm,comment);
        Long l = System.currentTimeMillis();
        comment.setTime(l);
        comment.setId(KeyUtil.genUniqueKey());
        comment.setPraiseNum(ResultEnum.SUCCESS.getCode());
        comment.setShowState(ResultEnum.PARAM_NULL.getCode());
        commentRepository.save(comment);
    }

    /**
     * 评论回复举报上传
     */
    @Override
    public void commentReplyReportSave(CommentReportForm commentReplyReportForm) {
        CommentReport commentReport = new CommentReport();
        BeanUtils.copyProperties(commentReplyReportForm,commentReport);
        Long l = System.currentTimeMillis();
        commentReport.setTime(l);
        commentReport.setId(KeyUtil.genUniqueKey());
        commentReportRepository.save(commentReport);
    }
}
