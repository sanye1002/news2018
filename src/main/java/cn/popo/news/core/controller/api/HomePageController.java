package cn.popo.news.core.controller.api;

import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.service.ClassifyService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/5/31 10:31
 * @Desc    首页控制
 */

@Controller
@RequestMapping("/homePage")
public class HomePageController {

    @Autowired
    ClassifyService classifyService;

    /**
     * @prama null
     *
     * @return List<Classify>
     *
     */
    @GetMapping("/nav")
    public ResultVO<Map<String,Object>> nav(){
        Map<String,Object> map  = new HashMap<>();
        List<Classify> list = classifyService.findAllClassify();
        map.put("classify",list);
        return ResultVOUtil.success(map);
    }
}
