package cn.popo.news.core.controller.api;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ReplyVO;
import cn.popo.news.core.entity.form.ReplyForm;
import cn.popo.news.core.entity.form.ReplyReportForm;
import cn.popo.news.core.service.api.AgoReplyService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SortTools;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/article")
public class ReplyController {

    private static final Integer ONE = 1;

    @Autowired
    private AgoReplyService agoReplyService;


    /**
     * @param replyForm
     * @return
     * @desc 回复上传
     */
    @PostMapping("/reply/save")
    public ResultVO<Map<String,Object>> replySave(@Valid ReplyForm replyForm){
        agoReplyService.replySave(replyForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * @param replyReportForm
     * @return
     * @desc 回复举报
     */
    @PostMapping("reply/report")
    public ResultVO<Map<String,Object>> replyReportSave(@Valid ReplyReportForm replyReportForm){
        agoReplyService.replyReportSave(replyReportForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }


    /**
     * @param commentId page size
     * @return
     * @desc 回复数据
     */
    @PostMapping("/reply/list")
    public ResultVO<Map<String,Object>> replyList(Map<String,Object> map,
                                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                  @RequestParam(value = "userId", defaultValue = "12") String userId,
                                                  @RequestParam(value = "commentId") String commentId
    ){

        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","time"));
        PageDTO<ReplyVO> pageDTO = agoReplyService.findReplyByCommentId(pageRequest,commentId,userId,ONE);
        map.put("pageContent", pageDTO);
        map.put("size", size);
        map.put("currentPage", page);
        return ResultVOUtil.success(map);
    }

    /**
     * @param replyId userId
     * @return
     * @desc 回复点赞
     */
    @PostMapping("/reply/praise")
    public ResultVO<Map<String,Object>> articleCommentPraise(@RequestParam(value = "replyId") String replyId,
                                                             @RequestParam(value = "userId") String userId){
        agoReplyService.replyPraise(userId,replyId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * @param replyPraiseId replyId
     * @return
     * @desc 取消点赞
     */
    @PostMapping("/reply/praise/delete")
    public ResultVO<Map<String,Object>> articleDeletePraise(@RequestParam(value = "replyId") String replyId,
                                                            @RequestParam(value = "replyPraiseId") Integer replyPraiseId){
        agoReplyService.deleteReplyPraise(replyPraiseId,replyId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

}
