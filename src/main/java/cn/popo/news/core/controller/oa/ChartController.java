package cn.popo.news.core.controller.oa;

import cn.popo.news.common.utils.GetMaxUtil;
import cn.popo.news.core.dto.AuthorDTO;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.service.ArticleService;
import cn.popo.news.core.service.IPStatisticsService;
import cn.popo.news.core.service.UserService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SplitUtil;
import cn.popo.news.core.vo.ResultVO;
import com.alibaba.fastjson.JSON;
import com.qq.connect.api.qzone.UserInfo;
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

    @Autowired
    private UserService userService;


    /**
     * ip访问量
     */
    @GetMapping("/ip")
    @RequiresPermissions("ipChart:list")
    public ModelAndView index(Map<String,Object> map,
                              @RequestParam(value = "month",defaultValue = "0") Integer month,
                              @RequestParam(value = "year",defaultValue = "0") Integer year
    ){
        List<Integer> list = new ArrayList<>();

        if (month==0) {
            month = GetTimeUtil.getNowMonth();
        }

        if (year==0){
            year = GetTimeUtil.getNowYear();
        }

        for(int i=1;i<8;i++){
            Integer num = ipStatisticsService.findDayCount(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(i,month,year)));
            list.add(num);
        }


        Integer max = Collections.max(list);
        max = GetMaxUtil.maxValue(max);
        map.put("max",max);
        map.put("month",month);
        map.put("year",year);
        map.put("list",JSON.toJSONString(list));
        map.put("pageId",1000);
        map.put("pageTitle",month+"月ip访问量统计");

        return new ModelAndView("pages/chart-ip",map);
    }

    /**
     * 文章发布统计
     */
    @GetMapping("/issue")
    public ModelAndView ArticleIssueChart(Map<String,Object> map,
                              @RequestParam(value = "month",defaultValue = "0") Integer month,
                              @RequestParam(value = "type",defaultValue = "100") Integer type,
                              @RequestParam(value = "year",defaultValue = "0") Integer year
    ){


        List<Integer> list = new ArrayList<>();

        if (month==0) {
            month = GetTimeUtil.getNowMonth();
        }

        if (year==0){
            year = GetTimeUtil.getNowYear();
        }

        for(int i=1;i<8;i++){
            Integer num = ipStatisticsService.findArticleIssueNumByDay(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(i,month,year)),type);
            list.add(num);
        }

        List<AuthorDTO> authorDTOList = userService.findAllAuthor();

        Integer max = Collections.max(list);
        max = GetMaxUtil.maxValue(max);
        map.put("max",max);
        map.put("month",month);
        map.put("year",year);
        map.put("author",authorDTOList);
        map.put("list",JSON.toJSONString(list));
        map.put("pageId",1001);
        map.put("pageTitle","文章发布统计");

        return new ModelAndView("pages/chart-issue",map);
    }

    /**
     * 用户文章发布统计
     */

    @ResponseBody
    @PostMapping("/issue/user")
    public ResultVO<Map<String, String>> ArticleIssueUserChart(Map<String,Object> map,
                                          @RequestParam(value = "month",defaultValue = "0") Integer month,
                                          @RequestParam(value = "type",defaultValue = "100") Integer type,
                                          @RequestParam(value = "year",defaultValue = "0") Integer year,
                                          @RequestParam(value = "len",defaultValue = "0") Integer len,
                                          @RequestParam(value = "userId") String userId
    ){


        List<Integer> list = new ArrayList<>();

        if (month==0) {
            month = GetTimeUtil.getNowMonth();
        }

        if (year==0){
            year = GetTimeUtil.getNowYear();
        }

        if (len==0) {
            len = 7;
        }

        for(int i=1;i<len+1;i++){
            Integer num = ipStatisticsService.findUserIssueNumByDay(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(i,month,year)),userId,type);
            list.add(num);
        }

        User user = userService.findOne(userId);

        Integer max = Collections.max(list);
        max = GetMaxUtil.maxValue(max);
        map.put("max",max);
        map.put("month",month);
        map.put("year",year);
        map.put("name",user.getName());
        map.put("userId",user.getUserId());
        map.put("list",list);

        return ResultVOUtil.success(map);
    }

    /**
     * 文章审核统计
     */
    @GetMapping("/audit")
    public ModelAndView ArticleAuditChart(Map<String,Object> map,
                                          @RequestParam(value = "month",defaultValue = "0") Integer month,
                                          @RequestParam(value = "auditState",defaultValue = "2") Integer auditState,
                                          @RequestParam(value = "type",defaultValue = "100") Integer type,
                                          @RequestParam(value = "len",defaultValue = "0") Integer len,
                                          @RequestParam(value = "year",defaultValue = "0") Integer year
    ){


        List<Integer> list = new ArrayList<>();

        if (month==0) {
            month = GetTimeUtil.getNowMonth();
        }

        if (year==0){
            year = GetTimeUtil.getNowYear();
        }

        if (len==0) {
            len = 7;
        }

        for(int i=1;i<len+1;i++){
            Integer num = ipStatisticsService.findArticleAuditNumByDay(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(i,month,year)),auditState,type);
            list.add(num);
        }

        Integer max = Collections.max(list);
        max = GetMaxUtil.maxValue(max);
        map.put("max",max);
        map.put("month",month);
        map.put("year",year);
        map.put("list",JSON.toJSONString(list));
        map.put("pageId",1002);
        map.put("pageTitle","文章审核统计");

        return new ModelAndView("pages/chart-audit",map);
    }

    /**
     * 增加天数
     * @param day
     * @param month
     * @return
     */
    @ResponseBody
    @PostMapping("/add/day")
    public ResultVO<Map<String, String>> addDay(
            @RequestParam(value = "day") Integer day,
            @RequestParam(value = "month") Integer month,
            @RequestParam(value = "max") Integer max,
            @RequestParam(value = "year") Integer year
    ){

        Map<String,Object> map  = new HashMap<>();
        Integer num = ipStatisticsService.findDayCount(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)));
        if (max<num){
           max = GetMaxUtil.maxValue(num);
        }
        map.put("max",max);
        map.put("dayValue",num);
        return ResultVOUtil.success(map);
    }

    /**
     * 增加天数
     * @param day
     * @param month
     * @param type
     * @return
     */
    @ResponseBody
    @PostMapping("/add/day/issue")
    public ResultVO<Map<String, String>> addIssueDay(
            @RequestParam(value = "day") Integer day,
            @RequestParam(value = "month") Integer month,
            @RequestParam(value = "max") Integer max,
            @RequestParam(value = "type",defaultValue = "100") Integer type,
            @RequestParam(value = "year") Integer year,
            @RequestParam(value = "dataStr") String dataStr
    ){

        Map<String,Object> map  = new HashMap<>();
        List<String> list = SplitUtil.splitComme(dataStr);
        List<Integer> listNum = new ArrayList<>();
        list.forEach(l->{
            if("1".equals(l)){
                listNum.add(ipStatisticsService.findArticleIssueNumByDay(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)),type));
            }else {
                listNum.add(ipStatisticsService.findUserIssueNumByDay(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)),l,type));
            }
        });
//        Integer num = ipStatisticsService.findArticleIssueNumByDay(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)),type);
       Integer num = Collections.max(listNum);
        if (max<num){
            max = GetMaxUtil.maxValue(num);
        }
        map.put("max",max);
        map.put("dayValue",listNum);
        return ResultVOUtil.success(map);
    }

    /**
     * 增加天数
     * @param day
     * @param month
     * @param auditState
     * @param type
     * @return
     */
    @ResponseBody
    @PostMapping("/add/day/audit")
    public ResultVO<Map<String, String>> addAuditDay(
            @RequestParam(value = "day") Integer day,
            @RequestParam(value = "month") Integer month,
            @RequestParam(value = "max") Integer max,
            @RequestParam(value = "auditState",defaultValue = "2") Integer auditState,
            @RequestParam(value = "type",defaultValue = "100") Integer type,
            @RequestParam(value = "year") Integer year
    ){

        Map<String,Object> map  = new HashMap<>();
        Integer num = ipStatisticsService.findArticleAuditNumByDay(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)),auditState,type);
        if (max<num){
            max = GetMaxUtil.maxValue(num);
        }
        map.put("max",max);
        map.put("dayValue",num);
        return ResultVOUtil.success(map);
    }

    /*@ResponseBody
    @GetMapping("/address")
    public ResultVO<Map<String, String>> address(
            @RequestParam(value = "a") Integer a,
            @RequestParam(value = "b") Integer b
    ){
        Map<String,Object> map  = new HashMap<>();
        ipStatisticsService.findAfterIp(a,b);
        return ResultVOUtil.success(map);
    }*/


}
