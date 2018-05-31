package cn.popo.news.core.controller.api;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.IndexVO;
import cn.popo.news.core.dto.api.SearchVO;
import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.service.ArticleService;
import cn.popo.news.core.service.ClassifyService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/5/31 10:31
 * @Desc    首页控制控制中心
 */

@Controller
@RequestMapping("/api/homePage")
public class HomePageController {

    @Autowired
    ClassifyService classifyService;

    @Autowired
    ArticleService articleService;

    private static final Integer ZERO = 0;
    private static final Integer ONE = 1;
//    private static final Integer TWO = 2;
//    private static final Integer THREE = 3;


    /**
     * @param content
     * @return List<SearchVO>
     * @desc 首页搜索
     */
    @PostMapping("/search")
    @ResponseBody
    public ResultVO<Map<String,Object>> search(Map<String,Object> map,
                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "12") Integer size,
                                               @RequestParam(value = "content") String content
                                                                                        ){
        PageRequest pageRequest = new PageRequest(page-1,size);
        PageDTO<SearchVO> pageDTO = articleService.findArticleTitleLikeAndStateAndShowStateAndDraft(pageRequest,ONE,content,ONE,ZERO);

        map.put("size", size);
        map.put("currentPage", page);
        map.put("pageContent", pageDTO);
        return ResultVOUtil.success(map);
    }


    /**
     * @param
     * @return
     * @desc 首页数据
     */
    @PostMapping("/index")
    @ResponseBody
    public ResultVO<Map<String,Object>> index(Map<String,Object> map,
                                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "size", defaultValue = "12") Integer size){
        //导航
        List<Classify> list = classifyService.findAllClassify();
        map.put("nav",list);
        //文章
        PageRequest pageRequest = new PageRequest(page-1,size);
        PageDTO<IndexVO> pageDTO = articleService.findAllArticleByShowStateAndStateAndDraft(pageRequest,ONE,ONE,ZERO);
        map.put("size", size);
        map.put("currentPage", page);
        map.put("pageContent", pageDTO);
        return ResultVOUtil.success(map);
    }






}
