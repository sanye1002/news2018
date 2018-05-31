package cn.popo.news.core.controller.api;

import cn.popo.news.core.entity.form.ReplyForm;
import cn.popo.news.core.entity.form.ReplyReportForm;
import cn.popo.news.core.service.api.AgoReplyService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/article")
public class ReplyController {

    @Autowired
    AgoReplyService agoReplyService;


    /**
     * 回复上传
     */
    @ResponseBody
    @PostMapping("/replysave")
    public ResultVO<Map<String,Object>> replysave(@Valid ReplyForm replyForm){
        agoReplyService.replySave(replyForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 回复举报上传
     */
    @ResponseBody
    @PostMapping("/report")
    public ResultVO<Map<String,Object>> replyReportSave(@Valid ReplyReportForm replyReportForm){
        agoReplyService.replyReportSave(replyReportForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }
}
