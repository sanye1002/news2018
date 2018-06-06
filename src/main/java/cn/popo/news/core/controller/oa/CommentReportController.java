package cn.popo.news.core.controller.oa;

import cn.popo.news.core.dto.CommentReportDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.form.CommentReportForm;
import cn.popo.news.core.enums.ResultEnum;
import cn.popo.news.core.service.CommentReportService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SortTools;
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
 * @Desc    评论举报控制中心
 */

@Controller
@RequestMapping("/oa/report/comment")
@Slf4j
public class CommentReportController {

    @Autowired
    private CommentReportService commentReportService;



    /**
     * 评论回复举报展示
     */
    @GetMapping("/reportlist")
    @RequiresPermissions("commentReport:list")
    public ModelAndView commentReportList(Map<String,Object> map,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "12") Integer size,
                                   @RequestParam(value = "disposeState", defaultValue = "0") Integer disposeState
    ){
        map.put("pageId",106);
        map.put("pageTitle","评论举报");
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","time"));
        PageDTO<CommentReportDTO> pageDTO = commentReportService.findAllCommentReportByDisposeSate(pageRequest,disposeState);
        Integer OnDispose = commentReportService.findCommentReportByDisposeStateNum(ResultEnum.PARAM_NULL.getCode());
        Integer UnDispose = commentReportService.findCommentReportByDisposeStateNum(ResultEnum.SUCCESS.getCode());
        map.put("pageContent", pageDTO);
        map.put("url", "/oa/article/commetnreportlist.html");
        map.put("size", size);
        map.put("disposeState",disposeState);
        map.put("OnDispose",OnDispose);
        map.put("UnDispose",UnDispose);
        map.put("currentPage", page);
        return new ModelAndView("pages/article-comment-report-list",map);
    }

    /**
     * 举报管理（标记为已处理，取消展示并标记为处理）
     */
    @PostMapping("/reportdispose")
    @ResponseBody
    public ResultVO<Map<String, String>>commentReportDispose(
            @RequestParam(value = "commentId") String commentId,
            @RequestParam(value = "disposeState") Integer disposeState,
            @RequestParam(value = "dispose") Integer dispose,
            @RequestParam(value = "id") String id
    ){
        commentReportService.updateCommentShowState(id,disposeState,commentId,dispose);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }
}
