package cn.popo.news.core.controller.oa;

import cn.popo.news.core.service.ArticleService;
import cn.popo.news.core.service.IPStatisticsService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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

    @Autowired
    private ArticleService articleService;


    /**
     * ip访问量
     */
    @GetMapping("/ip")
    @RequiresPermissions("ipChart:list")
    public ModelAndView index(Map<String,Object> map,
                              @RequestParam(value = "month",defaultValue = "1") Integer month
    ){


        List<Integer> list = new ArrayList<>();

        for(int i=1;i<8;i++){
            Integer num = ipStatisticsService.findDayCount(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getMonthDay(i,month)));
            list.add(num);
        }

       /* Integer max = Collections.max(list);
        max = GetMaxUtil.maxValue(max);*/
        map.put("month",month);
        map.put("list",JSON.toJSONString(list));
        map.put("pageId",1000);
        map.put("pageTitle","ip访问量统计");

        return new ModelAndView("pages/chart-ip",map);
    }

    /**
     * 文章发布统计
     */
    @GetMapping("/issue")
    public ModelAndView ArticleIssueChart(Map<String,Object> map,
                              @RequestParam(value = "month",defaultValue = "1") Integer month
    ){


        /*List<Integer> list = new ArrayList<>();

        for(int i=1;i<8;i++){
            Integer num = ipStatisticsService.findArticleIssueNumByDay(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getMonthDay(i,month)));
            list.add(num);
        }

       *//* Integer max = Collections.max(list);
        max = GetMaxUtil.maxValue(max);*//*
        map.put("month",month);
        map.put("list",JSON.toJSONString(list));
        map.put("pageId",1001);
        map.put("pageTitle","文章发布统计");*/
        System.out.println(123);

        return new ModelAndView("pages/chart-issue",map);
    }

    /**
     * 文章审核统计
     */
    @GetMapping("/audit")
    public ModelAndView ArticleAuditChart(Map<String,Object> map,
                                          @RequestParam(value = "month",defaultValue = "1") Integer month,
                                          @RequestParam(value = "auditState",defaultValue = "100") Integer auditState
    ){


        List<Integer> list = new ArrayList<>();

        for(int i=1;i<8;i++){
            Integer num = ipStatisticsService.findArticleAuditNumByDay(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getMonthDay(i,month)),auditState);
            list.add(num);
        }

       /* Integer max = Collections.max(list);
        max = GetMaxUtil.maxValue(max);*/
        map.put("month",month);
        map.put("list",JSON.toJSONString(list));
        map.put("pageId",1002);
        map.put("pageTitle","文章审核统计");

        return new ModelAndView("pages/chart-audit",map);
    }

    /**
     * 增加天数
     */
    @ResponseBody
    @PostMapping("/add/day")
    public ResultVO<Map<String, String>> addDay(
            @RequestParam(value = "day") Integer day,
            @RequestParam(value = "month") Integer month
    ){
        Map<String,Object> map  = new HashMap<>();
//        System.out.println(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getMonthDay(day,month)));
        Integer num = ipStatisticsService.findDayCount(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getMonthDay(day,month)));
        /*if (max<num){
            System.out.println(num);
           max = GetMaxUtil.maxValue(num);
            System.out.println(max);
        }
        map.put("max",max);*/
        map.put("dayValue",num);
        return ResultVOUtil.success(map);
    }

    @ResponseBody
    @GetMapping("/address")
    public ResultVO<Map<String, String>> address(
            @RequestParam(value = "a") Integer a,
            @RequestParam(value = "b") Integer b
    ){
        Map<String,Object> map  = new HashMap<>();
        ipStatisticsService.findAfterIp(a,b);
        return ResultVOUtil.success(map);
    }


}
