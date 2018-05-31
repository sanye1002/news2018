package cn.popo.news.core.controller.api;

import cn.popo.news.core.entity.form.CommentForm;
import cn.popo.news.core.entity.form.CommentReportForm;
import cn.popo.news.core.service.api.AgoCommentService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/article")
public class CommentController {

    @Autowired
    AgoCommentService agoCommentService;

    /**
     * 评论点赞
     */
    @ResponseBody
    @PostMapping("/praise")
    public ResultVO<Map<String,Object>> articlePraise(@RequestParam(value = "pid") String pid,
                                                       @RequestParam(value = "userId") String userId){
        agoCommentService.praise(pid,userId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 评论删除点赞
     */
    @ResponseBody
    @PostMapping("/praise/delete")
    public ResultVO<Map<String,Object>> articleDeletePraise(@RequestParam(value = "praiseId") Integer praiseId){
        agoCommentService.deletePraise(praiseId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 评论上传
     */
    @ResponseBody
    @PostMapping("/comment/save")
    public ResultVO<Map<String,Object>> commentSave(@Valid CommentForm commentForm){
        agoCommentService.commontSave(commentForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 评论举报上传
     */
    @ResponseBody
    @PostMapping("/comment/report")
    public ResultVO<Map<String,Object>> commentReportSave(@Valid CommentReportForm commentReportForm){
        agoCommentService.commentReplyReportSave(commentReportForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }
}
