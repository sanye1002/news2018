package cn.popo.news.core.controller.oa;

import cn.popo.news.common.utils.GetMaxUtil;
import cn.popo.news.common.utils.StatisticsInfoGetUtil;
import cn.popo.news.core.dto.AuthorDTO;
import cn.popo.news.core.dto.IpDataDTO;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.IpTime;
import cn.popo.news.core.entity.common.User;
import cn.popo.news.core.service.ArticleService;
import cn.popo.news.core.service.IPStatisticsService;
import cn.popo.news.core.service.UserService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SortTools;
import cn.popo.news.core.utils.SplitUtil;
import cn.popo.news.core.vo.ResultVO;
import com.alibaba.fastjson.JSON;
import com.qq.connect.api.qzone.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.Get;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
                              @RequestParam(value = "nowDay",defaultValue = "1") Integer nowDay,
                              @RequestParam(value = "year",defaultValue = "0") Integer year
    ){
        List<Integer> list = new ArrayList<>();
        Integer day = 7;

        if (month==0) {
            month = GetTimeUtil.getNowMonth();
        }
        if (year==GetTimeUtil.getNowYear()||year==0){
            if (month==GetTimeUtil.getNowMonth()||month==0){
                day = GetTimeUtil.getNowDay();
                nowDay = GetTimeUtil.getNowDay();
            }
        }



        if (year==0){
            year = GetTimeUtil.getNowYear();
        }

        List<String> dayList = new ArrayList<>();




        //访问地址统计
        Map<String,Object> address = ipStatisticsService.findAddressNum(
                GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(nowDay,month,year)));

        map.put("addressKey",JSON.toJSONString(address.get("key")));
        map.put("addressCount",JSON.toJSONString(address.get("count")));

        //访问工具统计
        Map<String,Object> util = ipStatisticsService.findUtilNum(
                GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(nowDay,month,year)));

        map.put("utilKey",JSON.toJSONString(util.get("key")));
        map.put("utilCount",JSON.toJSONString(util.get("count")));

        //访问浏览器内核统计
        Map<String,Object> browser = ipStatisticsService.findBrowserNum(
                GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(nowDay,month,year)));

        map.put("browserKey",JSON.toJSONString(browser.get("key")));
        map.put("browserCount",JSON.toJSONString(browser.get("count")));

        //ip访问数据表
        /*PageRequest pageRequest = new PageRequest(page-1,size, SortTools.basicSort("desc","fullTime"));
        PageDTO<IpDataDTO> ipTimes = ipStatisticsService.findIpInfoByDay(pageRequest,GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(nowDay,month,year)));
        map.put("pageContent",ipTimes);*/

        if (month<GetTimeUtil.getNowMonth()){
            day = GetTimeUtil.getMaxDayByYearMonth(year,month);
        }

        //ip访问总量
        for(int i=1;i<day+1;i++){
            Integer num = ipStatisticsService.findDayCount(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(i,month,year)));
            list.add(num);
            dayList.add(i+"号");
        }

        Integer max = Collections.max(list);
        max = GetMaxUtil.maxValue(max);
        map.put("max",max);
        map.put("nowDay",nowDay);
        map.put("month",month);
        map.put("year",year);
        map.put("dayList",JSON.toJSONString(dayList));
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
        List<String> dayList = new ArrayList<>();
        Integer day = 7;

        if (year==GetTimeUtil.getNowYear()||year==0){
            if (month==GetTimeUtil.getNowMonth()||month==0){
                day = GetTimeUtil.getNowDay();
            }
        }

        if (month==0) {
            month = GetTimeUtil.getNowMonth();
        }

        if (year==0){
            year = GetTimeUtil.getNowYear();
        }

        for(int i=1;i<day+1;i++){
            Integer num = ipStatisticsService.findArticleIssueNumByDay(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(i,month,year)),type);
            list.add(num);
            dayList.add(i+"号");
        }

        List<AuthorDTO> authorDTOList = userService.findAllAuthor();

        Integer max = Collections.max(list);
        max = GetMaxUtil.maxValue(max);
        map.put("max",max);
        map.put("month",month);
        map.put("year",year);
        map.put("dayList",JSON.toJSONString(dayList));
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
        List<String> dayList = new ArrayList<>();
        Integer day = 7;

        if (year==GetTimeUtil.getNowYear()||year==0){
            if (month==GetTimeUtil.getNowMonth()||month==0){
                day = GetTimeUtil.getNowDay();
            }
        }

        if (month==0) {
            month = GetTimeUtil.getNowMonth();
        }

        if (year==0){
            year = GetTimeUtil.getNowYear();
        }

        if (len==0) {
            len = 7;
        }

        for(int i=1;i<day+1;i++){
            Integer num = ipStatisticsService.findArticleAuditNumByDay(GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(i,month,year)),auditState,type);
            list.add(num);
            dayList.add(i+"号");
        }

        Integer max = Collections.max(list);
        max = GetMaxUtil.maxValue(max);
        map.put("max",max);
        map.put("month",month);
        map.put("year",year);
        map.put("dayList",JSON.toJSONString(dayList));
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


    @GetMapping("/ip/list")
    @RequiresPermissions("ipChartTable:list")
    public ModelAndView fullIpData(
            @RequestParam(value = "day",defaultValue = "0") Integer day,
            @RequestParam(value = "month",defaultValue = "0") Integer month,
            @RequestParam(value = "size",defaultValue = "12") Integer size,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "onValue",defaultValue = "0") String onValue,
            @RequestParam(value = "table",defaultValue = "0") String table,
            @RequestParam(value = "year",defaultValue = "0") Integer year
    ){

        if (day==0&&month==0&&year==0){
            day = GetTimeUtil.getNowDay();
            month = GetTimeUtil.getNowMonth();
            year = GetTimeUtil.getNowYear();
        }

        Map<String,Object> map  = new HashMap<>();
        PageRequest pageRequest = new PageRequest(page-1,size, SortTools.basicSort("desc","fullTime"));
        PageDTO<IpDataDTO> ipTimes = new PageDTO<>();
        if ("0".equals(table)){
            ipTimes = ipStatisticsService.findIpInfoByDay(pageRequest,GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)));
        }
        if ("address".equals(table)){
            ipTimes = ipStatisticsService.findIpInfoByDayAndAddress(pageRequest,GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)),onValue);
        }
        if ("browser".equals(table)){

            ipTimes = ipStatisticsService.findIpInfoByDayAndBrowser(pageRequest,GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)),onValue);
        }
        if ("util".equals(table)){
            ipTimes = ipStatisticsService.findIpInfoByDayAndUtil(pageRequest,GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)),onValue);

        }


        //访问地址统计
        Map<String,Object> address = ipStatisticsService.findAddressNum(
                GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)));

        map.put("addressKey",address.get("key"));
        map.put("addressCount",address.get("count"));
        map.put("addressAll",address.get("addressAll"));

        //访问工具统计
        Map<String,Object> util = ipStatisticsService.findUtilNum(
                GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)));

        map.put("utilKey",util.get("key"));
        map.put("utilCount",util.get("count"));
        map.put("utilAll",util.get("utilAll"));

        //访问浏览器内核统计
        Map<String,Object> browser = ipStatisticsService.findBrowserNum(
                GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)));

        map.put("browserKey",browser.get("key"));
        map.put("browserCount",browser.get("count"));
        map.put("browserAll",browser.get("browserAll"));

        List<Integer> manyYear = new ArrayList<>();
        List<Integer> manyDay = new ArrayList<>();
        for (int i=0;i<10;i++){
            manyYear.add(2018+i);
        }

        Integer maxDay = GetTimeUtil.getMaxDayByYearMonth(year,month);

        for (int d=0;d<maxDay;d++){
            manyDay.add(d+1);
        }


        map.put("pageContent",ipTimes);
        map.put("manyYear",manyYear);
        map.put("manyDay",manyDay);
        map.put("day",day);
        map.put("month",month);
        map.put("year",year);
        map.put("size",size);
        map.put("page",page);
        map.put("onValue",onValue);
        map.put("table",table);
        map.put("pageId",1005);
        map.put("pageTitle","ip访问数据图表");
        map.put("currentPage", page);
        map.put("url", "/oa/chart/ip/list.html");
        return new ModelAndView("pages/chart-ip-table",map);
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

    /*@ResponseBody
    @GetMapping("/browser")
    public ResultVO<Map<String, String>> address(HttpServletRequest httpServletRequest
    ){
        Map<String,Object> map  = new HashMap<>();
        StatisticsInfoGetUtil.getSystemAndBrowser(httpServletRequest);
        System.out.println(StatisticsInfoGetUtil.getVisitUitl(httpServletRequest));
        return ResultVOUtil.success(map);
    }*/

    @ResponseBody
    @PostMapping("/address")
    public ResultVO<Map<String, String>> address(@RequestParam(value = "month") Integer month,
                                                 @RequestParam(value = "day") Integer day,
                                                 @RequestParam(value = "year") Integer year
    ){
        Map<String,Object> map  = new HashMap<>();
        //访问地址统计
        Map<String,Object> address = ipStatisticsService.findAddressNum(
                GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)));

        map.put("addressKey",address.get("key"));
        map.put("addressCount",address.get("count"));
        map.put("nowDay",day);

        return ResultVOUtil.success(map);
    }

    @ResponseBody
    @PostMapping("/browser")
    public ResultVO<Map<String, String>> browser(@RequestParam(value = "month") Integer month,
                                                 @RequestParam(value = "day") Integer day,
                                                 @RequestParam(value = "year") Integer year
    ){
        Map<String,Object> map  = new HashMap<>();
        //访问浏览器内核统计
        Map<String,Object> browser = ipStatisticsService.findBrowserNum(
                GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)));

        map.put("browserKey",browser.get("key"));
        map.put("browserCount",browser.get("count"));
        map.put("nowDay",day);

        return ResultVOUtil.success(map);
    }

    @ResponseBody
    @PostMapping("/util")
    public ResultVO<Map<String, String>> util(@RequestParam(value = "month") Integer month,
                                                 @RequestParam(value = "day") Integer day,
                                                 @RequestParam(value = "year") Integer year
    ){
        Map<String,Object> map  = new HashMap<>();
        //访问工具统计
        Map<String,Object> util = ipStatisticsService.findUtilNum(
                GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)));

        map.put("utilKey",util.get("key"));
        map.put("utilCount",util.get("count"));
        map.put("nowDay",day);

        return ResultVOUtil.success(map);
    }


}
