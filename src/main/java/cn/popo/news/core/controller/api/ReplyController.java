package cn.popo.news.core.controller.api;

import cn.popo.news.common.utils.KeyWordFilter;
import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ReplyVO;
import cn.popo.news.core.entity.common.ReplyPraise;
import cn.popo.news.core.entity.form.ReplyForm;
import cn.popo.news.core.entity.form.ReplyReportForm;
import cn.popo.news.core.repository.ReplyPraiseRepository;
import cn.popo.news.core.service.api.AgoReplyService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SortTools;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/article")
public class ReplyController {

    private static final Integer ONE = 1;

    @Autowired
    private AgoReplyService agoReplyService;

    @Autowired
    private ReplyPraiseRepository replyPraiseRepository;

    @Autowired
    private UserSessionUtil userSessionUtil;


    /**
     * @param replyForm
     * @return
     * @desc 回复上传
     */
    @PostMapping("/reply/save")
    public ResultVO<Map<String,Object>> replySave(@Valid ReplyForm replyForm,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response){
        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }
        replyForm.setRid(userSessionUtil.getUserByCookie(request,response).getUserId());
        String content = replyForm.getReplyInfo();
        if (!KeyWordFilter.checkWords(content).equals("")){
            return ResultVOUtil.error(100,"回复内容违规："+KeyWordFilter.checkWords(content));
        }
        ReplyVO replyVO = agoReplyService.replySave(replyForm);
        Map<String,Object> map  = new HashMap<>();
        map.put("reply",replyVO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param replyReportForm
     * @return
     * @desc 回复举报
     */
    @PostMapping("reply/report")
    public ResultVO<Map<String,Object>> replyReportSave(@Valid ReplyReportForm replyReportForm,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response){
        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }
        replyReportForm.setReportId(userSessionUtil.getUserByCookie(request,response).getUserId());
        String content = replyReportForm.getContent();
        if (!KeyWordFilter.checkWords(content).equals("")){
            return ResultVOUtil.error(100,"举报内容违规："+KeyWordFilter.checkWords(content));
        }
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
                                                  @RequestParam(value = "commentId") String commentId,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response
    ){
        String userId = null;

        if (userSessionUtil.verifyLoginStatus(request,response)){
            userId = userSessionUtil.getUserByCookie(request,response).getUserId();
        }


        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","time"));
        PageDTO<ReplyVO> pageDTO = agoReplyService.findReplyByCommentId(pageRequest,commentId,userId,ONE);
        pageDTO.setCurrentPage(page);
        map.put("pageContent", pageDTO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param replyId userId
     * @return
     * @desc 回复点赞
     */
    @PostMapping("/reply/praise")
    public ResultVO<Map<String,Object>> articleCommentPraise(@RequestParam(value = "replyId") String replyId,
                                                             HttpServletRequest request,
                                                             HttpServletResponse response){
        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }

        String userId = userSessionUtil.getUserByCookie(request,response).getUserId();
        ReplyPraise replyPraise = replyPraiseRepository.findAllByUidAndReplyId(userId,replyId);
        if(replyPraise!=null){
            return ResultVOUtil.error(100,"已赞");
        }
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
                                                            @RequestParam(value = "replyPraiseId") Integer replyPraiseId,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response){
        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }
        agoReplyService.deleteReplyPraise(replyPraiseId,replyId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

}
