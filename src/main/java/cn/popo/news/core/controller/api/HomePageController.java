package cn.popo.news.core.controller.api;

import cn.popo.news.common.constant.RedisConstant;
import cn.popo.news.common.utils.RedisOperator;
import cn.popo.news.common.utils.StatisticsInfoGetUtil;
import cn.popo.news.common.utils.ToolUtil;
import cn.popo.news.common.utils.UserSessionUtil;
import cn.popo.news.core.controller.oa.UserRealInfo;
import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.ArticleVO;
import cn.popo.news.core.dto.api.Author;
import cn.popo.news.core.entity.common.ArticlePraise;
import cn.popo.news.core.entity.common.Classify;
import cn.popo.news.core.entity.common.Logo;
import cn.popo.news.core.entity.common.SearchWords;
import cn.popo.news.core.repository.ArticlePraiseRepository;
import cn.popo.news.core.service.ArticleService;
import cn.popo.news.core.service.ClassifyService;
import cn.popo.news.core.service.IPStatisticsService;
import cn.popo.news.core.service.LogoService;
import cn.popo.news.core.service.api.AgoArticleService;
import cn.popo.news.core.service.api.AgoPersonalService;
import cn.popo.news.core.service.api.SearchWordsService;
import cn.popo.news.core.utils.RandomUtil;
import cn.popo.news.core.utils.ResultVOUtil;
import cn.popo.news.core.utils.SortTools;
import cn.popo.news.core.vo.ResultVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    private SearchWordsService searchWordsService;

    @Autowired
    private LogoService logoService;

    @Autowired
    private IPStatisticsService ipStatisticsService;

    @Autowired
    private UserSessionUtil userSessionUtil;

    @Autowired
    private ArticlePraiseRepository articlePraiseRepository;


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
    public ResultVO<Map<String,Object>> nav(Map<String,Object> map,HttpServletRequest httpServletRequest){

        ipStatisticsService.saveIP(ToolUtil.getClientIp(httpServletRequest),
                StatisticsInfoGetUtil.getVisitUitl(httpServletRequest),
                StatisticsInfoGetUtil.getSystemAndBrowser(httpServletRequest)
                );
        StatisticsInfoGetUtil.getSystemAndBrowser(httpServletRequest);

        //导航
        List<Classify> list = classifyService.findAllClassify();
        map.put("indexNavigation",list);
        return ResultVOUtil.success(map);
    }

    //获取访问信息
    @PostMapping("/ip")
    @ResponseBody
    public ResultVO<Map<String,Object>> getIpAndSystemAndBrowser(Map<String,Object> map,HttpServletRequest httpServletRequest){
        ipStatisticsService.saveIP(ToolUtil.getClientIp(httpServletRequest),
                StatisticsInfoGetUtil.getVisitUitl(httpServletRequest),
                StatisticsInfoGetUtil.getSystemAndBrowser(httpServletRequest)
        );
        StatisticsInfoGetUtil.getSystemAndBrowser(httpServletRequest);
        return ResultVOUtil.success(map);
    }

    //android nav
    @PostMapping("/android/nav")
    @ResponseBody
    public ResultVO<Map<String,Object>> navAndroid(Map<String,Object> map){
        //导航
        List<Classify> list = classifyService.findAllClassify();
        map.put("indexNavigation",list);
        return ResultVOUtil.success(map);
    }

    /**
     * @param
     * @return
     * @desc 热搜词
     */
    @PostMapping("/hot/search/words")
    @ResponseBody
    public ResultVO<Map<String,Object>> hotSearchWords(Map<String,Object> map){

        List<SearchWords> list = searchWordsService.findHotSearchWords();
        map.put("hot",list);
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
     * @desc 地址 ip
     */
    @PostMapping("/city")
    @ResponseBody
    public ResultVO<Map<String,Object>> city(Map<String,Object> map,
                                             @RequestParam(value = "content") String content
                                             ){

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
        searchWordsService.saveSearchWords(content);
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
     * @desc 首页
     */
    @PostMapping("/index/article")
    @ResponseBody
    public ResultVO<Map<String,Object>> indexArticle(Map<String,Object> map,
                                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "size", defaultValue = "12") Integer size){


        //文章
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","auditTime"));
        PageDTO<ArticleVO> pageDTO = articleService.findAllArticleByShowStateAndStateAndDraft(pageRequest,ONE,ONE,ZERO);
        pageDTO.setCurrentPage(page);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param page size
     * @return  List<article>
     * @desc 首页随机刷新
     */
    @PostMapping("/random/article")
    @ResponseBody
    public ResultVO<Map<String,Object>> indexRandomArticle(Map<String,Object> map,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "10") Integer size){


        PageDTO<ArticleVO> pageDTO = new PageDTO<>();
        List<ArticleVO> list = new ArrayList<>();
        for (int i=0;i<size;i++){
            page = new Random().nextInt(700)+300;
            PageRequest pageRequest = new PageRequest(page-1,1,SortTools.basicSort("desc","auditTime"));
            list.addAll(articleService.findAllArticleByShowStateAndStateAndDraft(pageRequest,ONE,ONE,ZERO).getPageContent());
        }
        pageDTO.setPageContent(list);
        //文章
        pageDTO.setCurrentPage(page);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }

    @PostMapping("/classify/random/article")
    @ResponseBody
    public ResultVO<Map<String,Object>> classifyRandomArticle(Map<String,Object> map,
                                                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                           @RequestParam(value = "classifyId") Integer classifyId

    ){


        PageDTO<ArticleVO> pageDTO = new PageDTO<>();
        List<ArticleVO> list = new ArrayList<>();
        for (int i=0;i<size;i++){
            page = new Random().nextInt(80)+20;
            PageRequest pageRequest = new PageRequest(page-1,1,SortTools.basicSort("desc","auditTime"));
            list.addAll(agoArticleService.findAllArticleByClassifyIdAndShowStateAndStateAndDraft(pageRequest,classifyId,ONE,ONE,ZERO).getPageContent());
        }
        pageDTO.setPageContent(list);
        //文章
        pageDTO.setCurrentPage(page);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }



    /**
     * @param page size
     * @return  List<article>
     * @desc 首页刷新
     */
    @PostMapping("/refresh/article")
    @ResponseBody
    public ResultVO<Map<String,Object>> refreshArticle(Map<String,Object> map,
                                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", defaultValue = "6") Integer size){


        //文章

        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("asc","auditTime"));
        PageDTO<ArticleVO> pageDTO = articleService.findAllArticleByShowStateAndStateAndDraft(pageRequest,ONE,ONE,ZERO);
        pageDTO.setCurrentPage(page);
        Collections.shuffle(pageDTO.getPageContent());
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
                                                     @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                     @RequestParam(value = "time") Long time
    ){
        //文章
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","auditTime"));
        PageDTO<ArticleVO> pageDTO = agoArticleService.findAllArticleByStateAndShowStateAndDraftAndTimeAfter(pageRequest,ONE,ZERO,ZERO,1,time);
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
                                                     @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                     @RequestParam(value = "content") List<String> content
    ){

        List<ArticleVO> list = new ArrayList<>();
        /*content.forEach(l->{

        });*/

        List<ArticleVO> articleVOList = agoArticleService.findAllArticleByKeywordsLike(ONE,ZERO,ONE,content.get(0));
        List<ArticleVO> temp = new ArrayList<>(articleVOList);
        temp.retainAll(list);
        articleVOList.removeAll(temp);
        list.addAll(articleVOList);

        double d = size;
        double l = list.size()/d;
        Integer totalPages = (int)Math.ceil(l);
        PageDTO<ArticleVO> pageDTO = new PageDTO<>();
        if(list.size()!=0){
            if (totalPages == page){
                pageDTO.setPageContent(list.subList((page-1)*size,list.size()));
            }else {
                pageDTO.setPageContent(list.subList((page-1)*size,size*page));
            }
        }
        pageDTO.setCurrentPage(page);
        pageDTO.setTotalPages(totalPages);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param page size
     * @return  List<article>
     * @desc 各类型文章
     */
    @PostMapping("/type/article")
    @ResponseBody
    public ResultVO<Map<String,Object>> typeArticle(Map<String,Object> map,
                                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                    @RequestParam(value = "type") Integer type,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response
    ){

        String userId = "";
        if (userSessionUtil.verifyLoginStatus(request,response)){
            userId = userSessionUtil.getUserByCookie(request,response).getUserId();
        }

        //文章
        PageRequest pageRequest = new PageRequest(page-1,size,SortTools.basicSort("desc","auditTime"));
        PageDTO<ArticleVO> pageDTO = agoArticleService.findAllArticleByTypeId(pageRequest,type,ONE,ONE,ZERO,ZERO,userId);
        pageDTO.setCurrentPage(page);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }

    /**
     * @param page size
     * @return  List<article>
     * @desc 随机视频
     */
    @PostMapping("/random/type/article")
    @ResponseBody
    public ResultVO<Map<String,Object>> typeRandomArticle(Map<String,Object> map,
                                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                    @RequestParam(value = "type",defaultValue = "3") Integer type,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response
    ){

        String userId = "";
        if (userSessionUtil.verifyLoginStatus(request,response)){
            userId = userSessionUtil.getUserByCookie(request,response).getUserId();
        }

        PageDTO<ArticleVO> pageDTO = new PageDTO<>();

        Set<Integer> integers = RandomUtil.moreRandom(50, 200, size);

        List<ArticleVO> list = new ArrayList<>();
        for (int i=0;i<integers.size();i++){
            page = (Integer) integers.toArray()[i];
            PageRequest pageRequest = new PageRequest(page-1,1,SortTools.basicSort("desc","auditTime"));
            list.addAll(agoArticleService.findAllArticleByTypeId(pageRequest,type,ONE,ONE,ZERO,ZERO,userId).getPageContent());
        }
        //文章
//        pageDTO.setCurrentPage(page);
        pageDTO.setPageContent(list);
        map.put("article", pageDTO);
        return ResultVOUtil.success(map);
    }


    /**
     * 文章点赞
     * @param articleId
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/article/praise")
    @ResponseBody
    public ResultVO<Map<String,Object>> articlePraise(
            @RequestParam(value = "articleId") String articleId,
            HttpServletRequest request,
            HttpServletResponse response){

        if (!userSessionUtil.verifyLoginStatus(request,response)){
            return ResultVOUtil.error(3,"用户失效");
        }

        String userId = userSessionUtil.getUserByCookie(request,response).getUserId();
        ArticlePraise articlePraise = articlePraiseRepository.findAllByUidAndArticleId(userId,articleId);
        if(articlePraise!=null){
            return ResultVOUtil.error(100,"已赞");
        }
        agoArticleService.articlePraise(userId,articleId);
        Map<String,Object> map  = new HashMap<>();
        return ResultVOUtil.success(map);
    }


}
