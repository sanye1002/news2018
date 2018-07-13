package cn.popo.news.core.controller.oa;


import cn.popo.news.core.entity.common.OutLink;
import cn.popo.news.core.entity.param.OutLinkParam;
import cn.popo.news.core.service.OutLinkService;
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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-07-13 下午 5:10
 * @Description description
 */

@Controller
@RequestMapping("/oa/link")
@Slf4j
public class OutLinkController {

    @Autowired
    private OutLinkService outLinkService;

    /**
     * 链接添加
     * @param outLinkParam
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public ResultVO<Map<String,Object>> saveClassify(@Valid OutLinkParam outLinkParam){
        outLinkService.addLink(outLinkParam);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }


    /*@ResponseBody
    @GetMapping("/list")
    public ResultVO<Map<String,Object>> allClassify(Map<String, Object> map,
                                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size){
        PageRequest pageRequest = new PageRequest(page - 1, size, SortTools.basicSort("desc","ranking"));
        Page<OutLink> outLinkPage = outLinkService.findAllOutLink(pageRequest);
        map.put("outLink",outLinkPage.getContent());
        return ResultVOUtil.success(map);
    }*/

    /**
     * 链接添加页面
     * @param map
     * @param id
     * @return
     */
    @GetMapping("/index")
    @RequiresPermissions("link:add")
    public ModelAndView classifyAdd(Map<String, Object> map,
                                    @RequestParam(value = "id", defaultValue = "") Integer id) {
//        Classify classify = new Classify();
        OutLink outLink = new OutLink();
        map.put("pageId", 500);
        if (id != null) {
            map.put("pageTitle", "分类编辑");
            outLink = outLinkService.findOneLine(id);
        } else {
            map.put("pageTitle", "分类添加");
        }
        map.put("outLink", outLink);
        return new ModelAndView("pages/outLinkAdd", map);
    }

    /**
     * 所有链接查询
     * @param map
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("link:list")
    public ModelAndView list(Map<String, Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size,SortTools.basicSort("desc","ranking"));
        Page<OutLink> outLinks = outLinkService.findAllOutLink(pageRequest);
        map.put("pageId", 501);
        map.put("pageTitle", "操作链接列表");
        map.put("pageContent", outLinks);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/link/list.html");
        return new ModelAndView("pages/outLinkList", map);
    }

    /**
     * 链接删除
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public ResultVO<Map<String, Object>> delete(@RequestParam(value = "id") Integer id
    ) {

        Map<String,Object> map  = new HashMap<>();
        outLinkService.delete(id);
        return ResultVOUtil.success(map);
    }
}
