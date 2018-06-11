package cn.popo.news.core.controller.api;

import cn.popo.news.common.utils.KeyWordFilter;
import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.entity.common.CommentPraise;
import cn.popo.news.core.entity.form.CommentForm;
import cn.popo.news.core.entity.form.CommentReportForm;
import cn.popo.news.core.repository.CommentPraiseRepository;
import cn.popo.news.core.service.api.AgoCommentService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/article")
public class CommentController {

    @Autowired
    private AgoCommentService agoCommentService;
    @Autowired
    private CommentPraiseRepository commentPraiseRepository;
    @Autowired
    private UserSessionUtil userSessionUtil;

    /**
     * @param userId commentId
     * @return
     * @desc 评论点赞
     */
    @PostMapping("/comment/praise")
    public ResultVO<Map<String,Object>> articleCommentPraise(
                                                        @RequestParam(value = "commentId") String commentId,
                                                       @RequestParam(value = "userId") String userId,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response){

        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }
        CommentPraise commentPraise = commentPraiseRepository.findAllByUidAndCommentId(userId,commentId);
        if(commentPraise!=null){
            return ResultVOUtil.error(100,"已赞");
        }
        agoCommentService.commentPraise(userId,commentId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * @param commentPraiseId
     * @return
     * @desc 取消点赞
     */
    @PostMapping("/comment/praise/delete")
    public ResultVO<Map<String,Object>> articleDeletePraise(@RequestParam(value = "commentId") String commentId,
                                                            @RequestParam(value = "commentPraiseId") Integer commentPraiseId,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response){
        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }
        agoCommentService.deleteCommentPraise(commentPraiseId,commentId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * @param commentForm
     * @return
     * @desc 评论上传
     */
    @PostMapping("/comment/save")
    public ResultVO<Map<String,Object>> commentSave(@Valid CommentForm commentForm,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response){
        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }
        String content = commentForm.getCommentInfo();
        if (!KeyWordFilter.checkWords(content).equals("")){
            return ResultVOUtil.error(100,"评论内容违规："+KeyWordFilter.checkWords(content));
        }
        agoCommentService.commontSave(commentForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * @param commentReportForm
     * @return
     * @desc 评论举报上传
     */
    @PostMapping("/comment/report")
    public ResultVO<Map<String,Object>> commentReportSave(@Valid CommentReportForm commentReportForm,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response){
        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }
        String content = commentReportForm.getContent();
        if (!KeyWordFilter.checkWords(content).equals("")){
            return ResultVOUtil.error(100,"举报内容违规："+KeyWordFilter.checkWords(content));
        }
        agoCommentService.commentReplyReportSave(commentReportForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }
}
