package cn.popo.news.core.controller.api;


import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/api/article")
public class ArticleDetailsController {

    /**
     * @param  "articleId"
     * @return
     * @desc 文章详情数据
     */
    @PostMapping("/details")
    @ResponseBody
    public ResultVO<Map<String,Object>> articleDetails(Map<String,Object> map){


        return ResultVOUtil.success(map);
    }
}
