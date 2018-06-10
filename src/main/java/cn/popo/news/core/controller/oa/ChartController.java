package cn.popo.news.core.controller.oa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/6/10 13:22
 * @Desc    图表控制中心
 */

@Slf4j
@Controller
@RequestMapping("/oa/chart")
public class ChartController {
    /**
     * 文章举报展示
     */
    @GetMapping("/index")
    public ModelAndView index(Map<String,Object> map
    ){

        map.put("pageId",1000);
        map.put("pageTitle","数据图表");
        return new ModelAndView("pages/chart",map);
    }
}
