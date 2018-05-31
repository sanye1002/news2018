package cn.popo.news.core.controller.oa;

import cn.popo.news.core.entity.form.ReplyForm;
import cn.popo.news.core.service.ReplyService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/5/29 14:09
 * @Desc    回复控制中心
 */

@Controller
@RequestMapping("/oa/reply")
@Slf4j
public class ReplyController {
    @Autowired
    ReplyService replyService;



}
