package cn.popo.news.core.controller.oa;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.entity.common.Logo;
import cn.popo.news.core.service.LogoService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/6/13 18:08
 * @Desc    logo控制中心
 */

@Controller
@RequestMapping("/oa/logo")
@Slf4j
public class LogoController {
    @Autowired
    private LogoService logoService;

    /**
     * logo上传
     */
    @ResponseBody
    @PostMapping("/save")
    public ResultVO<Map<String,Object>> saveLogo(@RequestParam(value = "logoUrl") String logoUrl,
                                                     @RequestParam(value = "showState") Integer showState){
        logoService.saveLogo(logoUrl,showState);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * logo上传问题
     */
    @GetMapping("/save/index")
    public ModelAndView logoAdd(Map<String, Object> map) {
        map.put("pageId", 111);
        map.put("pageTitle", "logo上传");
        return new ModelAndView("pages/logo-issue", map);
    }

    /**
     * logo列表
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        PageDTO<Logo> logos = logoService.findAllLogo(pageRequest);
        map.put("pageId", 112);
        map.put("pageTitle", "logo列表");
        map.put("pageContent", logos);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/logo/list.html");
        return new ModelAndView("pages/logo-list", map);
    }

}
