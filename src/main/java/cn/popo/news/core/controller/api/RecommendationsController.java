package cn.popo.news.core.controller.api;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.entity.common.ArticleInfo;
import cn.popo.news.core.service.api.AgoRecommendationsService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/today")
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


}
