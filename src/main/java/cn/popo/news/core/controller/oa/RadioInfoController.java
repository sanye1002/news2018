package cn.popo.news.core.controller.oa;

import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.RadioAnchorLetterDTO;
import cn.popo.news.core.entity.common.RadioAnchorLetter;
import cn.popo.news.core.entity.common.RadioInfo;
import cn.popo.news.core.entity.form.RadioInfoForm;
import cn.popo.news.core.entity.param.RadioAnchorLetterParam;
import cn.popo.news.core.service.RadioInfoService;
import cn.popo.news.core.utils.GetTimeUtil;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SortTools;
import cn.popo.news.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/oa/radio")
@Slf4j
public class RadioInfoController{

    @Autowired
    private RadioInfoService radioInfoService;

    @Autowired
    private UserSessionUtil userSessionUtil;

    /**
     * 添加电台页面
     * @param map
     * @param id
     * @return
     */
    @GetMapping("/index")
    @RequiresPermissions("radio:add")
    public ModelAndView index(Map<String, Object> map,
                                    @RequestParam(value = "id", defaultValue = "") Integer id) {
//        Classify classify = new Classify();
        RadioInfo radioInfo = new RadioInfo();
        map.put("pageId", 600);
        if (id != null) {
            map.put("pageTitle", "电台编辑");
            radioInfo = radioInfoService.findOneRadio(id);
        } else {
            map.put("pageTitle", "电台添加");
        }
        map.put("radioInfo", radioInfo);
        return new ModelAndView("pages/radioAdd", map);
    }

    /**
     * 添加电台
     * @param radioInfoForm
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public ResultVO<Map<String,Object>> saveRadio(@Valid RadioInfoForm radioInfoForm){
        radioInfoService.addRadio(radioInfoForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }


    /**
     * 电台列表页面
     * @param map
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("radio:list")
    public ModelAndView list(Map<String, Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<RadioInfo> radioInfoPage = radioInfoService.findAllRadioInfo(pageRequest);
        map.put("pageId", 601);
        map.put("pageTitle", "操作电台列表");
        map.put("pageContent", radioInfoPage);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/radio/list.html");
        return new ModelAndView("pages/radioList", map);
    }

    /**
     * 删除电台
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public ResultVO<Map<String, Object>> delete(@RequestParam(value = "id") Integer id
    ) {

        Map<String,Object> map  = new HashMap<>();
        radioInfoService.deleteRadio(id);
        return ResultVOUtil.success(map);
    }


    /**
     * 改变在线状态
     * @param id
     * @param showState
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public ResultVO<Map<String, Object>> updateShowState(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "showState") Integer showState
    ) {

        Map<String,Object> map  = new HashMap<>();
        radioInfoService.updateShowState(id,showState);
        return ResultVOUtil.success(map);
    }

    /**
     * 电台前台列表
     * @param page
     * @param size
     * @return
     */
    @ResponseBody
    @PostMapping("/web/list")
    public ResultVO<Map<String,Object>> radioList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "10") Integer size){
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<RadioInfo> radioInfoPage = radioInfoService.findRadioByShowState(pageRequest,1);
        Map<String,Object> map  = new HashMap<>();
        map.put("radio",radioInfoPage);
        map.put("currentPage", page);
        return ResultVOUtil.success(map);
    }

    /**
     * 添加用户发给电台主播的消息
     * @param radioAnchorLetterParam
     * @return
     */
    @ResponseBody
    @PostMapping("/add/letter")
    public ResultVO<Map<String,Object>> addLetter(@Valid RadioAnchorLetterParam radioAnchorLetterParam,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response
    ){

        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }
        Map<String,Object> map  = new HashMap<>();
        radioInfoService.addRadioAnchorLetter(radioAnchorLetterParam);
        return ResultVOUtil.success(map);
    }


    @GetMapping("/list/letter")
    @RequiresPermissions("radio:letterList")
    public ModelAndView letterList(Map<String, Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                               @RequestParam(value = "month",defaultValue = "0") Integer month,
                               @RequestParam(value = "day",defaultValue = "0") Integer day,
                               @RequestParam(value = "year",defaultValue = "0") Integer year,
                               @RequestParam(value = "radioId",defaultValue = "0") Integer radioId
                                   ) {

        if (day==0&&month==0&&year==0){
            day = GetTimeUtil.getNowDay();
            month = GetTimeUtil.getNowMonth();
            year = GetTimeUtil.getNowYear();
        }

        List<RadioInfo> list = radioInfoService.findAllRadioInfo();
        PageRequest pageRequest = new PageRequest(page - 1, size,SortTools.basicSort("desc","time"));
        PageDTO<RadioAnchorLetterDTO> radioInfoPage = radioInfoService.findAllByTimeAndRadioId(
                pageRequest,GetTimeUtil.getZeroDateFormat(GetTimeUtil.getYearMonthDay(day,month,year)),radioId);

        List<Integer> manyYear = new ArrayList<>();
        List<Integer> manyDay = new ArrayList<>();
        for (int i=0;i<10;i++){
            manyYear.add(2018+i);
        }

        Integer maxDay = GetTimeUtil.getMaxDayByYearMonth(year,month);

        for (int d=0;d<maxDay;d++){
            manyDay.add(d+1);
        }


        map.put("manyYear",manyYear);
        map.put("manyDay",manyDay);
        map.put("pageId", 602);
        map.put("pageTitle", "操作电台列表");
        map.put("pageContent", radioInfoPage);
        map.put("day",day);
        map.put("month",month);
        map.put("year",year);
        map.put("radioId",radioId);
        map.put("size", size);
        map.put("radioList",list);
        map.put("currentPage", page);
        map.put("url", "/oa/radio/list/letter.html");
        return new ModelAndView("pages/radioLetter", map);
    }
}
