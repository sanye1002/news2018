package cn.popo.news.core.controller.oa;

import cn.popo.news.core.entity.common.RadioInfo;
import cn.popo.news.core.entity.form.RadioInfoForm;
import cn.popo.news.core.service.RadioInfoService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/oa/radio")
@Slf4j
public class RadioInfoController {

    @Autowired
    private RadioInfoService radioInfoService;

    @GetMapping("/index")
    @RequiresPermissions("radio:add")
    public ModelAndView classifyAdd(Map<String, Object> map,
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

    @ResponseBody
    @PostMapping("/save")
    public ResultVO<Map<String,Object>> saveLine(@Valid RadioInfoForm radioInfoForm){
        radioInfoService.addRadio(radioInfoForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }


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

    @ResponseBody
    @PostMapping("/delete")
    public ResultVO<Map<String, Object>> delete(@RequestParam(value = "id") Integer id
    ) {

        Map<String,Object> map  = new HashMap<>();
        radioInfoService.deleteRadio(id);
        return ResultVOUtil.success(map);
    }

    @ResponseBody
    @PostMapping("/web/list")
    public ResultVO<Map<String,Object>> lineList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "10") Integer size){
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<RadioInfo> radioInfoPage = radioInfoService.findRadioByShowState(pageRequest,1);
        Map<String,Object> map  = new HashMap<>();
        map.put("radio",radioInfoPage);
        map.put("currentPage", page);
        return ResultVOUtil.success(map);
    }
}
