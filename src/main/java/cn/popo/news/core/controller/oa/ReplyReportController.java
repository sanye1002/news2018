package cn.popo.news.core.controller.oa;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.ReplyReportDTO;
import cn.popo.news.core.entity.form.ReplyReportForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.service.ReplyReportService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/5/26 13:37
 * @Desc    回复举报控制中心
 */

@Controller
@RequestMapping("/oa/report/reply")
@Slf4j
public class ReplyReportController {

    @Autowired
    ReplyReportService replyReportService;


    /**
     * 回复举报上传
     */
    @ResponseBody
    @PostMapping("/report")
    public ResultVO<Map<String,Object>> replyReportSave(@Valid ReplyReportForm replyReportForm){
        replyReportService.replyReportSave(replyReportForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 评论回复举报展示
     */
    @GetMapping("/reportlist")
    @RequiresPermissions("replyReport:list")
    public ModelAndView replyReportList(Map<String,Object> map,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "12") Integer size,
                                   @RequestParam(value = "disposeState", defaultValue = "0") Integer disposeState
    ){
        map.put("pageId",107);
        map.put("pageTitle","回复举报");
        PageRequest pageRequest = new PageRequest(page-1,size);
        PageDTO<ReplyReportDTO> pageDTO = replyReportService.findAllReplyReportByDisposeSate(pageRequest,disposeState);
        Integer OnDispose = replyReportService.findReplyReportByDisposeStateNum(ResultEnum.PARAM_NULL.getCode());
        Integer UnDispose = replyReportService.findReplyReportByDisposeStateNum(ResultEnum.SUCCESS.getCode());
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/article/reply/reportlist.html");
        map.put("size", size);
        map.put("disposeState",disposeState);
        map.put("OnDispose",OnDispose);
        map.put("UnDispose",UnDispose);
        map.put("currentPage", page);
        return new ModelAndView("pages/article-reply-report-list",map);
    }

    /**
     * 举报管理（标记为已处理，取消展示并标记为处理）
     */
    @PostMapping("/reportdispose")
    @ResponseBody
    public ResultVO<Map<String, String>>replyReportDispose(
            @RequestParam(value = "replyId", defaultValue = "1527061901012") String replyId,
            @RequestParam(value = "disposeState", defaultValue = "0") Integer disposeState,
            @RequestParam(value = "dispose", defaultValue = "0") Integer dispose,
            @RequestParam(value = "id", defaultValue = "0") String id
    ){
        replyReportService.updateReplyShowState(id,disposeState,replyId,dispose);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }
}
