package cn.popo.news.core.controller.oa;

import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.entity.common.Permission;
import cn.popo.news.core.repository.ArticleRepository;
import cn.popo.news.core.repository.ClassifyRepository;
import cn.popo.news.core.service.ArticleService;
import cn.popo.news.core.service.impl.ClassifyServiceImpl;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/5/22 18:42
 * @Desc
 */

@Controller
@RequestMapping("/oa/classify")
@Slf4j
public class ClassifyController {
    @Autowired
    private ClassifyServiceImpl classifyService;
    @Autowired
    private ArticleService articleService;

    /**
     * 分类列表显示
     */
    @ResponseBody
    @GetMapping("/list")
    public ResultVO<Map<String,Object>> allClassify(){
        Map<String,Object> map  = new HashMap<>();
        List<Classify> list = classifyService.findAllClassify();
        map.put("message","success");
        map.put("classify",list);
        return ResultVOUtil.success(map);
    }

    /**
     * 分类添加
     */
    @ResponseBody
    @PostMapping("/save")
    public ResultVO<Map<String,Object>> saveClassify(@RequestParam(value = "classify", defaultValue = "0") String classify,
                                                        @RequestParam(value = "id", defaultValue = "") Integer id){
        classifyService.saveClassify(classify,id);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 分类添加页面
     */
    @GetMapping("/index")
    @RequiresPermissions("classify:add")
    public ModelAndView classifyAdd(Map<String, Object> map,
                                        @RequestParam(value = "id", defaultValue = "") Integer id) {
        Classify classify = new Classify();
        map.put("pageId", 33);
        if (id != null) {
            map.put("pageTitle", "分类编辑");
            classify = classifyService.findClassifyById(id);
        } else {
            map.put("pageTitle", "分类添加");
        }
        map.put("classify", classify);
        return new ModelAndView("pages/classifyAdd", map);
    }

    /**
     * 所有分类展示
     */
    @GetMapping("/alllist")
    @RequiresPermissions("classify:list")
    public ModelAndView list(Map<String, Object> map,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<Classify> classifies = classifyService.findClassify(pageRequest);
        map.put("pageId", 34);
        map.put("pageTitle", "操作分类列表");
        map.put("pageContent", classifies);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("url", "/oa/classify/alllist.html");
        return new ModelAndView("pages/classifyList", map);
    }

    /**
     * 分类删除
     */
    @ResponseBody
    @PostMapping("/delete")
    public ResultVO<Map<String, Object>> delete(@RequestParam(value = "id") Integer id
    ) {

        Map<String,Object> map  = new HashMap<>();
        Boolean flag = articleService.findArticleByClassifyId(id);

        if (flag){
            classifyService.deleteClassify(id);
        }

        map.put("mmp",flag);
        return ResultVOUtil.success(map);
    }
}
