package cn.popo.news.core.controller.api;

import cn.popo.news.common.utils.HttpClientUtil;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.service.api.AgoRecommendationsService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultJsonVO;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author zhaoxiang
 * @Date 2018/11/14
 * @Desc
 */
@RestController
@RequestMapping("/api/recommendations")
public class RecommendationsController {
    @Autowired
    private AgoRecommendationsService service;

    @PostMapping("/today")
    public ResultVO<Map<String,Object>> findTodayArticle(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                                           @RequestParam(value = "size",defaultValue = "10")Integer size,
                                                           @RequestParam(value = "userId")String userId,
                                                           @RequestParam(value = "day",defaultValue = "0")Integer day,
                                                         Map<String,Object> map){
        Pageable pageable = new PageRequest(page-1,size);
        PageDTO<ArticleVO> pageDTO = service.findTodayRecommendArticle(pageable,userId,day);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }
    @PostMapping("/start")
    public ResultJsonVO start(@RequestParam(value = "userId")String userId,
                              Map<String,Object> map){
        String url = "http://news.immnc.com:8899/arithmetic/start";
        String param = "userId="+userId;
        return HttpClientUtil.sendPost(url,param);
    }



}
