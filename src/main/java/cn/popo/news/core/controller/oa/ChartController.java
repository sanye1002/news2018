package cn.popo.news.core.controller.oa;

import cn.popo.news.core.dto.api.ReplyVO;
import cn.popo.news.core.service.IPStatisticsService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private IPStatisticsService ipStatisticsService;

    /**
     * 文章举报展示
     */
    @GetMapping("/index")
    public ModelAndView index(Map<String,Object> map,
                              @RequestParam(value = "month",defaultValue = "1") Integer month
    ){

        List<Integer> list = new ArrayList<>();
        for(int i=1;i<8;i++){
            list.add(ipStatisticsService.findDayCount(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getMonthDay(i,month))));
        }

        map.put("month",month);
        map.put("list",JSON.toJSONString(list));
        map.put("pageId",1000);
        map.put("pageTitle","数据图表");

        return new ModelAndView("pages/chart",map);
    }

    /**
     * 文章举报展示
     */
    @ResponseBody
    @PostMapping("/add/day")
    public ResultVO<Map<String, String>> addDay(
            @RequestParam(value = "day") Integer day,
            @RequestParam(value = "month") Integer month
    ){
        Map<String,Object> map  = new HashMap<>();
        Integer num = ipStatisticsService.findDayCount(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getMonthDay(day,month)));
        map.put("dayValue",num);
        return ResultVOUtil.success(map);
    }


}
