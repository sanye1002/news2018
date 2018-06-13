package cn.popo.news.core.controller.api;

import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.dto.api.Author;
import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.entity.common.Logo;
import cn.popo.news.core.service.ArticleService;
import cn.popo.news.core.service.ClassifyService;
import cn.popo.news.core.service.LogoService;
import cn.popo.news.core.service.api.AgoArticleService;
import cn.popo.news.core.service.api.AgoPersonalService;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SortTools;
import cn.popo.news.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    private ClassifyService classifyService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AgoArticleService agoArticleService;

    @Autowired
    private AgoPersonalService agoPersonalService;

    @Autowired
    private LogoService logoService;

    private static final Integer ZERO = 0;
    private static final Integer ONE = 1;
    private static final Integer TWO = 2;
    private static final Integer THREE = 3;



    /**
     * @param
     * @return logo
     * @desc logo
     */
    @PostMapping("/logo")
    @ResponseBody
    public ResultVO<Map<String,Object>> logo(Map<String,Object> map){

       Logo logo = logoService.findShowLogo();
        map.put("logo",logo);
        return ResultVOUtil.success(map);
    }


    /**
     * @param
     * @return List<SearchVO>
     * @desc 导航
     */
    @PostMapping("/nav")
    @ResponseBody
    public ResultVO<Map<String,Object>> nav(Map<String,Object> map){

        //导航
        List<Classify> list = classifyService.findAllClassify();
        map.put("indexNavigation",list);
        return ResultVOUtil.success(map);
    }

    /**
     * @param
     * @return
     * @desc 轮播图
     */
    @PostMapping("/slide")
    @ResponseBody
    public ResultVO<Map<String,Object>> slide(Map<String,Object> map){

        //轮播图
        List<ArticleVO> slide = agoArticleService.findSlide(ONE,ZERO,ONE,ONE,ONE);
        map.put("slideContent",slide);
        return ResultVOUtil.success(map);
    }

    /**
     * @param
     * @return
     * @desc 侧边栏
     */
    @PostMapping("/recommend")
    @ResponseBody
    public ResultVO<Map<String,Object>> recommend(Map<String,Object> map){

        //侧边栏

        //图文
        List<ArticleVO> realTimeNews = agoArticleService.findRecommentByTypeId(ONE,ZERO,ONE,ONE,ONE,ONE);
        map.put("realTimeNews",realTimeNews);
        //多图
        List<ArticleVO> imgs = agoArticleService.findRecommentByTypeId(ONE,ZERO,ONE,ONE,TWO,ONE);
        map.put("recommendImgs",imgs);
        //视频
        List<ArticleVO> videos = agoArticleService.findRecommentByTypeId(ONE,ZERO,ONE,ONE,THREE,ONE);
        map.put("recommendVideos",videos);

        return ResultVOUtil.success(map);
    }



    /**
     * @param content page size
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
        if (content==""||content==null){
            return ResultVOUtil.error(100,"内容为空~");
        }

        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","crateTime"));
        PageDTO<ArticleVO> pageDTO = articleService.findArticleTitleLikeAndStateAndShowStateAndDraft(pageRequest,ONE,content,ONE,ZERO);
        pageDTO.setCurrentPage(page);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param content page size
     * @return
     * @desc 首页用户搜索
     */
    @PostMapping("/search/user")
    @ResponseBody
    public ResultVO<Map<String,Object>> searchUser(Map<String,Object> map,
                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "12") Integer size,
                                               @RequestParam(value = "content") String content
    ){
        if (content==""||content==null){
            return ResultVOUtil.error(100,"内容为空~");
        }

        PageRequest pageRequest = new PageRequest(page-1,size);
        PageDTO<Author> pageDTO = agoPersonalService.findUserByUserTypeAndNickNameLike(pageRequest,"1",content);
        pageDTO.setCurrentPage(page);
        map.put("user", pageDTO);
        return ResultVOUtil.success(map);
    }


    /**
     * @param classifyId page size
     * @return List<SearchVO>
     * @desc 分类查找
     */
    @PostMapping("/classify")
    @ResponseBody
    public ResultVO<Map<String,Object>> classify(Map<String,Object> map,
                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "12") Integer size,
                                               @RequestParam(value = "classifyId") Integer classifyId
    ){
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","crateTime"));
        PageDTO<ArticleVO> pageDTO = agoArticleService.findAllArticleByClassifyIdAndShowStateAndStateAndDraft(pageRequest,classifyId,ONE,ONE,ZERO);
        pageDTO.setCurrentPage(page);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }


    /**
     * @param page size
     * @return
     * @desc 首页文章数据第一版
     *//*
    @PostMapping("/index")
    @ResponseBody
    public ResultVO<Map<String,Object>> index(Map<String,Object> map,
                                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "size", defaultValue = "12") Integer size){



        //文章
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","crateTime"));
        PageDTO<ArticleVO> pageDTO = articleService.findAllArticleByShowStateAndStateAndDraft(pageRequest,ONE,ONE,ZERO);
        pageDTO.setCurrentPage(page);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }*/


    /**
     * @param page size
     * @return  List<article>
     * @desc 首页上拉刷新
     */
    @PostMapping("/index/article")
    @ResponseBody
    public ResultVO<Map<String,Object>> indexArticle(Map<String,Object> map,
                                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "size", defaultValue = "12") Integer size){


        //文章
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","crateTime"));
        PageDTO<ArticleVO> pageDTO = articleService.findAllArticleByShowStateAndStateAndDraft(pageRequest,ONE,ONE,ZERO);
        pageDTO.setCurrentPage(page);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param page size
     * @return  List<article>
     * @desc 首页刷新按时间
     */
    @PostMapping("/time/article")
    @ResponseBody
    public ResultVO<Map<String,Object>> timeArticle(Map<String,Object> map,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                     @RequestParam(value = "time") Long time
    ){


        //文章
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","crateTime"));
        PageDTO<ArticleVO> pageDTO = agoArticleService.findAllArticleByStateAndShowStateAndDraftAndTimeAfter(pageRequest,ONE,ONE,ZERO,1,time);
        pageDTO.setCurrentPage(page);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param page size
     * @return  List<article>
     * @desc 推荐文章（通过keyword查找）
     */
    @PostMapping("/recommend/article")
    @ResponseBody
    public ResultVO<Map<String,Object>> recommendArticle(Map<String,Object> map,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                     @RequestParam(value = "content", defaultValue = "12") String content
    ){


        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","crateTime"));
        PageDTO<ArticleVO> pageDTO = agoArticleService.findAllArticleByKeywordsLike(pageRequest,ONE,ZERO,ONE,content);
        pageDTO.setCurrentPage(page);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }


}
