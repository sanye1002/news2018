package cn.popo.news.core.controller.api;


import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleDetailsVO;
import cn.popo.news.core.dto.api.CommentVO;
import cn.popo.news.core.entity.form.ReprotInfoForm;
import cn.popo.news.core.service.api.AgoArticleService;
import cn.popo.news.core.service.api.AgoCommentService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author  Administrator
 * @Date    2018/5/31 18:22
 * @Desc    前台文章详情页面控制中心
 */

@Controller
@RequestMapping("/api/article")
public class ArticleDetailsController {

    @Autowired
    AgoCommentService agoCommentService;

    @Autowired
    AgoArticleService agoArticleService;

    private static final Integer ZERO = 0;
    private static final Integer ONE = 1;

    /**
     * @param  "articleId"
     * @return
     * @desc 文章详情数据
     */
    @PostMapping("/details")
    @ResponseBody
    public ResultVO<Map<String,Object>> articleDetails(Map<String,Object> map,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                       @RequestParam(value = "articleId") String articleId){
        //评论
        PageRequest pageRequest = new PageRequest(page-1,size);
        PageDTO<CommentVO> pageDTO = agoCommentService.findComment(pageRequest,articleId,ONE);
        map.put("pageContent", pageDTO);
        //文章详情
        ArticleDetailsVO articleDetailsVO = agoArticleService.findArticleDetails(articleId);
        map.put("articleDetails",articleDetailsVO);

        map.put("size", size);
        map.put("currentPage", page);
        return ResultVOUtil.success(map);
    }

    /**
     * 文章收藏
     */
    @ResponseBody
    @PostMapping("/collect")
    public ResultVO<Map<String,Object>> articleCollect(@RequestParam(value = "articleId") String articleId,
                                                     @RequestParam(value = "userId") String userId){
        agoArticleService.articleCollect(articleId,userId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 文章取消收藏
     */
    @ResponseBody
    @PostMapping("/collect/delete")
    public ResultVO<Map<String,Object>> deleteCollect(@RequestParam(value = "collectId") Integer collectId){
        agoArticleService.deleteCollectByCollectId(collectId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

    /**
     * 文章举报上传
     */
    @PostMapping("/report/save")
    @ResponseBody
    public ResultVO<Map<String, String>> reprotSave(@Valid ReprotInfoForm reprotInfoForm){
        agoArticleService.articleReportInfoSave(reprotInfoForm);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }

}
