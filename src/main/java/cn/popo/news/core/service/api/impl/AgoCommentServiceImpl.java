package cn.popo.news.core.service.api.impl;

import cn.popo.news.common.utils.KeyWordFilter;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.CommentVO;
import cn.popo.news.core.entity.common.*;
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
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private CommentPraiseRepository commentPraiseRepository;

    @Autowired
    private CommentReportRepository commentReportRepository;

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 查找评论通过文章id
     */
    @Override
    public PageDTO<CommentVO> findComment(Pageable pageable, String articleId,String userId,Integer showState) {
        PageDTO<CommentVO> pageDTO = new PageDTO<>();
        Page<Comment> commentPage = commentRepository.findAllByAidAndShowState(pageable, articleId, showState);
        Long time = System.currentTimeMillis();
        List<CommentVO> list = new ArrayList<CommentVO>();
        if (commentPage != null) {
            pageDTO.setTotalPages(commentPage.getTotalPages());
            if (!commentPage.getContent().isEmpty()) {
                commentPage.getContent().forEach(l -> {
                    CommentVO commentVO = new CommentVO();
                    BeanUtils.copyProperties(l, commentVO);
                    User user = userRepository.findOne(l.getUid());
                    commentVO.setAvatar(user.getAvatar());
                    commentVO.setNickName(user.getNikeName());
                    commentVO.setReplyNum(replyRepository.findAllByCommId(l.getId()).size());
                    commentVO.setManyTimeAgo(GetTimeUtil.getCurrentTimeMillisDiff(time, l.getTime()));
                    commentVO.setUserID(l.getUid());
                    CommentPraise commentPraise = commentPraiseRepository.findAllByUidAndCommentId(userId,l.getId());
                    if (commentPraise!=null){
                        commentVO.setCommentPraiseId(commentPraise.getId());
                    }else {
                        commentVO.setCommentPraiseId(0);
                    }
                    list.add(commentVO);
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
    public void commentPraise(String userId, String commentId) {
        Comment comment = commentRepository.findOne(commentId);
        Integer praiseNum = comment.getPraiseNum()+1;
        comment.setPraiseNum(praiseNum);
        CommentPraise commentPraise = new CommentPraise();
        commentPraise.setCommentId(commentId);
        commentPraise.setUid(userId);
        commentPraiseRepository.save(commentPraise);
    }

    /**
     * 取消点赞
     */
    @Override
    public void deleteCommentPraise(Integer praiseId, String commentId) {
        Comment comment = commentRepository.findOne(commentId);
        Integer praiseNum = comment.getPraiseNum()-1;
        comment.setPraiseNum(praiseNum);
        commentPraiseRepository.delete(praiseId);
    }

    /**
     * 评论保存
     */
    @Override
    public CommentVO commontSave(CommentForm commentForm) {
        //人气+1
        ArticleInfo articleInfo = articleRepository.findOne(commentForm.getAid());
        Integer lookNum = articleInfo.getLookNum();
        articleInfo.setLookNum(lookNum+1);



        Comment comment = new Comment();
        commentForm.setCommentInfo(KeyWordFilter.doFilter(commentForm.getCommentInfo()));
        BeanUtils.copyProperties(commentForm, comment);
        Long l = System.currentTimeMillis();
        comment.setTime(l);
        String id = KeyUtil.genUniqueKey();
        comment.setId(id);
        comment.setPraiseNum(ResultEnum.SUCCESS.getCode());
        comment.setShowState(ResultEnum.PARAM_NULL.getCode());
        commentRepository.save(comment);

        //返回数据
        CommentVO commentVO = new CommentVO();
        User user = userRepository.findOne(commentForm.getUid());
        commentVO.setAvatar(user.getAvatar());
        commentVO.setNickName(user.getNikeName());
        commentVO.setUserID(commentForm.getUid());
        commentVO.setId(id);
        commentVO.setCommentPraiseId(0);
        commentVO.setReplyNum(0);
        commentVO.setPraiseNum(0);
        commentVO.setCommentInfo(commentForm.getCommentInfo());

        return commentVO;

    }

    /**
     * 评论回复举报上传
     */
    @Override
    public void commentReplyReportSave(CommentReportForm commentReplyReportForm) {
        CommentReport commentReport = new CommentReport();
        commentReplyReportForm.setContent(KeyWordFilter.doFilter(commentReplyReportForm.getContent()));
        BeanUtils.copyProperties(commentReplyReportForm, commentReport);
        Long l = System.currentTimeMillis();
        commentReport.setTime(l);
        commentReport.setId(KeyUtil.genUniqueKey());
        commentReport.setDisposeState(ResultEnum.SUCCESS.getCode());
        commentReportRepository.save(commentReport);
    }

    /**
     * 评论数量
     */
    @Override
    public Integer findCommentNumByArticleId(String articleId, Integer showState) {
        return commentRepository.findAllByAidAndShowState(articleId,showState).size();
    }
}
