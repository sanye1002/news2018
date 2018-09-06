package cn.popo.news.core.controller.seo.mobile;

import cn.popo.news.core.dto.api.ArticleDetailsVO;
import cn.popo.news.core.service.api.AgoArticleService;
import cn.popo.news.core.utils.SEOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author zhaoxiang
 * @Date 2018/09/06
 * @Desc
 */
@Controller
@RequestMapping("/mobile")
public class DetailController {
    @Autowired
    private AgoArticleService articleService;
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam(value = "id")String id, Map<String,Object> map){
        ArticleDetailsVO articleDetailsVO = articleService.findArticleDetails(id,"");
        map.put("title",articleDetailsVO.getTitle());
        map.put("keywords",SEOUtil.getKeywordsByList(articleDetailsVO.getKeywordList()));
        map.put("description",SEOUtil.getDescription(articleDetailsVO.getContent()));
        map.put("content",articleDetailsVO.getContent());
        map.put("url","https://m.immnc.com/imgText?articleId="+id+"&sort=详情");
        return new ModelAndView("seo/mobile/page/detail",map);
    }
}
